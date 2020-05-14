package com.techmahidra.employeemanagement.data.repository

import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.HttpException
import com.techmahidra.employeemanagement.data.network.ApiService
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse
import kotlinx.coroutines.*

class EmployeeRepository() {
    private var employees = mutableListOf<EmployeeListResponse.Data>()
    private var emploeeMutableLiveData = MutableLiveData<List<EmployeeListResponse.Data>>()
    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private val thisApiCorService by lazy {
        ApiService.createCorService()
    }

    fun getEmpMutableLiveData():MutableLiveData<List<EmployeeListResponse.Data>> {
        coroutineScope.launch {
            val request = thisApiCorService.getEmployeeList()
            withContext(Dispatchers.Main) {
                try {

                    val response = request.await()
                    if (response != null) {
                        employees = response.data as MutableList<EmployeeListResponse.Data>
                        emploeeMutableLiveData.value=employees
                    }

                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    // Log error //)
                }
            }
        }
        return emploeeMutableLiveData
    }
}