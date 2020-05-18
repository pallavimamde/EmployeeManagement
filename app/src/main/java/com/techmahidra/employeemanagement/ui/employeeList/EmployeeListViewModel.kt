package com.techmahidra.employeemanagement.ui.employeeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.techmahidra.employeemanagement.data.repository.EmployeeRepository
import com.techmahidra.employeemanagement.data.response.AddEmployeeResponse
import com.techmahidra.employeemanagement.data.response.DeleteEmployeeResponse
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse

/*
* EmployeeListViewModel - This class get data and save in the form of livedata
* */
class EmployeeListViewModel : ViewModel() {
    // repository instance
    private val employeeRepository = EmployeeRepository()

    // get employee list from server and save in livedata
    val employeeListVm: LiveData<List<EmployeeListResponse.Data>> get() = employeeRepository.getEmpMutableLiveData()

    // add employee to database and save response in livedata
    val addEmployeeVm: LiveData<AddEmployeeResponse> get() = employeeRepository.submitEmpInfo()

    // delete employee from db
    val deleteEmployeeVm: LiveData<DeleteEmployeeResponse> get() = employeeRepository.deleteEmp()

    // clear repository jobs
    override fun onCleared() {
        super.onCleared()
        employeeRepository.completableJob.cancel()
    }
}