package com.navin.remote

import com.navin.data.model.ProjectEntity
import com.navin.data.repository.ProjectsRemote
import com.navin.remote.mapper.ProjectsResponseModelMapper
import com.navin.remote.service.GithubTrendingService
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
        private val mapper: ProjectsResponseModelMapper,
        private val githubTrendingService: GithubTrendingService )
    : ProjectsRemote {



    override fun getProjects(): Flowable<List<ProjectEntity>> {

        // we map it to projectResponseModel with our mapper class

        return githubTrendingService.searchRepositories("language:kotlin", "stars", "desc")
                .map {
                    it.items.map {
                        mapper.mapFromModel(it)
                    }
                }
    }




}