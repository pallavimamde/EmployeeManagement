package com.techmahidra.employeemanagement

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class EmployeeActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(EmployeeActivity::class.java)

    @Test
    fun EmployeeActivityTestTest2() {
        val appCompatImageView = onView(
            allOf(
                withId(R.id.search_button), withContentDescription("Search"),
                childAtPosition(
                    allOf(
                        withId(R.id.search_bar),
                        childAtPosition(
                            withId(R.id.search_emp),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val searchAutoComplete = onView(
            allOf(
                withId(R.id.search_src_text),
                childAtPosition(
                    allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        searchAutoComplete.perform(replaceText("a"), closeSoftKeyboard())

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.search_close_btn), withContentDescription("Clear query"),
                childAtPosition(
                    allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        val appCompatImageView3 = onView(
            allOf(
                withId(R.id.search_close_btn), withContentDescription("Clear query"),
                childAtPosition(
                    allOf(
                        withId(R.id.search_plate),
                        childAtPosition(
                            withId(R.id.search_edit_frame),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView3.perform(click())

        val textView = onView(
            allOf(
                withText("Employee Information"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Employee Information")))

        val linearLayout = onView(
            allOf(
                withId(R.id.search_bar),
                childAtPosition(
                    allOf(
                        withId(R.id.search_emp),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val imageView = onView(
            allOf(
                withId(R.id.search_button), withContentDescription("Search"),
                childAtPosition(
                    allOf(
                        withId(R.id.search_bar),
                        childAtPosition(
                            withId(R.id.search_emp),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val linearLayout2 = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.rv_emp_info_list),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.view.ViewGroup::class.java),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout2.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.imgbtn_emp_add),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withId(R.id.imgbtn_emp_add)))


        val editText = onView(
            allOf(
                withText("Name"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        editText.check(matches(isDisplayed()))

        val editText2 = onView(
            allOf(
                withText("Name"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        editText2.check(matches(withText("Name")))

        val editText3 = onView(
            allOf(
                withText("Salary"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_salary),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        editText3.check(matches(withText("Salary")))

        val editText4 = onView(
            allOf(
                withText("Salary"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_salary),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        editText4.check(matches(isDisplayed()))

        val editText5 = onView(
            allOf(
                withText("Age"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_age),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        editText5.check(matches(withText("Age")))

        val editText6 = onView(
            allOf(
                withText("Age"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_age),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        editText6.check(matches(isDisplayed()))

        val button = onView(
            allOf(
                withId(R.id.btn_add_emp_submit),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emplist_activity_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val button2 = onView(
            allOf(
                withId(R.id.btn_add_emp_submit),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emplist_activity_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        button2.check(matches(isDisplayed()))

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(click())

        val textInputEditText2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_name),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(click())

        val textInputEditText3 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_salary),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(click())

        val textInputEditText4 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_salary),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("4"), closeSoftKeyboard())

        val textInputEditText5 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tv_emp_age),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("6"), closeSoftKeyboard())

        val appCompatButton = onView(
            allOf(
                withId(R.id.btn_add_emp_submit), withText("Submit"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.emplist_activity_container),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())
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