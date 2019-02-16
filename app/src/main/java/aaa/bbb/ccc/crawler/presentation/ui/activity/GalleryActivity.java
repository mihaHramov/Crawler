package aaa.bbb.ccc.crawler.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import aaa.bbb.ccc.crawler.R;
import aaa.bbb.ccc.crawler.domain.model.PageOfSite;
import aaa.bbb.ccc.crawler.presentation.mvp.galletyActivity.GalleryActivityPresenter;
import aaa.bbb.ccc.crawler.presentation.mvp.galletyActivity.IGalleryActivityView;
import aaa.bbb.ccc.crawler.presentation.ui.adapter.ImageOfSiterRecyclerViewAdapter;

public class GalleryActivity extends MvpAppCompatActivity implements IGalleryActivityView {
    private static final String KEY = GalleryActivity.class.getName();
    RecyclerView mRecyclerView;
    ImageOfSiterRecyclerViewAdapter mAdapter;

    @InjectPresenter
    GalleryActivityPresenter presenter;
    @ProvidePresenter
    GalleryActivityPresenter provideGalleryActivityPresenter(){
        presenter = new GalleryActivityPresenter((PageOfSite) getIntent().getSerializableExtra(KEY));
        presenter.init();
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new ImageOfSiterRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showGallery(List<String> strings) {
        mAdapter.setImages(strings);
    }

    @Override
    public void showTitle(String string) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(string);
        }
    }

    public static Intent newInstance(PageOfSite pageOfSite, Context context) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(KEY, pageOfSite);
        return intent;
    }
}
