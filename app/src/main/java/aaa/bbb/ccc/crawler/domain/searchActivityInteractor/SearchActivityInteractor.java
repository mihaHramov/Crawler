package aaa.bbb.ccc.crawler.domain.searchActivityInteractor;

import android.util.Pair;

import aaa.bbb.ccc.crawler.data.repository.pageRepository.ISiteRepository;
import aaa.bbb.ccc.crawler.data.repository.settingsRepository.ISettingsRepository;
import aaa.bbb.ccc.crawler.domain.model.Site;
import rx.Observable;
import rx.functions.Func1;

public class SearchActivityInteractor implements ISearchActivityInteractor {
    private ISettingsRepository mSettingsRepository;
    private ISiteRepository mSiteRepository;

    public SearchActivityInteractor(ISettingsRepository mSettingsRepository, ISiteRepository mSiteRepository) {
        this.mSettingsRepository = mSettingsRepository;
        this.mSiteRepository = mSiteRepository;
    }

    @Override
    public Observable<Site> getSite(String query) {
        return Observable.zip(
                mSettingsRepository.getCountOfNode(),
                mSettingsRepository.getCountOfPage(), Pair::new).flatMap((Func1<Pair<Integer, Integer>, Observable<Site>>)
                        integerIntegerPair -> mSiteRepository.getSite(query, integerIntegerPair.first, integerIntegerPair.second)
                );
    }


}
