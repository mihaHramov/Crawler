package aaa.bbb.ccc.crawler.domain.utils.schedulers;

import rx.Scheduler;

public interface ISchedulersProvider {
    Scheduler ui();
    Scheduler computation();
    Scheduler io();
    Scheduler newThread();
    Scheduler trampoline();
}
