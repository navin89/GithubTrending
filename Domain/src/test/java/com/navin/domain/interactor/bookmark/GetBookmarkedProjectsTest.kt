package com.navin.domain.interactor.bookmark

import com.navin.domain.executor.PostExecutionThread
import com.navin.domain.interactor.browse.GetBookmarkedProjects
import com.navin.domain.model.Project
import com.navin.domain.repository.ProjectsRepository
import com.navin.domain.test.ProjectDataFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetBookmarkedProjectsTest {

    private lateinit var getBookmarkedProjects: GetBookmarkedProjects
    @Mock lateinit var projectsRepository: ProjectsRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        getBookmarkedProjects = GetBookmarkedProjects(projectsRepository, postExecutionThread)
    }


    @Test
    fun getBookmarkedProjectCompletes(){
        stubGetBookmarkedProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnData(){

        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetBookmarkedProjects(Observable.just(projects))

        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()

        // Expected value have returned??
        testObserver.assertValue(projects)
    }


    fun stubGetBookmarkedProjects(observable: Observable<List<Project>>){

        whenever(projectsRepository.getAllBookmarkedProjects())
                .thenReturn(observable)

    }






}