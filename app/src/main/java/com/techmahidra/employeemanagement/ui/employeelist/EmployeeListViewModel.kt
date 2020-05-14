package com.techmahidra.employeemanagement.ui.employeelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.techmahidra.employeemanagement.data.repository.EmployeeRepository
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse

class EmployeeListViewModel : ViewModel(){
    val employeeRepository : EmployeeRepository()
    val employeeList : LiveData<List<EmployeeListResponse.Data>> get() = employeeRepository.getEmpMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        employeeRepository.completableJob.cancel()
    }
}