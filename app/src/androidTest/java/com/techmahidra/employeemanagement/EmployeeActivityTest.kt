package com.techmahidra.employeemanagement

import android.content.Intent
import androidx.appcompat.app.ActionBar
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.techmahidra.employeemanagement.ui.employeeList.EmployeeListFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


internal class EmployeeActivityTest {
    @get: Rule
    val employeeActivityRule: ActivityTestRule<EmployeeActivity> = ActivityTestRule(EmployeeActivity::class.java , false , false)

    @Before
    fun setUp() {
        val intent = Intent()
        employeeActivityRule.launchActivity(intent)
    }

    @Test
    fun onLaunchActionBarTitleIsDisplayed() {
        val actionBar: ActionBar? = employeeActivityRule.activity.supportActionBar
        Assert.assertNotNull(actionBar?.title)
    }


    @Test
    fun appLaunchSuccessfully() {
        ActivityScenario.launch(EmployeeActivity::class.java)
    }


    @Test
    fun testme() {
        onView(withId(R.id.search_emp)).check(matches(isDisplayed()))
    }

  /*  @Test
    fun display(){
        onView(withId(R.id.search_emp)).check(matches((isDisplayed())))
    }*/
}