package aaa.bbb.ccc.crawler.presentation.mvp.searchActivity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import aaa.bbb.ccc.crawler.domain.model.Site;

public interface ISearchActivityView extends MvpView {
    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showError();

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showResult(Site site);

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showLoading(Boolean flag);

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showSettings();
}
