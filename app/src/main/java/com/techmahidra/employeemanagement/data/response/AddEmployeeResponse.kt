package com.techmahidra.employeemanagement.data.response

// Add item server response
data class AddEmployeeResponse(
    val `data`: Data,
    val status: String
) {
    data class Data(
        val age: String,
        val id: Int,
        val name: String,
        val salary: String
    )
}