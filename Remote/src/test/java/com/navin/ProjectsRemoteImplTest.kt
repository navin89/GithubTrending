package com.navin

import com.navin.data.model.ProjectEntity
import com.navin.remote.ProjectsRemoteImpl
import com.navin.remote.mapper.ProjectsResponseModelMapper
import com.navin.remote.model.ProjectModel
import com.navin.remote.model.ProjectsResponseModel
import com.navin.remote.service.GithubTrendingService
import com.navin.remote.test.factory.ProjectDataFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProjectsRemoteImplTest {

    @Mock
    private val mapper = ProjectsResponseModelMapper()
    @Mock
    lateinit var  remoteService : GithubTrendingService
    lateinit var projectsRemoteImpl: ProjectsRemoteImpl


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)

        projectsRemoteImpl = ProjectsRemoteImpl(mapper, remoteService)

    }

    @Test
    fun getProjectCompletes(){

        stubGetGithubTrendingServiceSearchRepositories(Flowable.just(ProjectDataFactory.makeProjectResponeModel()))
        stubProjectResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())


        val testObserver = projectsRemoteImpl.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun projectCallsServer(){

        stubGetGithubTrendingServiceSearchRepositories(Flowable.just(ProjectDataFactory.makeProjectResponeModel()))
        stubProjectResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())

        projectsRemoteImpl.getProjects().test()
        verify(remoteService).searchRepositories(any(), any(), any())

    }

    @Test
    fun projectCallsServerWithCorrectParameters(){

        stubGetGithubTrendingServiceSearchRepositories(Flowable.just(ProjectDataFactory.makeProjectResponeModel()))
        stubProjectResponseModelMapperMapFromModel(any(), ProjectDataFactory.makeProjectEntity())

        projectsRemoteImpl.getProjects().test()
        verify(remoteService).searchRepositories("language:kotlin", "stars", "desc")

    }

    @Test
    fun getProjectsReturnData(){

        val responseModel = ProjectDataFactory.makeProjectResponeModel()
        stubGetGithubTrendingServiceSearchRepositories(Flowable.just(responseModel))
        val entities = mutableListOf<ProjectEntity>()
        //loop thru each item in our response
        responseModel.items.forEach {
            println("The element is ${it.dateCreated}")
            val entity = ProjectDataFactory.makeProjectEntity()
            entities.add(entity)
            // over here testing if projectModel and projectEntity is similar
            stubProjectResponseModelMapperMapFromModel(it, entity)
        }

        val testObserver = projectsRemoteImpl.getProjects().test()
        testObserver.assertValue(entities) // test if entities are returned
    }

    private fun stubGetGithubTrendingServiceSearchRepositories(flowable: Flowable<ProjectsResponseModel>){

        whenever(remoteService.searchRepositories(any(), any(), any()))
                .thenReturn(flowable)

    }

    private fun stubProjectResponseModelMapperMapFromModel(model: ProjectModel, entity: ProjectEntity){

        whenever(mapper.mapFromModel(model))
                .thenReturn(entity)

    }







}