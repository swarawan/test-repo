package com.swarawan.collablib

import android.support.multidex.MultiDexApplication
import com.swarawan.collablib.deps.BaseComponent

/**
 * Created by Rio Swarawan on 9/18/18.
 */
class App : MultiDexApplication() {

    lateinit var baseComponent: BaseComponent

    override fun onCreate() {
        super.onCreate()


    }
}