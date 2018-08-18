package com.navin.data.mapper

/**
 * This mapper class is created to map instances from models between data and domain layer
 *
 * We can't live without mapper classes because throwing single object reference from two layers,
 * which has two different model representations, can cause unwanted complexity..
 *
 * thus we create mapper interfaces so that each of our mapper classes are enforced
 * to implement the same implementations between each mapper class. It also helps us to keep a sense
 * of consistency between mapper classes and aiding us in method generations..
 *
 * */

interface EntityMapper<E, D> {


    /** from data layer -> map entity to domain layer
     *  should be used when data is passed from data layer to domain layer
     * */
    fun mapFromEntity(entity: E) : D

    /** from domain layer map entity to data layer
     *  should be used when data is passed from domain to data layer
     * */

    fun mapToEntity(domain: D) : E



}