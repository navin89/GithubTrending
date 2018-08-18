package com.navin.githubtrending.injection.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun getContext(application: Application): Context

}