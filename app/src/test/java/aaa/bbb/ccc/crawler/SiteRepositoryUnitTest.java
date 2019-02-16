package aaa.bbb.ccc.crawler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import aaa.bbb.ccc.crawler.data.network.ISiteApi;
import aaa.bbb.ccc.crawler.data.repository.pageRepository.ILocalStorage;
import aaa.bbb.ccc.crawler.data.repository.pageRepository.ISiteRepository;
import aaa.bbb.ccc.crawler.data.repository.pageRepository.SiteRepository;
import aaa.bbb.ccc.crawler.domain.model.Site;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SiteRepositoryUnitTest {
    ISiteApi mApi;
    ILocalStorage mStorage;

    @Before
    public void before() {
        mApi = mock(ISiteApi.class);
        mStorage = mock(ILocalStorage.class);
    }

    @Test
    public void save_answer_to_db_isCorrect() {
        Site site = new Site();
        site.setUrl("tempUrl");
        when(mApi.getSite(anyString(), anyInt(), anyInt())).then((Answer<Observable<Site>>) invocation -> Observable.just(site));
        ISiteRepository repository = new SiteRepository(mApi, mStorage);
        repository
                .getSite("", 1, 1)
                .subscribe(new TestSubscriber<>());
        verify(mStorage).setSite(site);
        verify(mStorage, never()).getSiteFromLocalStorage(anyString());
    }

    @Test
    public void error_from_api_isCorrect() {
        Site site = new Site();
        site.setUrl("tempUrl");
        when(mApi.getSite(anyString(), anyInt(), anyInt())).then((Answer<Observable<Site>>) invocation -> Observable.error(new Throwable("")));
        when(mStorage.getSiteFromLocalStorage(anyString())).then((Answer<Observable<Site>>) invocation -> Observable.just(site));
        ISiteRepository repository = new SiteRepository(mApi, mStorage);
        repository
                .getSite("", 1, 1)
                .subscribe(new TestSubscriber<>());
        verify(mStorage).getSiteFromLocalStorage(anyString());
    }
}