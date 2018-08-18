package com.navin.cache.com.navin.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.navin.cache.com.navin.cache.test.factory.ProjectDataFactory
import com.navin.cache.db.ProjectsDatabase
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedProjectsDaoTest {

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()


    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java )
            .allowMainThreadQueries()
            .build()

    @After
    fun closeDB(){
        database.close()
    }

    @Test
    fun getProjectsReturnsData(){

        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))

        val testObserver = database.cachedProjectsDao().getProjects().test()
        testObserver.assertValue(listOf(project))

    }

    @Test
    fun deleteProjectClearsData(){
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))

        database.cachedProjectsDao().deleteProjects()

        // check if its empty - it should be to pass.
        val testObserver = database.cachedProjectsDao().getProjects().test()
        testObserver.assertValue(emptyList())
    }

    @Test
    fun getBookmarkedProjectsReturnsData(){

        val project = ProjectDataFactory.makeCachedProject()
        val bookmarkProject = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project, bookmarkProject))

        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(bookmarkProject))
    }

    @Test
    fun setProjectAsBookmarkedSavesData(){

        // over here we creating an un-bookmarked project
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkedStatus(true, project.id)

        // so over here we forcefully set it to true
        project.isBookmarked = true

        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(listOf(project))

    }

    @Test
    fun setProjectAsNotBookmarkedSavesData(){

        // over here we creating an un-bookmarked project
        val project = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkedStatus(false, project.id)

        // so over here we forcefully set it to false
        project.isBookmarked = false

        // then it should return emptyList()
        val testObserver = database.cachedProjectsDao().getBookmarkedProjects().test()
        testObserver.assertValue(emptyList())
    }


}