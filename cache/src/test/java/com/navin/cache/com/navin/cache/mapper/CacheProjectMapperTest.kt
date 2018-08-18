package com.navin.cache.com.navin.cache.mapper

import com.navin.cache.com.navin.cache.test.factory.ProjectDataFactory
import com.navin.cache.mapper.CacheProjectMapper
import com.navin.cache.model.CachedProject
import com.navin.data.model.ProjectEntity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class CacheProjectMapperTest {

    private val mapper = CacheProjectMapper()


    @Test
    fun mapFromCacheMapsData(){

        val cacheModel = ProjectDataFactory.makeCachedProject()
        val entity = mapper.mapFromCache(cacheModel)

        stubMappingOfProjects(cacheModel, entity)

    }

    @Test
    fun mapToCacheMapsData(){

        val entity = ProjectDataFactory.makeProjectEntity()
        val cacheModel = mapper.mapToCache(entity)

        stubMappingOfProjects(cacheModel, entity)

    }


    private fun stubMappingOfProjects(model: CachedProject, project: ProjectEntity) {

        assertEquals(model.id, project.id)
        assertEquals(model.name, project.name)
        assertEquals(model.fullName, project.fullname)
        assertEquals(model.dateCreated, project.dateCreated)
        assertEquals(model.starCount, project.starCount)
        assertEquals(model.ownerName, project.ownerName)
        assertEquals(model.ownerAvatar, project.ownerAvatar)
        assertEquals(model.isBookmarked, project.isBookmarked)
    }





}