package com.navin.presentation.mapper

interface mapper<in D, out P> {


    fun mapToView(type: D): P

}