package com.navin.githubtrending.mapper

/** generics - mapToView
 * from presentation to viewModel view class */
interface ProjectMapper<in P, out V> {

    fun mapToView(presentation: P): V

}