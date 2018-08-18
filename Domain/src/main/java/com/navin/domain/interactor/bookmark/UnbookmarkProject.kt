package com.navin.domain.interactor.bookmark

import com.navin.domain.executor.PostExecutionThread
import com.navin.domain.interactor.CompletableUseCase
import com.navin.domain.repository.ProjectsRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException
import javax.inject.Inject

open class UnbookmarkProject @Inject constructor(
        private val projectsRepository: ProjectsRepository,
        private val postExecutionThread: PostExecutionThread ):
        CompletableUseCase<UnbookmarkProject.Params>(postExecutionThread) {


    public override fun buildUseCaseCompletable(params: Params?): Completable {

        if (params == null) throw IllegalArgumentException("Params can't be null")
        return projectsRepository.unBookmarkProject(params.projectId)

    }

    data class Params constructor(val projectId: String) {

        companion object {
            fun forProject(projectId: String): Params {
                return Params(projectId)
            }
        }
    }



}