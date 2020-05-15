package com.techmahidra.employeemanagement.core

import android.app.Application
import android.content.Context

/* *
* TelstraApplication - Base application, create singleton instance of class
* */
class EmployeeApplication : Application() {
    lateinit var sharedPrefs : PreferencesImpl

    init {
        instance = this
    }


    companion object {
        private var instance: EmployeeApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        sharedPrefs = PreferencesImpl(this)
    }

    fun getEmpInfo(): PreferencesImpl {
        return sharedPrefs
    }





}