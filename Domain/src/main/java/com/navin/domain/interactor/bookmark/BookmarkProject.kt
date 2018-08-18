package com.navin.domain.interactor.bookmark

import com.navin.domain.executor.PostExecutionThread
import com.navin.domain.interactor.CompletableUseCase
import com.navin.domain.repository.ProjectsRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * Constructor takes two parameters: ProjectRepository, PostExecutionThread
 *
 * ProjectRepository is our abstracted access point for outside data layers and domain layer contract for this layers
 *
 * PostExecutionThread instance to be thrown into the inherited base class use-case;
 * Refer to use-case class for more info.
 *
 * This is Completable type so there is no data type to be returned but just the params must be allocated
 *
 * */

open class BookmarkProject @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        postExecutionThread: PostExecutionThread ):
        CompletableUseCase<BookmarkProject.Params>(postExecutionThread) {


    public override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Params can't be null")
        return projectsRepository.bookmarkProject(params.projectId)
    }


    // Providing a way for an external class to instantiate an instance
    // and pass parameters into this use-case class
    //
    data class Params constructor(val projectId: String) {
        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }




}