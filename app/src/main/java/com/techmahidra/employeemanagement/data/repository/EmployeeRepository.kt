package com.techmahidra.employeemanagement.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.load.HttpException
import com.techmahidra.employeemanagement.data.network.ApiService
import com.techmahidra.employeemanagement.data.response.AddEmployeeResponse
import com.techmahidra.employeemanagement.data.response.DeleteEmployeeResponse
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse
import com.techmahidra.employeemanagement.ui.addEmp.AddEmployeeFragment
import com.techmahidra.employeemanagement.ui.employeeList.EmployeeListFragment
import kotlinx.coroutines.*

class EmployeeRepository() {

    private var employees = mutableListOf<EmployeeListResponse.Data>()
    private var employeeMutableLiveData = MutableLiveData<List<EmployeeListResponse.Data>>()
    private var employeeInfoMutableLiveData = MutableLiveData<AddEmployeeResponse>()
    private var deleteEmpMutableLiveData = MutableLiveData<DeleteEmployeeResponse>()


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
                        employeeMutableLiveData.value=employees
                    }else{

                    }

                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    Log.e(TAG,e.message)
                }
            }
        }
        return employeeMutableLiveData
    }
    fun submitEmpInfo(): MutableLiveData<AddEmployeeResponse> {
        coroutineScope.launch {
            val request = AddEmployeeFragment.empInfo?.let { thisApiCorService.getAddEmployee(it) }
            withContext(Dispatchers.Main) {
                try {

                    val response = request?.await()!!
                    if (response != null) {
                        employeeInfoMutableLiveData.value = response
                    }else{

                    }

                } catch (e: HttpException) {
                    Log.e(TAG,e.message)

                } catch (e: Throwable) {
                    Log.e(TAG,e.message)
                }
            }
        }
        return employeeInfoMutableLiveData
    }

    fun deleteEmp(): MutableLiveData<DeleteEmployeeResponse> {
        coroutineScope.launch {
            val request = EmployeeListFragment.deleteEmpId?.let { thisApiCorService.deleteEmployee(it) }
            withContext(Dispatchers.Main) {
                try {

                    val response = request?.await()!!
                    if (response != null) {
                        deleteEmpMutableLiveData.value = response
                    }else{

                    }

                } catch (e: HttpException) {
                    Log.e(TAG,e.message)

                } catch (e: Throwable) {
                    Log.e(TAG,e.message)
                }
            }
        }
        return deleteEmpMutableLiveData
    }
}