package com.navin.data.repository

import com.navin.data.model.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 *  Separation of concerns are shown here
 *
 *  This is the cache layer and has only the methods that conform to
 *  data -> pulling and pushing to/from a cache source
 *
 *  Since Data layer has a huge responsibility on deciding what is required,
 *  we don't want to clump the data layer options(Remote and Cache) / methods into a single
 *  interface which drops the burden on any one of these(Remote and Cache) interfaces with
 *  unwanted calls.. thus avoiding bloated process calls in data layer.
 * */


interface ProjectsCache {

    fun clearProjects(): Completable

    fun saveProjects(projects: List<ProjectEntity>): Completable

    fun getProjects(): Flowable<List<ProjectEntity>>

    fun getBookmarkedProjects(): Flowable<List<ProjectEntity>>

    fun setProjectAsBookmarked(projectId: String): Completable

    fun setProjectAsNotBookmarked(projectId: String): Completable

    fun areProjectsCached(): Single<Boolean> // give me one value

    fun setLastCacheTime(lastCache: Long): Completable

    fun isProjectCacheExpired(): Single<Boolean> // give me one value


}