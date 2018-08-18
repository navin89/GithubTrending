package com.navin.presentation.test.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.navin.domain.interactor.browse.GetBookmarkedProjects
import com.navin.domain.model.Project
import com.navin.presentation.BrowseBookmarkedProjectsViewModel
import com.navin.presentation.mapper.ProjectViewMapper
import com.navin.presentation.model.ProjectView
import com.navin.presentation.state.ResourceState
import com.navin.presentation.test.factory.DataFactory
import com.navin.presentation.test.factory.ProjectDataFactory
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor


@RunWith(JUnit4::class)
class BrowseBookmarkedProjectsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    var getBookmarkedProjects = mock<GetBookmarkedProjects>()
    var mapper = mock<ProjectViewMapper>()
    var projectViewModel = BrowseBookmarkedProjectsViewModel(
            getBookmarkedProjects, mapper)


    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()

    @Test
    fun fetchProjectsExecutesUseCase() {
        projectViewModel.fetchProjects()

        verify(getBookmarkedProjects, times(1)).execute(any(), eq(null))
    }

    @Test
    fun fetchProjectsReturnsSuccess() {
        val projects = ProjectDataFactory.makeProjectList(2)
        val projectViews = ProjectDataFactory.makeProjectViewList(2)
        stubProjectMapperMapToView(projectViews[0], projects[0])
        stubProjectMapperMapToView(projectViews[1], projects[1])

        projectViewModel.fetchProjects()

        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)

        assertEquals(ResourceState.SUCCESS,
                projectViewModel.getProjects().value?.state)
    }

    @Test
    fun fetchProjectsReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        val projectViews = ProjectDataFactory.makeProjectViewList(2)
        stubProjectMapperMapToView(projectViews[0], projects[0])
        stubProjectMapperMapToView(projectViews[1], projects[1])

        projectViewModel.fetchProjects()

        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)

        assertEquals(projectViews,
                projectViewModel.getProjects().value?.data)
    }

    @Test
    fun fetchProjectsReturnsError() {
        projectViewModel.fetchProjects()

        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR,
                projectViewModel.getProjects().value?.state)
    }

    @Test
    fun fetchProjectsReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        projectViewModel.fetchProjects()

        verify(getBookmarkedProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assertEquals(errorMessage,
                projectViewModel.getProjects().value?.message)
    }

    private fun stubProjectMapperMapToView(projectView: ProjectView,
                                           project: Project) {
        whenever(mapper.mapToView(project))
                .thenReturn(projectView)
    }


}