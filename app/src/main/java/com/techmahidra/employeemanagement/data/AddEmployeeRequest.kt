package com.techmahidra.employeemanagement.data

import androidx.lifecycle.MutableLiveData

// Add employee request object
data class AddEmployeeRequest(
    var age: String,
    var name: String,
    var salary: String
)