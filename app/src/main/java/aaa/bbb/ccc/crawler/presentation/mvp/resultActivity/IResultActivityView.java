package aaa.bbb.ccc.crawler.presentation.mvp.resultActivity;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import aaa.bbb.ccc.crawler.domain.model.PageOfSite;

public interface IResultActivityView extends MvpView {
    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showResult(List<PageOfSite> pagers);
}
