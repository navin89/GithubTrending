package com.navin.mobile_ui

import android.support.annotation.MainThread
import com.navin.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UIThread @Inject constructor(): PostExecutionThread {
    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()


}