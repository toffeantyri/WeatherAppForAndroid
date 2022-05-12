package ru.weather.weathertoday.view.mainAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textview.MaterialTextView
import ru.weather.weathertoday.R
import ru.weather.weathertoday.busines.model.HourlyWeatherModel
import ru.weather.weathertoday.view.DAY_WEEK_NAME_LONG
import ru.weather.weathertoday.view.provideIcon
import ru.weather.weathertoday.view.toDateFormatOf
import ru.weather.weathertoday.view.toDegree
import java.lang.StringBuilder

class AdapterHourlyWeather() : BaseAdapters<HourlyWeatherModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HourlyViewHolder(inflater.inflate(R.layout.item_rc_hourly_list, parent, false))
    }

    inner class HourlyViewHolder(itemView: View) : BaseViewHolder(itemView) {
        //findViewById by ButterKnife
        @BindView(R.id.tv_hourly_time)
        lateinit var time: MaterialTextView

        @BindView(R.id.tv_hourly_temp)
        lateinit var temperature: MaterialTextView

        @BindView(R.id.tv_hourly_vlazhnost)
        lateinit var poprate: MaterialTextView

        @BindView(R.id.iv_hourly_weather_stat)
        lateinit var image: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }

        override fun bindView(position: Int) {
            mData[position].apply {
                time.text = dt.toDateFormatOf(DAY_WEEK_NAME_LONG)
                temperature.text =
                    StringBuilder().append(temp.toDegree()).append("\u00b0").toString()
                poprate.text = pop.toString()
                image.setImageResource(weather[0].icon.provideIcon())

            }

        }
    }
}