package com.navin.cache.com.navin.cache.test.factory

import com.navin.cache.model.CachedProject
import com.navin.data.model.ProjectEntity

object ProjectDataFactory {

    fun makeCachedProject(): CachedProject {

        return CachedProject(DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString()
                        ,DataFactory.randomString(),DataFactory.randomString(),
                DataFactory.randomString(),DataFactory.randomString(), DataFactory.randomBoolean())

    }

    fun makeBookmarkedCachedProject(): CachedProject {

        return CachedProject(DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString()
                ,DataFactory.randomString(),DataFactory.randomString(),
                DataFactory.randomString(),DataFactory.randomString(), true)

    }

    fun makeProjectEntity(): ProjectEntity {

        return ProjectEntity(DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString()
                ,DataFactory.randomString(),DataFactory.randomString(),
                DataFactory.randomString(),DataFactory.randomString(), DataFactory.randomBoolean())

    }

    fun makeBookmarkedProjectEntity(): ProjectEntity {

        return ProjectEntity(DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString()
                ,DataFactory.randomString(),DataFactory.randomString(),
                DataFactory.randomString(),DataFactory.randomString(), true)

    }

}