package com.techmahidra.employeemanagement.ui.employeeList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.techmahidra.employeemanagement.R
import com.techmahidra.employeemanagement.core.EmployeeApplication
import com.techmahidra.employeemanagement.data.response.EmployeeListResponse
import com.techmahidra.employeemanagement.ui.employeeList.EmployeeListFragment
import com.techmahidra.employeemanagement.ui.employeeList.EmployeeListFragment.Companion.deleteEmpId
import com.techmahidra.employeemanagement.ui.employeeList.EmployeeListFragment.Companion.modifiedFeatureList
import com.techmahidra.employeemanagement.utilities.loadImage
import kotlinx.android.synthetic.main.adapter_employee_list.view.*
import java.util.*
import kotlin.collections.ArrayList


/* *
* EmployeeListAdapter - helps to bind data in feature recyclerview
* highlight the selected list item*/
class EmployeeListAdapter(private val employeeList: ArrayList<EmployeeListResponse.Data>) :
    RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>(),Filterable {
    var rowIndex = -1 // default selected row index
    var employeeListFragment: EmployeeListFragment = EmployeeListFragment()
    var empFilterList = ArrayList<EmployeeListResponse.Data>()
    init {
        empFilterList = employeeList
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_employee_list, parent, false)
        return ViewHolder(v)
    }

    //get list item count
    override fun getItemCount() = empFilterList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(empFilterList[position], position)
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
    /*fun empSearchFilter(charText: String) {
            var charText = charText
            charText = charText.toLowerCase(Locale.getDefault())
            EmployeeListFragment.modifiedFeatureList.clear()
            if (charText.length == 0) {
                EmployeeListFragment.modifiedFeatureList.addAll(employeeList)
              //\  EmployeeListFragment.()
            } else {
                for (wp in employeeList) {
                    *//* for(obj  in wp.tripDriverList)
                     {
                         if (obj.driverDetail != null)
                         {
                             if()
                         }

                     }*//*
                    //if (wp.tripId.toLowerCase(Locale.getDefault()).contains(charText)) {
                    if (wp.employeeName.toLowerCase(Locale.getDefault()).contains(charText)) {
                        EmployeeListFragment.modifiedFeatureList.add(wp)
                    }
                }
                if( EmployeeListFragment.modifiedFeatureList.size==0)
                {
                   // homeFragment.showNoData(true, "No such trip available")
                }
                else
                {
                    employeeListFragment.updateUI(modifiedFeatureList)
                }
            }
            notifyDataSetChanged()
        }*/
    fun removeAt(position: Int) {
        deleteEmpId = employeeList[position].id.toInt()
        employeeList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    empFilterList = employeeList
                } else {
                    val resultList = ArrayList<EmployeeListResponse.Data>()
                    for (row in employeeList) {
                        if (row.employeeName.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    empFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = empFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                empFilterList = results?.values as ArrayList<EmployeeListResponse.Data>
                notifyDataSetChanged()
            }

        }
    }

}
