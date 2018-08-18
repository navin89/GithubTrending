package com.navin.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.navin.domain.interactor.bookmark.BookmarkProject
import com.navin.domain.interactor.bookmark.UnbookmarkProject
import com.navin.domain.interactor.browse.GetBookmarkedProjects
import com.navin.domain.interactor.browse.GetProjects
import com.navin.domain.model.Project
import com.navin.presentation.mapper.ProjectViewMapper
import com.navin.presentation.model.ProjectView
import com.navin.presentation.state.Resource
import com.navin.presentation.state.ResourceState
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

open class BrowseProjectsViewModel @Inject internal constructor(
        val getProjects: GetProjects?,
        val bookmarkProject: BookmarkProject,
        val unbookmarkProject: UnbookmarkProject,
        val mapper: ProjectViewMapper ): ViewModel() {


    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()


    override fun onCleared() {
        getProjects?.dispose()
        super.onCleared()
    }


    fun getProjects(): LiveData<Resource<List<ProjectView>>> {

        return liveData
    }

    fun fetchProjects(){
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getProjects?.execute(ProjectSubscriber())!!
    }

    fun bookmarkProject(projectId: String) {

        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return bookmarkProject.execute(BookmarkingSubscriber(), BookmarkProject.Params.forProject(projectId))
    }

    fun unBookmarkProject(projectId: String) {

        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return unbookmarkProject.execute(BookmarkingSubscriber(), UnbookmarkProject.Params.forProject(projectId))
    }



    inner class ProjectSubscriber: DisposableObserver<List<Project>>() {

        override fun onComplete() { }

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map { mapper.mapToView(it) }, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }

    inner class BookmarkingSubscriber: DisposableCompletableObserver() {

        override fun onComplete() {
            liveData.postValue(Resource(ResourceState.SUCCESS, liveData.value?.data , null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, liveData.value?.data , e.localizedMessage))
        }
    }


}