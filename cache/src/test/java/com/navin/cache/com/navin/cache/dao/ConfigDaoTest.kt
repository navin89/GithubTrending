package com.navin.cache.com.navin.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.navin.cache.com.navin.cache.test.factory.ConfigDataFactory
import com.navin.cache.db.ProjectsDatabase
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ConfigDaoTest {

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
    fun getConfigReturnsCorrectData(){

        val config = ConfigDataFactory.makeCachedConfig()
        database.configDao().insertConfig(config)

        val testObserver = database.configDao().getConfig().test()
        testObserver.assertValue(config)
    }


}