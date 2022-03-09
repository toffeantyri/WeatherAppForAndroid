package ru.weather.weathertoday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

class AdapterDailyWeather(list: ArrayList<DailyItem>) : RecyclerView.Adapter<AdapterDailyWeather.DailyViewHolder>() {
    val adapterListDaily = list

    class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayData : MaterialTextView = itemView.findViewById(R.id.tv_daily_date)
        val dayImage : ImageView = itemView.findViewById(R.id.iv_daily_weath_stat)
        val dayVlazhn : MaterialTextView = itemView.findViewById(R.id.tv_vlazhnost_daily)
        val dayMinTemp : MaterialTextView = itemView.findViewById(R.id.tv_mintemp_daily)
        val dayMaxTemp : MaterialTextView = itemView.findViewById(R.id.tv_maxtemp_daily)

        fun bind(itemDaily: DailyItem){
            dayData.text = itemDaily.date
            dayImage.setImageResource(itemDaily.image)
            dayVlazhn.text = itemDaily.vlazhnost
            dayMinTemp.text = itemDaily.minTemp
            dayMaxTemp.text = itemDaily.maxTemp
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DailyViewHolder(inflater.inflate(R.layout.item_rc_daily_list,parent,false))
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(adapterListDaily[position])
    }

    override fun getItemCount(): Int {
        return adapterListDaily.size
    }

}