package com.navin.data.store

import com.navin.data.model.ProjectEntity
import com.navin.data.repository.ProjectsRemote
import com.navin.data.test.factory.DataFactory
import com.navin.data.test.factory.ProjectFactory
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
class ProjectsRemoteDataStoreTest {

    @Mock
    private lateinit var remote : ProjectsRemote

    @Mock
    lateinit var remoteDataStore : ProjectsRemoteDataStore

    @Before
    fun setup(){

        MockitoAnnotations.initMocks(this)
        remoteDataStore = ProjectsRemoteDataStore(remote)

    }


    @Test
    fun getProjectsFromRemote() {

        stubGetProjectsFromRemote(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))

        val testObserver = remoteDataStore.getProjects().test()
        testObserver.assertComplete()

    }

    @Test
    fun getProjectsFromRemoteReturnsData() {

        val data = listOf(ProjectFactory.makeProjectEntity())
        stubGetProjectsFromRemote(Flowable.just(data))

        val testObserver = remoteDataStore.getProjects().test()
        testObserver.assertValue(data)

    }

    @Test
    fun getProjectsFromRemoteCallsRemote() {

        stubGetProjectsFromRemote(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))

        remoteDataStore.getProjects().test()
        verify(remote).getProjects()

    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearProjectsForRemote(){
        remoteDataStore.clearProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveProjectsForRemote(){
        remoteDataStore.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
    }


    @Test(expected = UnsupportedOperationException::class)
    fun getProjectsForRemote(){
        remoteDataStore.getBookmarkedProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectsBookmarkedForRemote(){
        remoteDataStore.setProjectAsBookmarked(DataFactory.randomString()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setProjectsUnBookmarkedForRemote(){
        remoteDataStore.setProjectAsNotBookmarked(DataFactory.randomString()).test()
    }



    fun stubGetProjectsFromRemote(flowable: Flowable<List<ProjectEntity>>) {

        whenever(remote.getProjects())
                .thenReturn(flowable)

    }




}