package com.navin.data.test.factory.mapper

import com.navin.data.mapper.ProjectMapper
import com.navin.data.model.ProjectEntity
import com.navin.data.test.factory.ProjectFactory
import com.navin.domain.model.Project
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ProjectMapperTest {

    private val mapper = ProjectMapper()


    @Test
    fun mapFromEntityMapsData(){

        val projectEntity = ProjectFactory.makeProjectEntity()
        val model = mapper.mapFromEntity(projectEntity)

        assertEqualData(projectEntity, model)

    }

    @Test
    fun mapToEntityMapsData(){

        val projectModel = ProjectFactory.makeProject()
        val entity = mapper.mapToEntity(projectModel)

        assertEqualData( entity, projectModel)

    }

    private fun assertEqualData(entity: ProjectEntity, model: Project){


        assertEquals(entity.id, model.id)
        assertEquals(entity.name, model.name)
        assertEquals(entity.fullname, model.fullname)
        assertEquals(entity.starCount, model.starCount)
        assertEquals(entity.dateCreated, model.dateCreated)
        assertEquals(entity.ownerAvatar, model.ownerAvatar)
        assertEquals(entity.ownerName, model.ownerName)
        assertEquals(entity.isBookmarked, model.isBookmarked)
    }


}