package aaa.bbb.ccc.crawler.data.repository.pageRepository;

import aaa.bbb.ccc.crawler.data.network.ISiteApi;
import aaa.bbb.ccc.crawler.domain.model.Site;
import rx.Observable;

public class SiteRepository implements ISiteRepository {
    private ISiteApi mSiteApi;
    private ILocalStorage mLocalStorage;

    public SiteRepository(ISiteApi mSiteApi, ILocalStorage mLocalStorage) {
        this.mSiteApi = mSiteApi;
        this.mLocalStorage = mLocalStorage;
    }

    @Override
    public Observable<Site> getSite(String query, Integer countOfNode, Integer countOfPage) {
        return mSiteApi.getSite(query, countOfNode, countOfPage)
                .doOnNext(site -> mLocalStorage.setSite(site))
                .onErrorResumeNext(throwable -> mLocalStorage.getSiteFromLocalStorage(query));
    }
}