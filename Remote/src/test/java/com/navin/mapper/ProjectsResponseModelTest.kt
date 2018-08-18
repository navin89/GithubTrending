package com.navin.mapper

import com.navin.remote.mapper.ProjectsResponseModelMapper
import com.navin.remote.test.factory.ProjectDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ProjectsResponseModelTest {


    private val mapper = ProjectsResponseModelMapper()


    @Test
    fun testModelMappingForRemoteSourceObjects() {

        val model = ProjectDataFactory.makeProject()
        val entity = mapper.mapFromModel(model)

        assertEquals(model.id, entity.id)
        assertEquals(model.name, entity.name)
        assertEquals(model.fullName, entity.fullname)
        assertEquals(model.starGazersCount.toString(), entity.starCount)
        assertEquals(model.dateCreated, entity.dateCreated)
        assertEquals(model.owner.ownerName, entity.ownerName)
        assertEquals(model.owner.avatarUrl, entity.ownerAvatar)

    }

}