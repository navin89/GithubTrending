package com.navin.presentation.mapper

import com.navin.domain.model.Project
import com.navin.presentation.model.ProjectView
import javax.inject.Inject

open class ProjectViewMapper @Inject constructor(): mapper<Project, ProjectView> {



    override fun mapToView(type: Project): ProjectView {
        return ProjectView(type.id, type.name, type.fullname, type.starCount,
                type.dateCreated, type.ownerName, type.ownerAvatar, type.isBookmarked)
    }


}