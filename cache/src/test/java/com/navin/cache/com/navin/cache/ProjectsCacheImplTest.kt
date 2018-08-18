package com.navin.cache.com.navin.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.navin.cache.ProjectsCacheImpl
import com.navin.cache.com.navin.cache.test.factory.ProjectDataFactory
import com.navin.cache.db.ProjectsDatabase
import com.navin.cache.mapper.CacheProjectMapper
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ProjectsCacheImplTest {

    // Since we are using flowables this is a good usage to promise us a return
    // of data instantly
    @get:Rule var instanceTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    private val entityMapper = CacheProjectMapper()

    // Because ProjectsCacheImpl returns ProjectCache from Data layer ..

    private val cache = ProjectsCacheImpl(database, entityMapper)


    @Test
    fun clearProjectCompletes(){
        val testObserver = cache.clearProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveProjectsCompletes(){
        val listOfProjectsEntity = listOf(ProjectDataFactory.makeProjectEntity())
        val testObserver = cache.saveProjects(listOfProjectsEntity).test()

        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData(){

        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()

        val testObserver = cache.getProjects().test()
        testObserver.assertValue(projects)

    }

    @Test
    fun getBookmarkedProjectsCompletes(){

        // Save Bookmarked Data into database first
        val bookmarkedProjects = ProjectDataFactory.makeBookmarkedProjectEntity()
        // Save a list of normal projects and bookmarked projects
        val projects = listOf(ProjectDataFactory.makeProjectEntity(), bookmarkedProjects)

        cache.saveProjects(projects).test()

        // Try to test retrieval of list of bookmarked projects
        val testObserver = cache.getBookmarkedProjects().test()
        testObserver.assertValue(listOf(bookmarkedProjects))

    }

    @Test
    fun setProjectAsBookmarkedCompletes(){


        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()

        val testObserver = cache.setProjectAsBookmarked(projects[0].id).test()
        testObserver.assertComplete()
    }

    @Test
    fun setProjectAsNotBookmarkedCompletes(){


        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()

        val testObserver = cache.setProjectAsNotBookmarked(projects[0].id).test()
        testObserver.assertComplete()
    }


    @Test
    fun areProjectsCacheReturnsData(){

        val projects = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(projects).test()

        val testObserver = cache.areProjectsCached().test()
        testObserver.assertValue(true)

    }


    @Test
    fun setLastCacheTimeExpires(){

        val setExpiredTime = 1000L
        val testObserver = cache.setLastCacheTime(setExpiredTime).test()
        testObserver.assertComplete()

    }

    @Test
    fun isProjectCacheExpiredReturnsTrue() {
        val testObserver = cache.isProjectCacheExpired().test()
        testObserver.assertValue(true)
    }

    @Test
    fun isProjectCacheExpiredReturnsFalse() {

        cache.setLastCacheTime(System.currentTimeMillis()).test()
        val testObserver = cache.isProjectCacheExpired().test()
        testObserver.assertValue(false)
    }







}