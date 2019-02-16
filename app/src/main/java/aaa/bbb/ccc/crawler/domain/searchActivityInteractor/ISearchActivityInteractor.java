package aaa.bbb.ccc.crawler.domain.searchActivityInteractor;

import aaa.bbb.ccc.crawler.domain.model.Site;
import rx.Observable;

public interface ISearchActivityInteractor {
    Observable<Site> getSite(String query);
}
