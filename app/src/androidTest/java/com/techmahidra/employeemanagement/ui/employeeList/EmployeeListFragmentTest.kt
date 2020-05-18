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
    fun test_isButtonTitleDisplayed() {
        val imageBtn = Espresso.onView(
            Matchers.allOf(
                withId(R.id.imgbtn_emp_add),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        imageBtn.check(ViewAssertions.matches(ViewMatchers.withId(R.id.imgbtn_emp_add)))
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

    @Test
    fun isAddButtonClick() {
        val activityScenario = ActivityScenario.launch(EmployeeActivity::class.java)
        val actionMenuItemView = Espresso.onView(
            Matchers.allOf(
                withId(R.id.imgbtn_emp_add),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        actionMenuItemView.perform(ViewActions.click())

    }
}