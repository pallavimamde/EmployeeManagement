package com.techmahidra.employeemanagement.utilities

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/* *
* FragmentTransUtil - Add or replace fragment, helps to reduce redundancy
* */
object FragmentTransUtil {
    fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) { // add fragment
        supportFragmentManager.inAddTransaction { add(frameId, fragment) }
    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) { // replace fragment
        supportFragmentManager.inTransaction { replace(frameId, fragment) }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) { // fragment trans
        beginTransaction().func().addToBackStack(null).commit()
    }

    private inline fun FragmentManager.inAddTransaction(func: FragmentTransaction.() -> FragmentTransaction) { // fragment trans
        beginTransaction().func().commit()
    }

}