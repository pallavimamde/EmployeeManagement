package com.techmahidra.employeemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.techmahidra.employeemanagement.ui.employeeList.EmployeeListFragment
import com.techmahidra.employeemanagement.utilities.FragmentTransUtil.addFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_toolbar)


        if (savedInstanceState == null)
            addFragment(EmployeeListFragment(), R.id.emplist_activity_container) // add fragment
    }
}
