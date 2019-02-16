package aaa.bbb.ccc.crawler.presentation.mvp.resultActivity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Collections;
import java.util.List;

import aaa.bbb.ccc.crawler.domain.model.PageOfSite;
import aaa.bbb.ccc.crawler.domain.model.Site;

@InjectViewState
public class ResultActivityPresenter extends MvpPresenter<IResultActivityView> {
    private Site mSite;

    public ResultActivityPresenter(Site mSite) {
        this.mSite = mSite;
    }

    public void init() {
        getViewState().showResult(mSite.getPages());
    }

    public void sortByAscending() {
        List<PageOfSite> pageOfSite = mSite.getPages();
        Collections.sort(pageOfSite, (pageOfSite1, t1) -> Integer.compare(pageOfSite1.getImages().size(), t1.getImages().size()));
        getViewState().showResult(pageOfSite);
    }

    public void sortByDescending() {
        List<PageOfSite> pageOfSite = mSite.getPages();
        Collections.sort(pageOfSite, (pageOfSite1, t1) -> Integer.compare(t1.getImages().size(), pageOfSite1.getImages().size()));
        getViewState().showResult(pageOfSite);
    }
}
