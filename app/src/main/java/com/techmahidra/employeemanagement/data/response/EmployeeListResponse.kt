package com.techmahidra.employeemanagement.data.response


import com.google.gson.annotations.SerializedName


// Server call Employee list response
data class EmployeeListResponse(
    val `data`: List<Data>,
    val status: String // success
) {
    data class Data(
        @SerializedName("employee_age")
        var employeeAge: String, // 61
        @SerializedName("employee_name")
        var employeeName: String, // Tiger Nixon
        @SerializedName("employee_salary")
        var employeeSalary: String, // 320800
        val id: String, // 1
        @SerializedName("profile_image")
        val profileImage: String
    )
}