package aaa.bbb.ccc.crawler.presentation.mvp.galletyActivity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import aaa.bbb.ccc.crawler.domain.model.PageOfSite;

@InjectViewState
public class GalleryActivityPresenter extends MvpPresenter<IGalleryActivityView> {
    private PageOfSite page;

    public GalleryActivityPresenter(PageOfSite page) {
        this.page = page;
    }

    public void init() {
        if (!page.getImages().isEmpty()) {
            getViewState().showGallery(page.getImages());
        }
        getViewState().showTitle(page.getAddress());
    }
}
