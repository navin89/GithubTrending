package com.navin.data.test.factory

import com.navin.data.model.ProjectEntity
import com.navin.domain.model.Project

object ProjectFactory {


    fun makeProjectEntity(): ProjectEntity {

        return ProjectEntity(DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomBoolean())


    }

    fun makeProject(): Project {

        return Project(DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomBoolean())

    }






}