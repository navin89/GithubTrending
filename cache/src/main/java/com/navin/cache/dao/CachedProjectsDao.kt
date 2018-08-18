package com.navin.cache.dao

import android.arch.persistence.room.*
import com.navin.cache.db.ProjectConstants.DELETE_PROJECTS
import com.navin.cache.db.ProjectConstants.QUERY_BOOKMARKED
import com.navin.cache.db.ProjectConstants.QUERY_PROJECTS
import com.navin.cache.db.ProjectConstants.QUERY_UPDATE_BOOKMARKED_STATUS
import com.navin.cache.model.CachedProject
import io.reactivex.Flowable

@Dao
abstract class CachedProjectsDao {

    @Query(QUERY_PROJECTS)
    abstract fun getProjects(): Flowable<List<CachedProject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProjects(projects: List<CachedProject>)

    @Query(DELETE_PROJECTS)
    abstract fun deleteProjects()

    @Query(QUERY_BOOKMARKED)
    abstract fun getBookmarkedProjects(): Flowable<List<CachedProject>>

    @Query(QUERY_UPDATE_BOOKMARKED_STATUS)
    abstract fun setBookmarkedStatus(isBookmarked: Boolean,
                                     projectId: String)


}