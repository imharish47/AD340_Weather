package com.company47.ad340weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company47.ad340weather.R
import com.company47.ad340weather.model_data.DailyForecast
import com.company47.ad340weather.utils.TempDisplaySettingManager
import com.company47.ad340weather.utils.formatTempForDisplay

class DailyForecastAdapter(
    private val tempDisplaySettingManager: TempDisplaySettingManager,
    private val clickHandler: (DailyForecast) -> Unit
) :
    ListAdapter<DailyForecast, DailyForecastAdapter.DailyForecastViewHolder>(
        DIFF_CONFIG
    ) {

    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyForecast>() {
            override fun areItemsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_forecast, parent, false)

        return DailyForecastViewHolder(itemView, tempDisplaySettingManager)

    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickHandler(getItem(position))
        }

    }


    //region ViewHolder for Adapter
    inner class DailyForecastViewHolder(
        view: View,
        tempDisplaySettingManager: TempDisplaySettingManager
    ) : RecyclerView.ViewHolder(view) {
        private val tempText: TextView = view.findViewById(R.id.tempText)
        private val descriptionText: TextView = view.findViewById(R.id.descText)

        fun bind(dailyForecast: DailyForecast) {
            tempText.text = formatTempForDisplay(
                dailyForecast.temp,
                tempDisplaySettingManager.getTempDisplaySetting()
            )
            descriptionText.text = dailyForecast.description
        }

    }
    //endregion

}