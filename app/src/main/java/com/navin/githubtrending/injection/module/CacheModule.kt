package com.navin.githubtrending.injection.module

import android.app.Application
import com.navin.cache.ProjectsCacheImpl
import com.navin.cache.db.ProjectsDatabase
import com.navin.data.repository.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideDatabase(application: Application): ProjectsDatabase{
            return ProjectsDatabase.getInstance(application)
        }

    }

    @Binds
    abstract fun bindProjectsCache(projectsCacheImpl: ProjectsCacheImpl): ProjectsCache


}