package com.navin.cache.com.navin.cache.test.factory

import com.navin.cache.model.Config

object ConfigDataFactory {

    fun makeCachedConfig(): Config {

        return Config(DataFactory.randomInt(), DataFactory.randomLong())
    }


}