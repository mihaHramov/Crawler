package aaa.bbb.ccc.crawler.data.repository.pageRepository;

import aaa.bbb.ccc.crawler.domain.model.Site;
import rx.Observable;

public interface ISiteRepository {
    Observable<Site> getSite(String query, Integer countOfNode, Integer countOfPage);
}
