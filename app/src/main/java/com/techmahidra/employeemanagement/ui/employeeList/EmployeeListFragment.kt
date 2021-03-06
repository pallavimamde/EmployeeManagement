package com.techmahidra.employeemanagement.ui.employeeList

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techmahidra.employeemanagement.R
import com.techmahidra.employeemanagement.core.EmployeeApplication
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse
import com.techmahidra.employeemanagement.ui.employeeList.adapter.EmployeeListAdapter
import com.techmahidra.employeemanagement.utilities.NetworkConnectionStatus
import com.techmahidra.employeemanagement.utilities.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_employee_list.*
import kotlinx.android.synthetic.main.no_data_layout.*


/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * Helps to show the list of employees
 */
class EmployeeListFragment : Fragment() {

    private var employeeListViewModel: EmployeeListViewModel? = null
    private var employeeListAdapter: RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>? = null
    private lateinit var loadingDialog: Dialog
    private var isRefreshing = false

    companion object {
        val modifiedEmployeeList: ArrayList<EmployeeListResponse.Data> = ArrayList()
        var deleteEmpId: Int = 0
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
        showData()
        activity?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        loadingDialog = Dialog(activity as AppCompatActivity)
        loadingDialog.setCancelable(false)
        loadingDialog.setCanceledOnTouchOutside(false)
        employeeListViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel::class.java)
        swipeDelete()
        searchFilter()
        loadData()
        swipe_refresh.setOnRefreshListener {
            isRefreshing = true
            loadData(isRefreshing)
            swipe_refresh.isRefreshing = false
        }

    }

    // Search employee by employee name. Filter checks list as we type each and every char
    private fun searchFilter() {
        search_emp.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (employeeListAdapter as EmployeeListAdapter).filter.filter(newText)
                return false
            }

        })

    }

    // Delete recyclerview item on swipe LEFT or RIGHT
    @RequiresApi(Build.VERSION_CODES.M)
    private fun swipeDelete() {
        val swipeHandler = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (employeeListAdapter as EmployeeListAdapter).removeAt(viewHolder.adapterPosition)
                deleteEmployee()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rv_emp_info_list)

    }

    // Delete api will call when nwe delete item by swipe
    @RequiresApi(Build.VERSION_CODES.M)
    private fun deleteEmployee() {
        val hasInternetConnected =
            NetworkConnectionStatus(EmployeeApplication.applicationContext()).isOnline()
        if (hasInternetConnected) {

            showLoading(
                EmployeeApplication.applicationContext().resources.getString(
                    R.string.please_wait
                )
            )

            // check the observer when api response is success and update list
            employeeListViewModel?.deleteEmployeeVm?.observe(
                this, Observer { deleteEmployeeResponse ->
                    Toast.makeText(
                        EmployeeApplication.applicationContext(),
                        deleteEmployeeResponse.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    hideLoading()
                })

        } else {
            showError(EmployeeApplication.applicationContext().resources.getString(R.string.network_error))
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
                showLoading(
                    EmployeeApplication.applicationContext().resources.getString(
                        R.string.please_wait
                    )
                )
            }
            // check the observer when api response is success and update list
            employeeListViewModel?.employeeListVm?.observe(
                this, Observer { employeeListResponse ->
                    updateUI(employeeListResponse)
                    hideLoading()
                })

        } else {
            showError(EmployeeApplication.applicationContext().resources.getString(R.string.network_error))
        }
    }

    // update UI
    @SuppressLint("WrongConstant")
    fun updateUI(response: List<EmployeeListResponse.Data>) {

        if (response.isNotEmpty()) {
            modifiedEmployeeList.clear()
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
                    modifiedEmployeeList.add(item)
                }
            }
            // initialize the @EmployeeListAdapter and set list
            rv_emp_info_list.layoutManager =
                LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            employeeListAdapter = EmployeeListAdapter(modifiedEmployeeList)
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

    // show employee list if list available else show no data view
    fun showData() {
        rv_emp_info_list.visibility = View.VISIBLE
        tv_no_data.visibility = View.GONE
    }

}
