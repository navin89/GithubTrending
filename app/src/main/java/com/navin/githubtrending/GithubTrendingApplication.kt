package com.navin.githubtrending

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.navin.githubtrending.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

import kotlinx.android.synthetic.main.activity_browse.*
import timber.log.Timber
import javax.inject.Inject

class GithubTrendingApplication : Application(), HasActivityInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()

        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }
}
