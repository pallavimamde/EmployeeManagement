package com.techmahidra.employeemanagement.data

import androidx.lifecycle.MutableLiveData


data class AddEmployeeRequest(
    var age: String,
    var name: String,
    var salary: String
) : MutableLiveData<AddEmployeeRequest>()