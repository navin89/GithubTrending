package com.navin.cache.mapper

import com.navin.cache.model.CachedProject
import com.navin.data.model.ProjectEntity
import com.navin.data.repository.ProjectsCache
import javax.inject.Inject

class CacheProjectMapper @Inject constructor(): CacheMapper<CachedProject, ProjectEntity> {
    override fun mapToCache(type: ProjectEntity): CachedProject {
        return CachedProject(type.id, type.name, type.fullname, type.starCount,
                type.dateCreated, type.ownerName, type.ownerAvatar, type.isBookmarked)
    }

    override fun mapFromCache(type: CachedProject): ProjectEntity {

        return ProjectEntity(type.id, type.name, type.fullName, type.starCount,
                type.dateCreated, type.ownerName, type.ownerAvatar, type.isBookmarked)
    }


}