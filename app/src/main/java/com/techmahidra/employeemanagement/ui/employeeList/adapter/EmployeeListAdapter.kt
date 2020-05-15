package com.techmahidra.employeemanagement.ui.employeeList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techmahidra.employeemanagement.R
import com.techmahidra.employeemanagement.core.EmployeeApplication
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse
import com.techmahidra.employeemanagement.ui.employeeList.EmployeeListFragment
import com.techmahidra.employeemanagement.utilities.loadImage
import kotlinx.android.synthetic.main.adapter_employee_list.view.*
import java.util.*


/* *
* EmployeeListAdapter - helps to bind data in feature recyclerview
* highlight the selected list item*/
class EmployeeListAdapter(private val empListFragment : EmployeeListFragment, private val employeeList: List<EmployeeListResponse.Data>) :
    RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {

    var rowIndex = -1 // default selected row index

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_employee_list, parent, false)
        return ViewHolder(v)
    }

    //get list item count
    override fun getItemCount() = employeeList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(employeeList[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val rowEmpLayout = itemView.const_layout_emp_list
        private val rowEmpName = itemView.tv_emp_name
        private val rowEmpAge = itemView.tv_emp_age
        private val rowEmpSalary = itemView.tv_emp_salary
        private val rowEmpPhoto = itemView.iv_emp_photo

        // bind data to view
        fun bind(employeeRow: EmployeeListResponse.Data, position: Int) {
            rowEmpName.text = employeeRow.employeeName
            rowEmpAge.text = employeeRow.employeeAge
            rowEmpSalary.text = employeeRow.employeeSalary
            rowEmpPhoto.loadImage(employeeRow.profileImage)


            if (rowIndex === position) {
                rowEmpLayout.setBackgroundColor(
                    EmployeeApplication.applicationContext().resources.getColor(
                        R.color.colorLightGray
                    )
                )
            } else {
                rowEmpLayout.setBackgroundColor(
                    EmployeeApplication.applicationContext().resources.getColor(
                        R.color.colorWhite
                    )
                )

            }
            itemView.setOnClickListener {
                rowIndex = position
                notifyDataSetChanged() // notify when data change
            }
        }
    }
    fun empSearchFilter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        EmployeeListFragment.modifiedFeatureList.clear()
        if (charText.length == 0) {
            EmployeeListFragment.modifiedFeatureList.addAll(employeeList)
            //empListFragment.showData()
        } else {//getDriverList
            for (item in employeeList) {

                if (item.employeeName.toLowerCase(Locale.getDefault()).contains(charText)) {
                    EmployeeListFragment.modifiedFeatureList.add(item)
                }
            }
            if(EmployeeListFragment.modifiedFeatureList.size==0)
            {
                empListFragment.showNoData()
            }
            else
            {
                empListFragment.updateUI(EmployeeListFragment.modifiedFeatureList)
            }
        }
        notifyDataSetChanged()
    }

}
