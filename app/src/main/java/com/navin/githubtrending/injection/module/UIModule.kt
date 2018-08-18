package com.navin.githubtrending.injection.module

import com.navin.domain.executor.PostExecutionThread
import com.navin.githubtrending.browse.BrowseActivity
import com.navin.mobile_ui.UIThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule{


    @Binds
    abstract fun postExecutionThread(uiThread: UIThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity


}