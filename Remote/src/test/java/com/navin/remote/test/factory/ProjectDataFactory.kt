package com.navin.remote.test.factory

import com.navin.data.model.ProjectEntity
import com.navin.remote.model.OwnerModel
import com.navin.remote.model.ProjectModel
import com.navin.remote.model.ProjectsResponseModel

object ProjectDataFactory {


    fun makeOwner(): OwnerModel {

        return OwnerModel(DataFactory.randomString(), DataFactory.randomString())
    }

    fun makeProject(): ProjectModel {

        return ProjectModel(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomInt(),DataFactory.randomString(), makeOwner() )

    }

    fun makeProjectEntity(): ProjectEntity {

        return ProjectEntity(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(), DataFactory.randomString(),
                DataFactory.randomString(),DataFactory.randomString(), DataFactory.randomBoolean())

    }

    fun makeProjectResponeModel(): ProjectsResponseModel {

        return ProjectsResponseModel(listOf(makeProject()))

    }



}