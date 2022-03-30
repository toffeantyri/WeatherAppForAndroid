package ru.weather.weathertoday.view.mainAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textview.MaterialTextView
import ru.weather.weathertoday.view.DailyItem
import ru.weather.weathertoday.R
import ru.weather.weathertoday.busined.model.DaylyResultModel

class AdapterDailyWeather() : BaseAdapters<DaylyResultModel>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DailyViewHolder(inflater.inflate(R.layout.item_rc_daily_list,parent,false))
    }


    class DailyViewHolder(itemView: View) : BaseAdapters.BaseViewHolder(itemView) {
        @BindView(R.id.tv_daily_date)
        lateinit var date : MaterialTextView

        @BindView(R.id.tv_vlazhnost_daily)
        lateinit var poprate : MaterialTextView

        @BindView(R.id.tv_mintemp_daily)
        lateinit var tempMin : MaterialTextView

        @BindView(R.id.tv_maxtemp_daily)
        lateinit var tempMax : MaterialTextView

        @BindView(R.id.iv_daily_weath_stat)
        lateinit var image: ImageView

        init {
            ButterKnife.bind(this,itemView)
        }


        override fun bindView(position: Int) {
            date.text = "9 May"
            poprate.text = "50" + "%"
            tempMin.text = "10\u00b0"
            tempMax.text = "20\u00b0"
            image.setImageResource(R.drawable.ic_water_drop)
        }

    }




}