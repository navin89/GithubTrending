package com.navin.data.model

/**
 * This class represents the Project.kt class model from the domain layer.
 *
 * We need to create a representation of the model in our domain layer.
 * This class ProjectEntity.kt represents the domain layer model -> Project.kt
 *
 * We do this so that this data layer is not tightly coupled to the domain layer,
 * keeping it more free if we do decide to switch model implementations at anytime.
 *
 * */


data class ProjectEntity(val id: String, val name: String, val fullname: String,
                    val starCount: String, val dateCreated: String,
                    val ownerName: String, val ownerAvatar: String,
                    val isBookmarked: Boolean)