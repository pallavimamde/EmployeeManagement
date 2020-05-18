package com.techmahidra.employeemanagement.ui.employeeList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.techmahidra.employeemanagement.data.network.ApiService
import com.techmahidra.employeemanagement.data.repository.EmployeeRepository
import com.techmahidra.employeemanagement.data.response.AddEmployeeResponse
import com.techmahidra.employeemanagement.data.response.DeleteEmployeeResponse
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import org.mockito.Mockito.*
import java.net.SocketException
import io.reactivex.Maybe


@RunWith(JUnit4::class)
internal class EmployeeListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Spy
    lateinit var apiService: ApiService

    lateinit var employeeListViewModel: EmployeeListViewModel

    lateinit var employeeRepository: EmployeeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.employeeRepository = EmployeeRepository()
        this.employeeListViewModel = EmployeeListViewModel()
    }

    // get employee list with positive and negative response handled
    @Test
    fun employeeListRepositories_positiveResponse() {
        `when`(this.apiService.getEmployeeList()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.any<EmployeeRepository>())
        }

        val observer = mock(Observer::class.java) as Observer<List<EmployeeListResponse.Data>>
        this.employeeListViewModel.employeeListVM.observeForever(observer)

        this.employeeListViewModel.employeeListVM

        assertNotNull(this.employeeListViewModel.employeeListVM.value)
    }

    // create employee with positive and negative response handled
    @Test
    fun employeeAddRepositories_positiveResponse() {
        `when`(this.apiService.getEmployeeList()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.any<EmployeeRepository>())
        }

        val observer = mock(Observer::class.java) as Observer<AddEmployeeResponse>
        this.employeeListViewModel.addEmployeeVM.observeForever(observer)

        this.employeeListViewModel.addEmployeeVM

        assertNotNull(this.employeeListViewModel.addEmployeeVM.value)
    }

    // delete employee list with positive and negative response handled
    @Test
    fun employeeDeleteRepositories_positiveResponse() {
        `when`(this.apiService.getEmployeeList()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.any<EmployeeRepository>())
        }

        val observer = mock(Observer::class.java) as Observer<DeleteEmployeeResponse>
        this.employeeListViewModel.deleteEmployeeVM.observeForever(observer)

        this.employeeListViewModel.deleteEmployeeVM

        assertNotNull(this.employeeListViewModel.deleteEmployeeVM.value)
    }


}