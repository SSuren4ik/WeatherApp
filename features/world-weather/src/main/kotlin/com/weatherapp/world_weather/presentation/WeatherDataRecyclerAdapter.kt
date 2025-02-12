package com.weatherapp.world_weather.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.core.data.Hour
import com.core.utils.ResourceProvider
import com.weatherapp.world_weather.R
import com.weatherapp.world_weather.databinding.WeatherHourDataItemBinding

class WeatherDataRecyclerAdapter(
    private val provider: ResourceProvider,
) : RecyclerView.Adapter<WeatherDataRecyclerAdapter.DataViewHolder>() {

    private var dataList: List<Hour> = emptyList()

    fun setData(newDataList: List<Hour>, diffCallback: DiffCallback) {
        diffCallback.setData(dataList, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        dataList = newDataList
        diffResult.dispatchUpdatesTo(this)
    }

    class DataViewHolder(val binding: WeatherHourDataItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding =
            WeatherHourDataItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val data = dataList[position]
        holder.binding.timeTextView.text = extractTime(data.time)
        holder.binding.dayStatusImageView.setImageResource(
            if (data.is_day == 0) R.drawable.moon else R.drawable.baseline_sunny_24
        )
        holder.binding.tempFeelsLikeView.text = getTempText(data.feelslike_c)
        holder.binding.tempTextView.text = getTempText(data.temp_c)
    }

    private fun extractTime(fullDateTime: String): String {
        return fullDateTime.substringAfter(' ').take(2)
    }

    private fun getString(id: Int): String {
        return provider.getString(id)
    }

    private fun getTempText(temp: Double): String {
        return temp.toInt().toString() + getString(com.weatherapp.design_system.R.string.degree)
    }
}