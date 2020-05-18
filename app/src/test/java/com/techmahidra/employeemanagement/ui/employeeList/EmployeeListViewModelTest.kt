package com.techmahidra.employeemanagement.ui.employeeList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.techmahidra.employeemanagement.data.network.ApiService
import com.techmahidra.employeemanagement.data.repository.EmployeeRepository
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

    @Test
    fun fetchRepositories_positiveResponse() {
        `when`(this.apiService.getEmployeeList()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.any<EmployeeRepository>())
        }

        val observer = mock(Observer::class.java) as Observer<List<EmployeeListResponse.Data>>
        this.employeeListViewModel.employeeListVM.observeForever(observer)

        this.employeeListViewModel.employeeListVM

        assertNotNull(this.employeeListViewModel.featureResponse.value)
    }

    @Test
    fun fetchRepositories_error() {
        `when`(this.apiService.getEmployeeList()).thenAnswer {
            return@thenAnswer Maybe.just(ArgumentMatchers.any<EmployeeRepository>())
        }

        val observer = mock(Observer::class.java) as Observer<List<EmployeeListResponse.Data>>
        this.employeeListViewModel.employeeListVM.observeForever(observer)

        this.employeeListViewModel.employeeListVM

        assertNotNull(this.employeeListViewModel.featureResponse.value)
        assertNotNull(this.employeeListViewModel.featureResponse.value)
        assert(this.employeeListViewModel.featureResponse.value?.error is String)
    }


}