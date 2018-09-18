package com.swarawan.collablib.deps

import com.swarawan.awesomelib.AppModule
import com.swarawan.awesomelib.network.NetworkModule
import com.swarawan.collablib.App
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rio Swarawan on 9/18/18.
 */

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class
))
interface BaseComponent {

    fun inject(application: App)
}