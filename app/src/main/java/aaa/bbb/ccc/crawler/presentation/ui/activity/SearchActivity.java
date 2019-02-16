package aaa.bbb.ccc.crawler.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import aaa.bbb.ccc.crawler.R;
import aaa.bbb.ccc.crawler.data.network.SiteApi;
import aaa.bbb.ccc.crawler.data.repository.pageRepository.ISiteRepository;
import aaa.bbb.ccc.crawler.data.repository.pageRepository.LocalSiteStorage;
import aaa.bbb.ccc.crawler.data.repository.pageRepository.SiteRepository;
import aaa.bbb.ccc.crawler.data.repository.settingsRepository.ISettingsRepository;
import aaa.bbb.ccc.crawler.data.repository.settingsRepository.SettingsRepository;
import aaa.bbb.ccc.crawler.domain.model.Site;
import aaa.bbb.ccc.crawler.domain.searchActivityInteractor.ISearchActivityInteractor;
import aaa.bbb.ccc.crawler.domain.searchActivityInteractor.SearchActivityInteractor;
import aaa.bbb.ccc.crawler.presentation.mvp.searchActivity.ISearchActivityView;
import aaa.bbb.ccc.crawler.presentation.mvp.searchActivity.SearchActivityPresenter;
import aaa.bbb.ccc.crawler.presentation.ui.fragment.ErrorInfoFragment;
import aaa.bbb.ccc.crawler.presentation.ui.fragment.SettingsFragment;

public class SearchActivity extends MvpAppCompatActivity implements ISearchActivityView {
    private EditText mQueryField;
    @InjectPresenter
    SearchActivityPresenter mPresenter;
    private Button mButtonSearch;
    private ProgressBar mProgressBar;

    @ProvidePresenter
    SearchActivityPresenter provide() {
        ISettingsRepository settingsRepository = new SettingsRepository(this);
        ISiteRepository siteRepository = new SiteRepository(new SiteApi(), new LocalSiteStorage(this));
        ISearchActivityInteractor interactor = new SearchActivityInteractor(settingsRepository, siteRepository);
        return new SearchActivityPresenter(interactor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mProgressBar = findViewById(R.id.progress_bar);
        mQueryField = findViewById(R.id.query_fild);
        mButtonSearch = findViewById(R.id.search_button);
        mButtonSearch.setOnClickListener(view -> mPresenter.search(mQueryField.getText().toString()));
    }


    @Override
    public void showError() {
        ErrorInfoFragment.newInstance(getResources().getString(R.string.empty_answer)).show(getSupportFragmentManager(), ErrorInfoFragment.class.getName());
    }

    @Override
    public void showResult(Site site) {
        Intent intent = ResultActivity.newInstance(site, this);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            mPresenter.onSettingClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading(Boolean flag) {
        mProgressBar.setVisibility(flag ? View.VISIBLE : View.GONE);
        mProgressBar.setEnabled(flag);
        mQueryField.setEnabled(!flag);
        mButtonSearch.setEnabled(!flag);
    }

    @Override
    public void showSettings() {
        new SettingsFragment().show(getSupportFragmentManager(), SettingsFragment.class.getName());
    }
}
