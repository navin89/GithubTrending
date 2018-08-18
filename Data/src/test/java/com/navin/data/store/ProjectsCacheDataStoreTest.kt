package com.navin.data.store

import com.navin.data.model.ProjectEntity
import com.navin.data.repository.ProjectsCache
import com.navin.data.test.factory.DataFactory
import com.navin.data.test.factory.ProjectFactory
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ProjectsCacheDataStoreTest {

    @Mock
    private lateinit var cache : ProjectsCache

    @Mock
    lateinit var cacheStore : ProjectsCacheDataStore

    @Before
    fun setup(){

        MockitoAnnotations.initMocks(this)
        cacheStore = ProjectsCacheDataStore(cache)

    }

/** Test methods area */

    @Test
    fun cacheProjectCompletes(){
        stubCacheProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))

        val testObserver = cacheStore.getProjects().test()
        testObserver.assertComplete()

    }

    @Test
    fun cacheProjectReturnsData(){

        val data = listOf(ProjectFactory.makeProjectEntity())
        stubCacheProjects(Flowable.just(data))

        val testObserver = cacheStore.getProjects().test()
        testObserver.assertValue(data)

    }

    @Test
    fun getProjectsCallCache(){
        //verify
        stubCacheProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))

        cacheStore.getProjects().test()
        verify(cache).getProjects()

    }

    @Test
    fun saveProjectsCompletes(){

        stubSaveProjects(Completable.complete())
        stubLastCachedProjects(Completable.complete())
        val testObserver = cacheStore.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
        testObserver.assertComplete()

    }

    @Test
    fun saveProjectsCallsCache(){

        val data = listOf(ProjectFactory.makeProjectEntity())
        stubSaveProjects(Completable.complete())
        stubLastCachedProjects(Completable.complete())
        cacheStore.saveProjects(data).test()
        verify(cache).saveProjects(data)

    }

    @Test
    fun clearProjectsCompletes(){

        stubClearProjects(Completable.complete())

        val testObserver = cacheStore.clearProjects().test()
        testObserver.assertComplete()

    }

    @Test
    fun clearProjectsCallsCache(){

        stubClearProjects(Completable.complete())
        cacheStore.clearProjects().test()
        verify(cache).clearProjects()
    }


    @Test
    fun getBookmarkedProjectsCompletes(){

        stubGetBookmarkedProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        val testObserver = cacheStore.getBookmarkedProjects().test()
        testObserver.assertComplete()

    }

    @Test
    fun getBookmarkedProjectsCallsCache(){

        stubGetBookmarkedProjects(Flowable.just(listOf(ProjectFactory.makeProjectEntity())))
        cacheStore.getBookmarkedProjects().test()
        verify(cache).getBookmarkedProjects()

    }

    @Test
    fun getBookmarkedProjectsReturnsData(){

        val data = listOf(ProjectFactory.makeProjectEntity())
        stubGetBookmarkedProjects(Flowable.just(data))
        val testObserver = cacheStore.getBookmarkedProjects().test()
        testObserver.assertValue(data)

    }


    @Test
    fun bookmarkProjectsCompletes(){

        stubSetBookmarkProjects(Completable.complete())

        val testObserver = cacheStore.setProjectAsBookmarked(DataFactory.randomString()).test()
        testObserver.assertComplete()

    }

    @Test
    fun bookmarkProjectsCallsCache(){

        val data = DataFactory.randomString()
        stubSetBookmarkProjects(Completable.complete())

        cacheStore.setProjectAsBookmarked(data).test()
        verify(cache).setProjectAsBookmarked(data)

    }

    @Test
    fun unBookmarkProjectCompletes(){

        stubSetUnBookmarkProjects(Completable.complete())

        val testObserver = cacheStore.setProjectAsNotBookmarked(DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun unBookmarkProjectCallsCache(){

        val data = DataFactory.randomString()
        stubSetUnBookmarkProjects(Completable.complete())

        cacheStore.setProjectAsNotBookmarked(data).test()
        verify(cache).setProjectAsNotBookmarked(data)
    }



    /** Test methods area */

    /** STUB methods area */

    fun stubCacheProjects(flowable: Flowable<List<ProjectEntity>>){

        whenever(cache.getProjects())
                .thenReturn(flowable)

    }

    fun stubSaveProjects(completable: Completable){

        whenever(cache.saveProjects(any()))
                .thenReturn(completable)

    }

    fun stubLastCachedProjects(completable: Completable){

        whenever(cache.setLastCacheTime(any()))
                .thenReturn(completable)

    }

    fun stubClearProjects(completable: Completable) {

        whenever(cache.clearProjects())
                .thenReturn(completable)
    }

    fun stubGetBookmarkedProjects(flowable: Flowable<List<ProjectEntity>>){

        whenever(cache.getBookmarkedProjects())
                .thenReturn(flowable)

    }

    fun stubSetBookmarkProjects(completable: Completable) {

        whenever(cache.setProjectAsBookmarked(any()))
                .thenReturn(completable)
    }

    fun stubSetUnBookmarkProjects(completable: Completable) {

        whenever(cache.setProjectAsNotBookmarked(any()))
                .thenReturn(completable)

    }





}