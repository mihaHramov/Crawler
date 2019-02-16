package aaa.bbb.ccc.crawler.data.repository.pageRepository;


import aaa.bbb.ccc.crawler.domain.model.Site;
import rx.Observable;

public interface ILocalStorage {
    Observable<Site> getSiteFromLocalStorage(String url);

    void setSite(Site site);
}
