package com.techmahidra.employeemanagement.ui.employeelist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techmahidra.employeemanagement.R
import com.techmahidra.employeemanagement.core.EmployeeApplication
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse
import com.techmahidra.employeemanagement.utilities.loadImage
import kotlinx.android.synthetic.main.adapter_employee_list.view.*


/* *
* EmployeeListAdapter - helps to bind data in feature recyclerview
* highlight the selected list item*/
class EmployeeListAdapter(private val employeeRow: List<EmployeeListResponse.Data>) :
    RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {

    var rowIndex = -1 // default selected row index

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_employee_list, parent, false)
        return ViewHolder(v)
    }

    //get list item count
    override fun getItemCount() = employeeRow.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(employeeRow[position], position)
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

}
