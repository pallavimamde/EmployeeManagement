package com.techmahidra.employeemanagement.data.network

import androidx.lifecycle.MutableLiveData
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.techmahidra.employeemanagement.data.AddEmployeeRequest
import com.techmahidra.employeemanagement.data.response.AddEmployeeResponse
import com.techmahidra.employeemanagement.data.response.DeleteEmployeeResponse
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

// Api constants
const val API_RETRIVE_EMP_LIST = "employees"
const val API_DELETE_EMP = "delete/{id}"
const val API_CREATE_EMP = "create"
const val BASE_URL = "http://dummy.restapiexample.com/api/v1/"


interface ApiService {


    // Det employee list api call
    @GET(API_RETRIVE_EMP_LIST)
    fun getEmployeeList(): Deferred<EmployeeListResponse>


    // Add employee api call
    @POST(API_CREATE_EMP)
    fun getAddEmployee(@Body addEmployeeRequest: AddEmployeeRequest): Deferred<AddEmployeeResponse>

    // Delete employee api call
    @GET(API_DELETE_EMP)
    fun deleteEmployee(@Path("id") id: Int): Deferred<DeleteEmployeeResponse>


    // Create Retrofit object
    companion object {

        fun createCorService(): ApiService {

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(ApiService::class.java)
        }
    }
}