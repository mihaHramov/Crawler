package aaa.bbb.ccc.crawler.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import aaa.bbb.ccc.crawler.R;
import aaa.bbb.ccc.crawler.domain.model.PageOfSite;
import aaa.bbb.ccc.crawler.domain.model.Site;
import aaa.bbb.ccc.crawler.presentation.mvp.resultActivity.IResultActivityView;
import aaa.bbb.ccc.crawler.presentation.mvp.resultActivity.ResultActivityPresenter;
import aaa.bbb.ccc.crawler.presentation.ui.adapter.PageOfSiteRecyclerViewAdapter;

public class ResultActivity extends MvpAppCompatActivity implements IResultActivityView {
    private RecyclerView mRecyclerView;
    private PageOfSiteRecyclerViewAdapter mAdapter;
    private Toolbar toolbar;
    private static final String KEY = ResultActivity.class.getName();
    @InjectPresenter
    ResultActivityPresenter presenter;

    @ProvidePresenter
    ResultActivityPresenter providePresenter() {
        presenter = new ResultActivityPresenter((Site) getIntent().getSerializableExtra(KEY));
        presenter.init();
        return presenter;
    }

    public static Intent newInstance(Site site, Context context) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(KEY, site);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new PageOfSiteRecyclerViewAdapter(pageOfSite -> {
            Intent intent = GalleryActivity.newInstance(pageOfSite, ResultActivity.this);
            startActivity(intent);
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showResult(List<PageOfSite> pagers) {
        mAdapter.setData(pagers);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort_ascending) {
            presenter.sortByAscending();
            return true;
        }
        if (id == R.id.action_sort_descending) {
            presenter.sortByDescending();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
