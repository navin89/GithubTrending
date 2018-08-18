package com.navin.data.store

import com.navin.data.model.ProjectEntity
import com.navin.data.repository.ProjectsDataStore
import com.navin.data.repository.ProjectsRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteDataStore @Inject constructor(private val projectsRemote: ProjectsRemote): ProjectsDataStore {


    override fun getProjects(): Flowable<List<ProjectEntity>> {
        return projectsRemote.getProjects()
    }

    override fun clearProjects(): Completable {
        throw UnsupportedOperationException("Clear projects method isn't supported here")
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException("saveProjects method isn't supported here")
    }

    override fun getBookmarkedProjects(): Flowable<List<ProjectEntity>> {
        throw UnsupportedOperationException("getBookmarkedProjects method isn't supported here")
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("setProjectAsBookmarked method isn't supported here")
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("setProjectAsNotBookmarked method isn't supported here")
    }

}