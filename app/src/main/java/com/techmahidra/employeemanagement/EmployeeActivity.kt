package com.techmahidra.employeemanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.techmahidra.employeemanagement.core.EmployeeApplication
import com.techmahidra.employeemanagement.ui.addEmp.AddEmployeeFragment
import com.techmahidra.employeemanagement.ui.employeeList.EmployeeListFragment
import com.techmahidra.employeemanagement.utilities.FragmentTransUtil.addFragment
import com.techmahidra.employeemanagement.utilities.FragmentTransUtil.replaceFragment
import kotlinx.android.synthetic.main.custom_toolbar.*

class EmployeeActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_toolbar)
        tv_emp_title.text =
            EmployeeApplication.applicationContext().resources.getString(R.string.emp_list)
        imgbtn_emp_add.setOnClickListener(this)
        if (savedInstanceState == null)
            addFragment(EmployeeListFragment(), R.id.emplist_activity_container) // add fragment

    }

    override fun onClick(v: View?) {
        replaceFragment(AddEmployeeFragment(), R.id.emplist_activity_container) // add fragment
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 0){
            super.onBackPressed()
        }else{
            supportFragmentManager.popBackStack()
        }
    }

}

