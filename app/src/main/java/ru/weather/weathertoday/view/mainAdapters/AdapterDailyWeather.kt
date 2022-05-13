package ru.weather.weathertoday.view.mainAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textview.MaterialTextView
import ru.weather.weathertoday.R
import ru.weather.weathertoday.busines.model.DailyWeatherModel
import ru.weather.weathertoday.view.*
import java.lang.StringBuilder

class AdapterDailyWeather() : BaseAdapters<DailyWeatherModel>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DailyViewHolder(inflater.inflate(R.layout.item_rc_daily_list, parent, false))
    }


   inner class DailyViewHolder(itemView: View) : BaseViewHolder(itemView) {
        @BindView(R.id.tv_daily_date)
        lateinit var date: MaterialTextView

        @BindView(R.id.tv_vlazhnost_daily)
        lateinit var poprate: MaterialTextView

        @BindView(R.id.tv_mintemp_daily)
        lateinit var tempMin: MaterialTextView

        @BindView(R.id.tv_maxtemp_daily)
        lateinit var tempMax: MaterialTextView

        @BindView(R.id.iv_daily_weath_stat)
        lateinit var image: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }


        override fun bindView(position: Int) {
            mData[position].apply {
                date.text = dt.toDateFormatOf(DAY_WEEK_NAME_LONG)
                poprate.text = pop.toPercentString("%")
                tempMin.text = StringBuilder().append(temp.min.toDegree()).append("\u00b0").toString()
                tempMax.text = StringBuilder().append(temp.max.toDegree()).append("\u00b0").toString()
                image.setImageResource(weather[0].icon.provideIcon())

            }




        }

    }


}