package com.navin.data.test.factory.store

import com.navin.data.store.ProjectsCacheDataStore
import com.navin.data.store.ProjectsDataStoreFactory
import com.navin.data.store.ProjectsRemoteDataStore
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class ProjectDataStoreFactoryTest {

    private lateinit var factory:ProjectsDataStoreFactory
    @Mock
    lateinit var cacheStore:ProjectsCacheDataStore
    @Mock
    lateinit var remoteStore:ProjectsRemoteDataStore

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        factory = ProjectsDataStoreFactory(cacheStore,remoteStore)
    }

    @Test
    fun getDataStoreReturnsRemoteDataWhenCacheExpired(){

        assertEquals(remoteStore, factory.getDataStore(true, true))
    }

    @Test
    fun getDataStoreReturnsRemoteDataWhenCacheNotExpired(){

        assertEquals(remoteStore, factory.getDataStore(false, false))
    }

    @Test
    fun getDataStoreReturnsCacheDataWhenCache(){

        assertEquals(cacheStore, factory.getDataStore(true, false))
    }

    @Test
    fun getCacheDataStoreReturns(){

        assertEquals(cacheStore, factory.getCachedDataStore())
    }


}