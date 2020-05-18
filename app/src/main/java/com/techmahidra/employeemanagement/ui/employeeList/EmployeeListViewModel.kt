package com.techmahidra.employeemanagement.ui.employeeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.techmahidra.employeemanagement.data.AddEmployeeRequest
import com.techmahidra.employeemanagement.data.repository.EmployeeRepository
import com.techmahidra.employeemanagement.data.response.AddEmployeeResponse
import com.techmahidra.employeemanagement.data.response.DeleteEmployeeResponse
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse

class EmployeeListViewModel : ViewModel() {
    private val employeeRepository = EmployeeRepository()
    val employeeListVM: LiveData<List<EmployeeListResponse.Data>> get() = employeeRepository.getEmpMutableLiveData()
    val addEmployeeVM: LiveData<AddEmployeeResponse> get() = employeeRepository.submitEmpInfo()
    val deleteEmployeeVM: LiveData<DeleteEmployeeResponse> get() = employeeRepository.deleteEmp()

    override fun onCleared() {
        super.onCleared()
        employeeRepository.completableJob.cancel()
    }
}