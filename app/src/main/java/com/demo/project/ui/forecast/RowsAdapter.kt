package com.demo.project.ui.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.project.R
import com.demo.project.databinding.RowDirectionTitleBinding
import com.demo.project.databinding.RowTramBinding

class RowsAdapter(private var rows: ArrayList<Row>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder( viewGroup: ViewGroup, viewType: Int ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when(viewType){
            Row.TYPE_TRAM_FORECAST_ROW -> TramViewHolder(DataBindingUtil.inflate(inflater, R.layout.row_tram, viewGroup, false))
            else -> TitleViewHolder( DataBindingUtil.inflate(inflater, R.layout.row_direction_title, viewGroup, false))
        }
    }

    private fun inflate(@LayoutRes layoutResId: Int, viewGroup:ViewGroup): View {
        return LayoutInflater.from(viewGroup.context).inflate(layoutResId, viewGroup, false)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int  ) {
        when(getItemViewType(position)){
            Row.TYPE_TRAM_FORECAST_ROW -> (viewHolder as TramViewHolder).setRowData(  rows[position]  )
            else -> (viewHolder as TitleViewHolder).setRowData(rows[position])
        }
    }

    override fun getItemViewType(position: Int) = rows[position].type

    override fun getItemCount() = rows.size

    fun updateData(rows: ArrayList<Row>) {
        this.rows = rows
        notifyDataSetChanged()
    }

    internal inner class TitleViewHolder(private val binding: RowDirectionTitleBinding) :  RecyclerView.ViewHolder(binding.root) {
        fun setRowData(row: Row) {
            binding.row = row
            binding.executePendingBindings()
        }
    }

    internal inner class TramViewHolder(private val binding: RowTramBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setRowData(row: Row) {
            binding.row = row
            binding.executePendingBindings()
        }
    }

}