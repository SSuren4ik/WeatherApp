package com.weatherapp.world_weather.presentation

import androidx.recyclerview.widget.DiffUtil
import com.core.data.Hour

class DiffCallback : DiffUtil.Callback() {

    private var oldDataList: MutableList<Hour> = mutableListOf()
    private var newDataList: MutableList<Hour> = mutableListOf()

    fun setData(oldItems: List<Hour>, newItems: List<Hour>) {
        oldDataList.clear()
        oldDataList.addAll(oldItems)

        newDataList.clear()
        newDataList.addAll(newItems)
    }

    override fun getOldListSize(): Int = oldDataList.size

    override fun getNewListSize(): Int = newDataList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDataList[oldItemPosition].temp_c == newDataList[newItemPosition].temp_c
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldDataList[oldItemPosition] == newDataList[newItemPosition]
    }
}