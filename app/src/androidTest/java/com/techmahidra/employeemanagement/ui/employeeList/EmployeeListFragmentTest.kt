package com.techmahidra.employeemanagement.ui.employeeList

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.techmahidra.employeemanagement.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmployeeListFragmentTest {

    @Test fun clickFullscreen_ShouldDelegateToViewModel() {
        launchFragmentInContainer<EmployeeListFragment>()

        onView(withId(R.id.search_emp))
            .check(matches(isDisplayed()))
    }


}