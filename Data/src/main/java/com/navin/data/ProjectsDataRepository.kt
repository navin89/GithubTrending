package com.navin.data

import com.navin.data.mapper.ProjectMapper
import com.navin.data.repository.ProjectsCache
import com.navin.data.store.ProjectsDataStoreFactory
import com.navin.domain.model.Project
import com.navin.domain.repository.ProjectsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
        private val mapper: ProjectMapper,
        private val cache: ProjectsCache,
        private val factory: ProjectsDataStoreFactory ): ProjectsRepository {

    override fun getProjects(): Observable<List<Project>> {
        /**
         * When getProjects() invokes, zip() method starts a race for cached or expired projects
         * whatever that comes in; as a boolean value; it takes the value and evaluate further with
         * factory.getDataStore().getProjects()
         * */
        return Observable.zip(
                cache.areProjectsCached().toObservable(),
                cache.isProjectCacheExpired().toObservable(),
                BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { areCached, isExpired ->
                    Pair(areCached, isExpired)
                })
                .flatMap {
                    factory.getDataStore(it.first, it.second).getProjects().toObservable()
                            .distinctUntilChanged()

                }
                .flatMap { projects ->

                    factory.getCachedDataStore()
                            .saveProjects(projects)
                            .andThen(Observable.just(projects))

                }
                .map {
                    it.map { 
                        mapper.mapFromEntity(it)
                    }
                }
    }

    override fun bookmarkProject(projectId: String): Completable {
        return factory.getCachedDataStore().setProjectAsBookmarked(projectId)
    }

    override fun unBookmarkProject(projectId: String): Completable {
        return factory.getCachedDataStore().setProjectAsNotBookmarked(projectId)
    }

    override fun getAllBookmarkedProjects(): Observable<List<Project>> {
        return factory.getCachedDataStore().getBookmarkedProjects().toObservable()
                .map { it.map { mapper.mapFromEntity(it) } }
    }

}