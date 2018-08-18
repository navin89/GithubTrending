package com.navin.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.navin.domain.interactor.browse.GetBookmarkedProjects
import com.navin.domain.model.Project
import com.navin.presentation.mapper.ProjectViewMapper
import com.navin.presentation.model.ProjectView
import com.navin.presentation.state.Resource
import com.navin.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseBookmarkedProjectsViewModel @Inject constructor(
        private val getBookmarkedProjects: GetBookmarkedProjects,
        private val mapper: ProjectViewMapper ): ViewModel() {


    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()


    override fun onCleared() {
        getBookmarkedProjects.dispose()
        super.onCleared()
    }


    fun getProjects(): LiveData<Resource<List<ProjectView>>>{
        return liveData
    }

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getBookmarkedProjects.execute(PublishSubscriber())
    }

    inner class PublishSubscriber: DisposableObserver<List<Project>>() {

        override fun onComplete() { }

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map { mapper.mapToView(it) }, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }
    }


}