package com.navin.githubtrending.mapper

import com.navin.githubtrending.model.Project
import com.navin.presentation.model.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor(): ProjectMapper<ProjectView, Project>{


    override fun mapToView(presentation: ProjectView): Project {
        return Project(presentation.id, presentation.name, presentation.fullname, presentation.starCount, presentation.dateCreated,
                presentation.ownerName, presentation.ownerAvatar, presentation.isBookmarked)
    }


}