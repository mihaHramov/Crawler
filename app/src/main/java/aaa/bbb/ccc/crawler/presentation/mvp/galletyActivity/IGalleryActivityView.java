package aaa.bbb.ccc.crawler.presentation.mvp.galletyActivity;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface IGalleryActivityView extends MvpView {
    void showGallery(List<String> strings);
    void showTitle(String string);
}
