package com.techmahidra.employeemanagement.ui.employeeList

import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.techmahidra.employeemanagement.EmployeeActivity
import com.techmahidra.employeemanagement.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class EmployeeListFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(EmployeeActivity::class.java)

    @Test
    fun recyclerViewTestScrolling() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_emp_info_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun recyclerViewTestScrollingToPositionEndIndex() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_emp_info_list)).perform(ViewActions.swipeUp())
    }

    @Test
    fun recyclerViewTestScrollingToPositionTop() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_emp_info_list)).perform(ViewActions.swipeDown())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }


}