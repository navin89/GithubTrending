package com.navin.domain.interactor.browse

import com.navin.domain.executor.PostExecutionThread
import com.navin.domain.interactor.ObservableUseCase
import com.navin.domain.model.Project
import com.navin.domain.repository.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Constructor takes two parameters: ProjectRepository, PostExecutionThread
 *
 * ProjectRepository is our abstracted access point for outside data layers and domain layer contract for this layers
 *
 * PostExecutionThread instance to be thrown into the inherited base class use-case;
 * Refer to use-case class for more info.
 *
 * */

open class GetBookmarkedProjects @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        postExecutionThread: PostExecutionThread): ObservableUseCase<List<Project>, Nothing?>(postExecutionThread) {


    public override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {

        return projectsRepository.getAllBookmarkedProjects()
    }


}