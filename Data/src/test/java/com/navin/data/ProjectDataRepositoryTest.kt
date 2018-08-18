package com.navin.data

import com.navin.data.mapper.ProjectMapper
import com.navin.data.model.ProjectEntity
import com.navin.data.repository.ProjectsCache
import com.navin.data.repository.ProjectsDataStore
import com.navin.data.store.ProjectsDataStoreFactory
import com.navin.data.test.factory.DataFactory
import com.navin.data.test.factory.ProjectFactory
import com.navin.domain.model.Project
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProjectDataRepositoryTest {

    @Mock
    lateinit var dataStore : ProjectsDataStore

    @Mock
    private val mapper = ProjectMapper()
    @Mock
    lateinit var cache : ProjectsCache
    @Mock
    lateinit var factory: ProjectsDataStoreFactory

    private lateinit var repository: ProjectsDataRepository

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        repository = ProjectsDataRepository(mapper, cache, factory)

        // getting factory methods to be alive
        stubFactoryGetDataStore()

        stubAreProjectsCached(Single.just(false))
        stubIsProjectsExpired(Single.just(false))
        stubGetCacheStore()
        stubSaveProject(Completable.complete())

    }

    @Test
    fun getProjectCompletes(){
        stubGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapper(ProjectFactory.makeProject(), any())

        val testObserver = repository.getProjects().test()
        testObserver.assertComplete()

    }

    @Test
    fun getProjectReturnsData(){
        val projectEntity = ProjectFactory.makeProjectEntity()
        val project = ProjectFactory.makeProject()
        stubGetProjects(Flowable.just(listOf(projectEntity)))
        stubMapper(project, projectEntity)

        val testObserver = repository.getProjects().test()
        testObserver.assertValue(listOf(project))

    }

    @Test
    fun getAllBookmarkedProjectCompletes(){
        stubGetProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        stubMapper(ProjectFactory.makeProject(), any())

        val testObserver = repository.getProjects().test()
        testObserver.assertComplete()

    }

    @Test
    fun getAllBookmarkedProjectReturnsData(){

        val projectEntity = ProjectFactory.makeProjectEntity()
        val project = ProjectFactory.makeProject()

        stubGetProjects(Flowable.just(listOf(projectEntity)))
        stubMapper(project, projectEntity)

        val testObserver = repository.getProjects().test()
        testObserver.assertValue(listOf(project))

    }

    @Test
    fun bookmarkProjectTest(){

        stubBookmarkingAProject(Completable.complete())

        val testObserver = repository.bookmarkProject(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }


    fun stubBookmarkingAProject(completable: Completable){

        whenever(dataStore.setProjectAsBookmarked(any()))
                .thenReturn(completable)
    }

    @Test
    fun unBookmarkProjectTest(){

        stubUnBookmarkingAProject(Completable.complete())

        val testObserver = repository.unBookmarkProject(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }


    fun stubUnBookmarkingAProject(completable: Completable){

        whenever(dataStore.setProjectAsNotBookmarked(any()))
                .thenReturn(completable)
    }


    /** Stubs for mapper and getting projects and get data store */
    private fun stubMapper(model: Project, entity: ProjectEntity){

        whenever(mapper.mapFromEntity(entity))
                .thenReturn(model)
    }

    private fun stubGetProjects(flowable: Flowable<List<ProjectEntity>>){

        whenever(dataStore.getProjects())
                .thenReturn(flowable)
    }

    private fun stubFactoryGetDataStore(){

        whenever(factory.getDataStore(any(), any()) )
                .thenReturn(dataStore)
    }

    /** Stubs for the cache boolean value && is project expired boolean value,
     *  project expiry, to save a project, getting the cache store */
    private fun stubAreProjectsCached(single: Single<Boolean>){

        whenever(cache.areProjectsCached())
                .thenReturn(single)
    }

    private fun stubIsProjectsExpired(single: Single<Boolean>){

        whenever(cache.isProjectCacheExpired())
                .thenReturn(single)
    }

    private fun stubSaveProject(completable: Completable){

        whenever(dataStore.saveProjects(any()))
                .thenReturn(completable)
    }

    private fun stubGetCacheStore(){

        whenever(factory.getCachedDataStore())
                .thenReturn(dataStore)
    }

    /** Stubs for getting bookmarked projects */
    private fun stubGetBookamrkedProjects(flowable: Flowable<List<ProjectEntity>>){
        whenever(dataStore.getBookmarkedProjects())
                .thenReturn(flowable)
    }







}