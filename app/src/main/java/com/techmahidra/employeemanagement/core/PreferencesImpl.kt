package com.techmahidra.employeemanagement.core

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.techmahidra.employeemanagement.data.AddEmployeeRequest

class PreferencesImpl {
    var sharedPreference: SharedPreferences
    var editor: SharedPreferences.Editor
    val MY_PREFS = "MY_PREFS"
    private val USER_DATA = "USER_DATA"
    private var defaultString: String? = null
    private val gson = Gson()


    constructor(context: Context) {
        sharedPreference = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE)
        editor = sharedPreference.edit()
        //aesEncryption = AESEncryption()
    }
    fun putUserDetails(user: AddEmployeeRequest?) {
        if (user != null) {
            putString(USER_DATA, gson.toJson(user))
        } else {
            putString(USER_DATA, defaultString!!)
        }
    }
    fun getUserDetails(): AddEmployeeRequest? {
        val userDataString = getString(USER_DATA)
        return if (userDataString != null)
            gson.fromJson(userDataString, AddEmployeeRequest::class.java)
        else
            null
    }
    fun getString(key: String): String? {
        if (sharedPreference.contains(key))
            return sharedPreference.getString(key, defaultString)
        else
            return defaultString
    }
    fun putString(Key: String, value: String) {
        editor.putString(Key, value)
        editor.apply()
    }

}