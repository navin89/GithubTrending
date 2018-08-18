package com.navin.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.navin.cache.db.ConfigConstants
import com.navin.cache.db.ConfigConstants.QUERY_CONFIG
import com.navin.cache.model.Config
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class ConfigDao {

    @Query(QUERY_CONFIG)
    abstract fun getConfig(): Single<Config>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertConfig(config: Config)

}