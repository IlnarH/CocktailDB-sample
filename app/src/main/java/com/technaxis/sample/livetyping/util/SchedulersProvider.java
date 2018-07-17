package com.technaxis.sample.livetyping.util;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SchedulersProvider {

    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler computation() {
        return Schedulers.computation();
    }

    public Scheduler io() {
        return Schedulers.io();
    }

    public Scheduler newThread() {
        return Schedulers.newThread();
    }

    public Scheduler trampoline() {
        return Schedulers.trampoline();
    }
}
