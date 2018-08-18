package com.navin.remote.mapper

import com.navin.data.model.ProjectEntity
import com.navin.remote.model.ProjectModel
import javax.inject.Inject

open class ProjectsResponseModelMapper @Inject constructor(): ModelMapper<ProjectModel, ProjectEntity> {


    override fun mapFromModel(model: ProjectModel): ProjectEntity {

        return ProjectEntity(model.id, model.name, model.fullName, model.starGazersCount.toString(),
                model.dateCreated, model.owner.ownerName, model.owner.avatarUrl, false)
    }
}