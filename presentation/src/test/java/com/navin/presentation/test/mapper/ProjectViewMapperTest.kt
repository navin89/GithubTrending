package com.navin.presentation.test.mapper

import com.navin.presentation.mapper.ProjectViewMapper
import com.navin.presentation.test.factory.ProjectDataFactory
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ProjectViewMapperTest {

    private val projectMapper = ProjectViewMapper()


    @Test
    fun mapToViewMapsData(){

        val projectModel = ProjectDataFactory.makeProject()
        val projectView = projectMapper.mapToView(projectModel)

        assertEquals(projectModel.id, projectView.id)
        assertEquals(projectModel.name, projectView.name)
        assertEquals(projectModel.fullname, projectView.fullname)
        assertEquals(projectModel.dateCreated, projectView.dateCreated)
        assertEquals(projectModel.starCount, projectView.starCount)
        assertEquals(projectModel.ownerName, projectView.ownerName)
        assertEquals(projectModel.ownerAvatar, projectView.ownerAvatar)
        assertEquals(projectModel.isBookmarked, projectView.isBookmarked)

    }



}