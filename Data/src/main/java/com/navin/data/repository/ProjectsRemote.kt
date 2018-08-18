package com.navin.data.repository

import com.navin.data.model.ProjectEntity
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 *  Separation of concerns are shown here
 *
 *  This is the remote layer and has only the methods that conform to
 *  data pulling and pushing to/from a remote source
 *
 *  Since Data layer has a huge responsibility on deciding what is required,
 *  we don't want to clump the data layer options(Remote and Cache) / methods into a single
 *  interface which drops the burden on any one of these(Remote and Cache) interfaces with
 *  unwanted calls.. thus avoiding bloated process calls in data layer.
 *
 * */

interface ProjectsRemote {


    fun getProjects(): Flowable<List<ProjectEntity>>


}