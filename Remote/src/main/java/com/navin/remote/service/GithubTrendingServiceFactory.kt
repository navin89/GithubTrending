package com.navin.remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * Since we are using OOP paradigm, as it is for the codes + functions/methods below;
 * you will notice each method is taking reference from each other.
 * In this class our focus is to enable logging when we are in debug mode and
 * HttpLoggingInterceptor.Level.NONE logging -> while we're not in debug mode.
 *
 * refactoring
 *
 *
 * */

class GithubTrendingServiceFactory {




    fun makeGithubTrendingService(isDebug: Boolean): GithubTrendingService {
        val okHttpClient = makeOkHttpClient(
                makeLoggingInterceptor(isDebug))

        return makeGithubTrendingService(okHttpClient)
    }


    fun makeGithubTrendingService(okHttpClient: OkHttpClient): GithubTrendingService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        return retrofit.create(GithubTrendingService::class.java)

    }

    fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {

        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(120, TimeUnit.MILLISECONDS)
                .readTimeout(120, TimeUnit.MILLISECONDS)
                .build()

    }



    fun makeLoggingInterceptor(isDebug: Boolean) : HttpLoggingInterceptor {

        val loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.level = if (isDebug) {

            HttpLoggingInterceptor.Level.BODY
        }else {
            HttpLoggingInterceptor.Level.NONE
        }

        return loggingInterceptor
    }

}