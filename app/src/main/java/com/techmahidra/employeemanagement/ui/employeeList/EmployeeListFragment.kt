package com.techmahidra.employeemanagement.ui.employeeList

import android.annotation.SuppressLint
import androidx.appcompat.app.ActionBar
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

import com.techmahidra.employeemanagement.R
import com.techmahidra.employeemanagement.core.EmployeeApplication
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse
import com.techmahidra.employeemanagement.ui.employeeList.adapter.EmployeeListAdapter
import com.techmahidra.employeemanagement.utilities.NetworkConnectionStatus
import kotlinx.android.synthetic.main.fragment_employee_list.*
import kotlinx.android.synthetic.main.no_data_layout.*

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var employeeListViewModel: EmployeeListViewModel? = null
    private var employeeListAdapter: RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>? = null
    private var actionBar: ActionBar? = null
    private lateinit var loadingDialog: Dialog
    private var isRefreshing = false

    companion object {
        val modifiedFeatureList: ArrayList<EmployeeListResponse.Data> = ArrayList()
    }

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employee_list, container, false)
    }

    // on fragment view create initialize the params
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = ""
        search_emp.setOnQueryTextListener(this)
        loadingDialog = Dialog(activity as AppCompatActivity)
        loadingDialog.setCancelable(false)
        loadingDialog.setCanceledOnTouchOutside(false)
        employeeListViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel::class.java)

        loadData()
        swipe_refresh.setOnRefreshListener {
            isRefreshing = true
            loadData(isRefreshing)
            swipe_refresh.isRefreshing = false
        }

    }


    // get data from server
    @RequiresApi(Build.VERSION_CODES.M)
    private fun loadData(isRefreshing: Boolean = false) {
        // check internet connection
        val hasInternetConnected =
            NetworkConnectionStatus(EmployeeApplication.applicationContext()).isOnline()
        if (hasInternetConnected) {
            if (!isRefreshing) { // if default refreshing is visible don't show loading dialog
                /*showLoading(
                    EmployeeApplication.applicationContext().resources.getString(
                        R.string.please_wait
                    )
                )*/
            }
            // check the observer when api response is success and update list
            employeeListViewModel?.employeeListVM?.observe(
                this, Observer { employeeListResponse ->
                    updateUI(employeeListResponse)
                    //hideLoading()
                })


            /*  //check the observer when api response is failed and show the error
              employeeListViewModel.apiResponseFail.observe(
                  this,
                  Observer(function = fun(apiResponseFail: ApiResponseFail) {
                      showError(apiResponseFail.error)
                      hideLoading()
                  })
              )*/
        } else {
            showError(EmployeeApplication.applicationContext().resources.getString(R.string.network_error))
        }
    }

    // update UI
    @SuppressLint("WrongConstant")
    fun updateUI(response: List<EmployeeListResponse.Data>) {
        // set actionbar title
        if (actionBar != null) {
            actionBar?.title = "DEMO"
        }

        if (response.isNotEmpty()) {
            for (item in response) {

                var isAllNull = false

                //check the empty or null string in response object
                if (item.employeeName.isNullOrBlank() && item.employeeAge.isNullOrBlank() && item.employeeSalary.isNullOrBlank()) {
                    isAllNull = true
                } else {
                    if (item.employeeName.isNullOrBlank()) {
                        item.employeeName = "Empty String"
                    }
                    if (item.employeeAge.isNullOrBlank()) {
                        item.employeeAge = "Empty Value"
                    }
                    if (item.employeeSalary.isNullOrBlank()) {
                        item.employeeSalary = "Empty Value"
                    }

                }
                if (!isAllNull) {
                    modifiedFeatureList.add(item)
                }
            }
            // initialize the @EmployeeListAdapter and set list
            employeeListAdapter = EmployeeListAdapter(this, modifiedFeatureList)
            rv_emp_info_list.adapter = employeeListAdapter
        } else {
            showNoData()
        }
    }

    // if there will be any error occurred while server call
     fun showError(errorMsg: String) {
        Toast.makeText(EmployeeApplication.applicationContext(), errorMsg, Toast.LENGTH_SHORT)
            .show()
    }

    // show dialog while loading data from server
     fun showLoading(loadingMessage: String) {
        loadingDialog.setContentView(R.layout.progress_bar)
//        loadingDialog.show()

    }

    // hide loading
     fun hideLoading() {
        loadingDialog.dismiss()
    }

    // if there is empty list then so no data layout
     fun showNoData() {
        rv_emp_info_list.visibility = View.GONE
        tv_no_data.visibility = View.VISIBLE
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            try {
                (employeeListAdapter as EmployeeListAdapter).empSearchFilter(newText)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return false
    }
}

/*
* UIHandler - helps to display user needed information while handling the server call
* */
interface UIHandler {
    fun showLoading(loadingMessage: String)
    fun hideLoading()
    fun showNoData()
    fun showError(errorMsg: String)
}