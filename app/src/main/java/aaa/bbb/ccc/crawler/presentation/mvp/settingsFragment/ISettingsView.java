package aaa.bbb.ccc.crawler.presentation.mvp.settingsFragment;

import com.arellomobile.mvp.MvpView;

public interface ISettingsView extends MvpView {
    void showCountOfNode(Integer integer);
    void showCountOfPage(Integer integer);
}
