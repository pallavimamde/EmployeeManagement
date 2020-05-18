package com.techmahidra.employeemanagement.ui.addEmp

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.techmahidra.employeemanagement.R
import com.techmahidra.employeemanagement.core.EmployeeApplication
import com.techmahidra.employeemanagement.data.AddEmployeeRequest
import com.techmahidra.employeemanagement.data.response.AddEmployeeResponse
import com.techmahidra.employeemanagement.ui.employeeList.EmployeeListViewModel
import com.techmahidra.employeemanagement.utilities.NetworkConnectionStatus
import kotlinx.android.synthetic.main.fragment_add_employee.*


/**
 * A simple [Fragment] subclass.
 * Use the [AddEmployeeFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * collect employee information and add employee to database
 */
class AddEmployeeFragment : Fragment(), View.OnClickListener {

    private var employeeListViewModel: EmployeeListViewModel? = null
    private lateinit var loadingDialog: Dialog

    companion object {
        var empInfo: AddEmployeeRequest? = null
    }

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_employee, container, false)
    }

    // on fragment view create initialize the params
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.getWindow()
            ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        (activity as AppCompatActivity).supportActionBar?.hide()
        btn_add_emp_submit.setOnClickListener(this)
        loadingDialog = Dialog(activity as AppCompatActivity)
        loadingDialog.setCancelable(false)
        loadingDialog.setCanceledOnTouchOutside(false)
        employeeListViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel::class.java)

    }


    // get data from server
    @RequiresApi(Build.VERSION_CODES.M)
    private fun collectEmpInfo() {
        val userName = etv_add_emp_name.text.toString()
        val userAge = etv_add_emp_age.text.toString()
        val userSalary = etv_add_emp_salary.text.toString()
        empInfo = AddEmployeeRequest(userAge, userName, userSalary)
        // check internet connection
        val hasInternetConnected =
            NetworkConnectionStatus(EmployeeApplication.applicationContext()).isOnline()
        if (hasInternetConnected) {
            showLoading(
                EmployeeApplication.applicationContext().resources.getString(
                    R.string.please_wait
                )
            )

            // check the observer when api response is success and update list
            employeeListViewModel?.addEmployeeVM?.observe(
                this, Observer { addEmployeeResponse ->
                    updateUI(addEmployeeResponse)
                    hideLoading()
                })

        } else {
            showError(EmployeeApplication.applicationContext().resources.getString(R.string.network_error))
        }
    }

    // update UI
    @SuppressLint("WrongConstant")
    fun updateUI(addEmployeeResponse: AddEmployeeResponse) {

        etv_add_emp_name.text.clear()
        etv_add_emp_age.text.clear()
        etv_add_emp_salary.text.clear()

    }

    // if there will be any error occurred while server call
    fun showError(errorMsg: String) {
        Toast.makeText(EmployeeApplication.applicationContext(), errorMsg, Toast.LENGTH_SHORT)
            .show()
    }

    // show dialog while loading data from server
    fun showLoading(loadingMessage: String) {
        loadingDialog.setContentView(R.layout.progress_bar)
        loadingDialog.show()

    }

    // hide loading
    fun hideLoading() {
        loadingDialog.dismiss()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onClick(v: View?) {
        collectEmpInfo()
    }

}
