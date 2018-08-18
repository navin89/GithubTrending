package com.navin.domain.interactor.bookmark

import com.navin.domain.executor.PostExecutionThread
import com.navin.domain.repository.ProjectsRepository
import com.navin.domain.test.ProjectDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class UnBookmarkProjectTest {

    private lateinit var unBookmarkedProjects: UnbookmarkProject
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        unBookmarkedProjects = UnbookmarkProject(projectsRepository, postExecutionThread)
    }


    @Test
    fun bookmarkProjectCompletes(){

        stubUnBookmarkProject(Completable.complete())
        val testObserver = unBookmarkedProjects.buildUseCaseCompletable(
                UnbookmarkProject.Params.forProject(ProjectDataFactory.randomUuid())
        ).test()

        testObserver.assertComplete()

    }


    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectExceptionTest(){

        unBookmarkedProjects.buildUseCaseCompletable().test()
    }

    private fun stubUnBookmarkProject(completable: Completable){

        whenever(projectsRepository.bookmarkProject(any()))
                .thenReturn(completable)

    }


}