package com.techmahidra.employeemanagement.data


import com.google.gson.annotations.SerializedName

data class AddEmployeeRequest(
    val age: String,
    val name: String,
    val salary: String
)