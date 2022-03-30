package ru.weather.weathertoday.view.mainAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textview.MaterialTextView
import ru.weather.weathertoday.view.HourlyItem
import ru.weather.weathertoday.R
import ru.weather.weathertoday.busined.model.HourlyResultModel

class AdapterHourlyWeather() : BaseAdapters<HourlyResultModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HourlyViewHolder(inflater.inflate(R.layout.item_rc_hourly_list, parent, false))
    }

    inner class HourlyViewHolder(itemView: View) : BaseViewHolder(itemView) {
        //findViewById by ButterKnife
        @BindView(R.id.tv_hourly_time)
        lateinit var time : MaterialTextView

        @BindView(R.id.tv_hourly_temp)
        lateinit var temperature : MaterialTextView

        @BindView(R.id.tv_hourly_vlazhnost)
        lateinit var poprate : MaterialTextView

        @BindView(R.id.iv_hourly_weather_stat)
        lateinit var image : ImageView

        init {
            ButterKnife.bind(this, itemView)
        }

        override fun bindView(position: Int) {
            time.text = "14:00"
            temperature.text = "15"+"\u00b0"
            poprate.text = "25" + "%"
            image.setImageResource(R.drawable.ic_sun_24dp)
        }
    }
}