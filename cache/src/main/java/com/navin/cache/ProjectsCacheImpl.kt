package com.navin.cache

import com.navin.cache.db.ProjectsDatabase
import com.navin.cache.mapper.CacheProjectMapper
import com.navin.cache.model.Config
import com.navin.data.model.ProjectEntity
import com.navin.data.repository.ProjectsCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.rxkotlin.toSingle
import javax.inject.Inject

class ProjectsCacheImpl @Inject constructor(
        private val projectsDatabase: ProjectsDatabase,
        private val mapper: CacheProjectMapper
    ): ProjectsCache {

    override fun clearProjects(): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().deleteProjects()
            Completable.complete()
        }
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().insertProjects(
                    projects.map { mapper.mapToCache(it) }
            )
            Completable.complete()
        }

    }

    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao().getProjects()
                .map {
                    it.map { mapper.mapFromCache(it) }
                }
    }

    override fun getBookmarkedProjects(): Flowable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao().getBookmarkedProjects()
                .map {
                    it.map { mapper.mapFromCache(it) }
                }
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().setBookmarkedStatus(true, projectId)
            Completable.complete()
        }

    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().setBookmarkedStatus(false, projectId)
            Completable.complete()
        }
    }

    override fun areProjectsCached(): Single<Boolean> {

        return projectsDatabase.cachedProjectsDao().getProjects().isEmpty
                .map {
                    !it // wants to return not empty
                }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            projectsDatabase.configDao().insertConfig(Config(lastCachedTime = lastCache))
            Completable.complete()
        }

    }

    override fun isProjectCacheExpired(): Single<Boolean> {

        val currentTime = System.currentTimeMillis()
        val expirationTime = (60 * 10 * 1000).toLong()
        return projectsDatabase.configDao().getConfig()
                .onErrorReturn { Config(lastCachedTime = 0) }
                .map {
                    currentTime - it.lastCachedTime > expirationTime
                }
    }
}

























