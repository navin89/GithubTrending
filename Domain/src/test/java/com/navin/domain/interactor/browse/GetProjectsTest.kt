package com.navin.domain.interactor.browse

import com.navin.domain.executor.PostExecutionThread
import com.navin.domain.interactor.browse.GetProjects
import com.navin.domain.model.Project
import com.navin.domain.repository.ProjectsRepository
import com.navin.domain.test.ProjectDataFactory
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class GetProjectsTest {

    private lateinit var getProjects: GetProjects
    @Mock
    lateinit var projectsRepository: ProjectsRepository
    @Mock
    lateinit var postExecutionThread: PostExecutionThread


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getProjects = GetProjects(projectsRepository, postExecutionThread)
    }


    @Test
    fun getProjectCompletes(){

        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(8)))
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()

    }

    @Test
    fun getProjectReturnData(){

        val projects = ProjectDataFactory.makeProjectList(8)

        stubGetProjects(Observable.just(projects))

        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)

    }



    fun stubGetProjects(observable: Observable<List<Project>>) {

        whenever(projectsRepository.getProjects())
                .thenReturn(observable)
    }



}