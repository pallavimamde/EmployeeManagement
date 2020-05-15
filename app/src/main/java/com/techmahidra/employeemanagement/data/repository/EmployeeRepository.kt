package com.techmahidra.employeemanagement.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.HttpException
import com.techmahidra.employeemanagement.data.AddEmployeeRequest
import com.techmahidra.employeemanagement.data.network.ApiService
import com.techmahidra.employeemanagement.data.response.AddEmployeeResponse
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse
import com.techmahidra.employeemanagement.ui.addEmp.AddEmployeeFragment
import kotlinx.coroutines.*

class EmployeeRepository() {

    private var employees = mutableListOf<EmployeeListResponse.Data>()
    private var emploeeMutableLiveData = MutableLiveData<List<EmployeeListResponse.Data>>()
    private var employee = mutableSetOf<AddEmployeeResponse>()
    private var addEmployeeMutableLiveData= MutableLiveData<MutableSet<AddEmployeeResponse>>()
    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)
    private val TAG = "EmployeeRepository"

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
                    }else{

                    }

                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    Log.e(TAG,e.message)
                }
            }
        }
        return emploeeMutableLiveData
    }
    fun submitEmpInfo(): MutableLiveData<MutableSet<AddEmployeeResponse>> {
        coroutineScope.launch {
            val request = thisApiCorService.getAddEmployee(AddEmployeeFragment.empInfo)
            withContext(Dispatchers.Main) {
                try {

                    val response = request.await()
                    if (response != null) {
                        employee = response as MutableSet<AddEmployeeResponse>
                        addEmployeeMutableLiveData.value=employee
                    }else{

                    }

                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    Log.e(TAG,e.message)
                }
            }
        }
        return addEmployeeMutableLiveData
    }
}