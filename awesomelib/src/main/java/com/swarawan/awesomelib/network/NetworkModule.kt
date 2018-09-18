@file:JvmName("NetworkModule")

package com.swarawan.awesomelib.network

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.swarawan.awesomelib.BuildConfig
import com.swarawan.awesomelib.R
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Rio Swarawan on 9/18/18.
 */

@Module
class NetworkModule {
    companion object {
        const val CACHE_DIR_SIZE_30MB = 30 * 1024 * 1024
        const val KEEP_ALIVE_DURATION = (30 * 1000).toLong()
        const val MAX_IDLE_CONNECTIONS = 10
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient,
                         gson: Gson,
                         networkConfig: NetworkConfig): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient())
            .baseUrl(networkConfig.baseUrl)
            .build()

    @Provides
    @Singleton
    fun providesOkHttpClient(cache: Cache,
                             connectionPool: ConnectionPool,
                             loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val timeout = 20
        return OkHttpClient.Builder()
                .readTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .writeTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .connectTimeout(timeout.toLong(), TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .connectionPool(connectionPool)
                .build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson =
            GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    @Provides
    @Singleton
    fun providesCache(context: Context): Cache =
            Cache(context.externalCacheDir ?: context.cacheDir, CACHE_DIR_SIZE_30MB.toLong())

    @Provides
    @Singleton
    fun providesConnectionPool(): ConnectionPool =
            ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MILLISECONDS)

    @Provides
    @Singleton
    fun providesNetworkConfig(context: Context): NetworkConfig =
            NetworkConfig(context.getString(R.string.server_url))

    @Provides
    @Singleton
    fun providesHttpLogginInterceptor(context: Context): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = getLevel(context.getString(R.string.okhttp_log_level))
        return interceptor
    }

    private fun getLevel(level: String?): HttpLoggingInterceptor.Level =
            when (level) {
                "NONE" -> HttpLoggingInterceptor.Level.NONE
                "BASIC" -> HttpLoggingInterceptor.Level.BASIC
                "HEADERS" -> HttpLoggingInterceptor.Level.HEADERS
                "BODY" -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
}