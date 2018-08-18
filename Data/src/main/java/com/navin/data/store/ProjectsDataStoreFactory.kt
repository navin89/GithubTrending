package com.navin.data.store

import com.navin.data.model.ProjectEntity
import com.navin.data.repository.ProjectsDataStore
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

open class ProjectsDataStoreFactory @Inject constructor(
            private val projectsCacheDataStore: ProjectsCacheDataStore,
            private val projectsRemoteDataStore: ProjectsRemoteDataStore ) {

    /**
     * Machine chooses which data store to tap onto..
     * Get the cache data if it is still available, if it isn't fetch from remote
     * */

    open fun getDataStore(projectCached: Boolean,
                          cacheExpired: Boolean ): ProjectsDataStore {
        return if (projectCached && !cacheExpired) {
            projectsCacheDataStore
        } else {
            projectsRemoteDataStore
        }
    }

    /**
     * For getting bookmarked projects only..
     *
     * */
    open fun getCachedDataStore(): ProjectsDataStore {

        return projectsCacheDataStore
    }




}