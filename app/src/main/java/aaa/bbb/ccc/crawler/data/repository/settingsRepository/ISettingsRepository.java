package aaa.bbb.ccc.crawler.data.repository.settingsRepository;

import rx.Observable;

public interface ISettingsRepository {
    void setCountOfPage(Integer countOfPage);

    void setCountOfNode(Integer countOfNode);

    Observable<Integer> getCountOfPage();

    Observable<Integer> getCountOfNode();
}
