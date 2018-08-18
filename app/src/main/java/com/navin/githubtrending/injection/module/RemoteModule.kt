package com.navin.githubtrending.injection.module

import com.navin.data.repository.ProjectsRemote
import com.navin.githubtrending.BuildConfig
import com.navin.remote.ProjectsRemoteImpl
import com.navin.remote.service.GithubTrendingService
import com.navin.remote.service.GithubTrendingServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun makeGithubTrendingService(): GithubTrendingService {

            return GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindRemoteProjects(projectsRemoteImpl: ProjectsRemoteImpl): ProjectsRemote


}