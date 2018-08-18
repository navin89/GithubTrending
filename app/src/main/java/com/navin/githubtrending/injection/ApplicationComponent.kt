package com.navin.githubtrending.injection

import android.app.Application
import com.navin.githubtrending.GithubTrendingApplication
import com.navin.githubtrending.injection.module.*
import com.navin.remote.service.GithubTrendingService
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,
        ApplicationModule::class,
        UIModule::class,
        PresentationModule::class,
        DataModule::class,
        CacheModule::class,
        RemoteModule::class) )
interface ApplicationComponent {

    @Component.Builder
    interface builder {
        @BindsInstance
        fun application(application: Application): builder

        fun build(): ApplicationComponent
    }

    fun inject(app: GithubTrendingApplication)

}