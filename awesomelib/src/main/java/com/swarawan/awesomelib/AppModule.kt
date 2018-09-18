package com.swarawan.awesomelib

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rio Swarawan on 9/18/18.
 */
@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesContext(): Context = context
}