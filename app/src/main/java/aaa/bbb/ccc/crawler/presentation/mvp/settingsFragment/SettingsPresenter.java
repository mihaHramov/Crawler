package aaa.bbb.ccc.crawler.presentation.mvp.settingsFragment;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.Pair;

import aaa.bbb.ccc.crawler.data.repository.settingsRepository.ISettingsRepository;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@InjectViewState
public class SettingsPresenter extends MvpPresenter<ISettingsView> {
    ISettingsRepository mRepositoryOfSettings;
    Integer mCountOfPage;
    Integer mCountOfNode;

    public SettingsPresenter(ISettingsRepository mRepositoryOfSettings) {
        this.mRepositoryOfSettings = mRepositoryOfSettings;
    }

    public void init() {
        mRepositoryOfSettings.getCountOfPage()
                .zipWith(mRepositoryOfSettings.getCountOfNode(), Pair::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integerIntegerPair -> {
                    mCountOfPage = integerIntegerPair.first;
                    mCountOfNode = integerIntegerPair.second;
                    getViewState().showCountOfPage(integerIntegerPair.first);
                    getViewState().showCountOfNode(integerIntegerPair.second);
                });
    }

    public void plusCountNode() {
        mCountOfNode++;
        setNewCountNode(mCountOfNode);
    }

    public void minusCountNode() {
        mCountOfNode--;
        setNewCountNode(mCountOfNode);
    }

    public void plusCountPage() {
        mCountOfPage++;
        setNewCountPage(mCountOfPage);
    }

    public void minusCountPage() {
        mCountOfPage--;
        setNewCountPage(mCountOfPage);
    }

    private void setNewCountNode(Integer count) {
        Observable.just(count)
                .doOnNext(integer -> mRepositoryOfSettings.setCountOfNode(integer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> getViewState().showCountOfNode(integer));
    }

    private void setNewCountPage(Integer count) {
        Observable.just(count)
                .doOnNext(integer -> mRepositoryOfSettings.setCountOfPage(integer))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> getViewState().showCountOfPage(integer));
    }
}
