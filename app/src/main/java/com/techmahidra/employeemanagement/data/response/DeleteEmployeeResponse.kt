package com.techmahidra.employeemanagement.data.response


import com.google.gson.annotations.SerializedName

data class DeleteEmployeeResponse(
    val message: String,
    val status: String
)