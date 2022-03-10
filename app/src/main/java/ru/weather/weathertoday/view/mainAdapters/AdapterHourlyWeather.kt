package ru.weather.weathertoday.view.mainAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import ru.weather.weathertoday.view.HourlyItem
import ru.weather.weathertoday.R

class AdapterHourlyWeather(list: ArrayList<HourlyItem>) : RecyclerView.Adapter<AdapterHourlyWeather.HourlyViewHolder>() {
    val adapterList = list


    class HourlyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val time : MaterialTextView = itemView.findViewById(R.id.tv_hourly_time)
        val temp : MaterialTextView = itemView.findViewById(R.id.tv_hourly_temp)
        val image: ImageView = itemView.findViewById(R.id.iv_hourly_weather_stat)
        val vlazhnost: MaterialTextView = itemView.findViewById(R.id.tv_hourly_vlazhnost)

        fun bind(hourlyItem: HourlyItem){
            time.text = hourlyItem.time
            temp.text = hourlyItem.temp
            image.setImageResource(hourlyItem.image)
            vlazhnost.text = hourlyItem.vlazhnost
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HourlyViewHolder(inflater.inflate(R.layout.item_rc_hourly_list,parent,false))
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(adapterList[position])
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }
}