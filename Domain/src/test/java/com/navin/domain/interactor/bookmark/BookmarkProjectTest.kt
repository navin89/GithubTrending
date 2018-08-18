package com.navin.domain.interactor.bookmark

import com.navin.domain.executor.PostExecutionThread
import com.navin.domain.interactor.browse.GetBookmarkedProjects
import com.navin.domain.repository.ProjectsRepository
import com.navin.domain.test.ProjectDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BookmarkProjectTest {

    private lateinit var bookmarkedProjects: BookmarkProject
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        bookmarkedProjects = BookmarkProject(projectsRepository, postExecutionThread)
    }


    @Test
    fun bookmarkProjectCompletes(){

        stubBookmarkProject(Completable.complete())
        val testObserver = bookmarkedProjects.buildUseCaseCompletable(
                BookmarkProject.Params.forProject(ProjectDataFactory.randomUuid())
        ).test()

        testObserver.assertComplete()

    }


    @Test(expected = IllegalArgumentException::class)
    fun bookmarkProjectExceptionTest(){

        bookmarkedProjects.buildUseCaseCompletable().test()
    }

    private fun stubBookmarkProject(completable: Completable){

        whenever(projectsRepository.bookmarkProject(any()))
                .thenReturn(completable)

    }


}