package com.navin.githubtrending.injection.module

import com.navin.data.ProjectsDataRepository
import com.navin.domain.repository.ProjectsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {


    @Binds
    abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectsRepository
}