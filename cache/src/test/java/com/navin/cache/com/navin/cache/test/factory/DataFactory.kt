package com.navin.cache.com.navin.cache.test.factory

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    fun randomInt(): Int {

        return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
    }

    fun randomLong(): Long {
        return randomInt().toLong()
    }


    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }


    fun makeStringList(count: Int): List<String> {

        val items = mutableListOf<String>()
        repeat(count){
            items.add(randomString())
        }

        return items
    }

}