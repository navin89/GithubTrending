package com.navin.presentation.state

import javax.inject.Inject

class Resource<out T> @Inject constructor (val state: ResourceState,
                                           val data: T?,
                                           val message: String? )