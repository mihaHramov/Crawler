package aaa.bbb.ccc.crawler.data.network;

import aaa.bbb.ccc.crawler.domain.model.Site;
import rx.Observable;

public interface ISiteApi {
    Observable<Site> getSite(String query, Integer countOfNode, Integer countOfPage);
}
