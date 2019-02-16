package aaa.bbb.ccc.crawler.presentation.mvp.searchActivity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import aaa.bbb.ccc.crawler.domain.searchActivityInteractor.ISearchActivityInteractor;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class SearchActivityPresenter extends MvpPresenter<ISearchActivityView> {
    private ISearchActivityInteractor mInteractor;


    public SearchActivityPresenter(ISearchActivityInteractor mInteractor) {
        this.mInteractor = mInteractor;
    }


    public void search(String query) {
        mInteractor.getSite(query)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getViewState().showLoading(true))
                .subscribe(site -> {
                            getViewState().showLoading(false);
                            getViewState().showResult(site);
                        }, throwable -> {
                            getViewState().showLoading(false);
                            getViewState().showError();
                        }
                );


    }

    public void onSettingClick() {
        getViewState().showSettings();
    }
}
