package com.navin.presentation.test.factory

import com.navin.domain.model.Project
import com.navin.presentation.model.ProjectView

object ProjectDataFactory {

    fun makeProjectView(): ProjectView {

        return ProjectView(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomBoolean())
    }

    fun makeProject(): Project {

        return Project(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomBoolean())
    }

    // make a list for project and projectView
    fun makeProjectViewList(count: Int): List<ProjectView> {

        val projectViewListHolder = mutableListOf<ProjectView>()
        repeat(count){
            projectViewListHolder.add(makeProjectView())
        }
        return projectViewListHolder
    }

    fun makeProjectList(count: Int): List<Project> {

        val projectListHolder = mutableListOf<Project>()
        repeat(count){
            projectListHolder.add(makeProject())
        }
        return projectListHolder
    }


}