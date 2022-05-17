package ru.weather.weathertoday.view.mainAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import ru.weather.weathertoday.R
import ru.weather.weathertoday.busines.model.GeoCodingDataModel

const val COORDINATES = "coordinates"

class CityListAdapter : BaseAdapters<GeoCodingDataModel>() {

    lateinit var clickListener: SearchItemClickListener

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BaseViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_city_list, p0, false)
        return CitySearchViewHolder(view)
    }

    interface SearchItemClickListener {
        fun addToFavorite(item: GeoCodingDataModel)

        fun removeFromFavorite(item: GeoCodingDataModel)

        fun showWeatherIn(item: GeoCodingDataModel)

    }

    inner class CitySearchViewHolder(view: View) : BaseViewHolder(view) {

        @BindView(R.id.item_search_city)
        lateinit var mCity: MaterialTextView

        @BindView(R.id.item_search_country)
        lateinit var mCountry: MaterialTextView

        @BindView(R.id.item_iv_favorite)
        lateinit var mFavorite: MaterialButton

        @BindView(R.id.item_location)
        lateinit var mLocation: MaterialCardView

        @BindView(R.id.item_state)
        lateinit var mState: MaterialTextView

        init {
            ButterKnife.bind(this, view)
        }


        override fun bindView(position: Int) {
            mLocation.setOnClickListener {
                val item = mData[position]
                when ((it as MaterialButton).isChecked) {
                    true -> {
                        item.isFavorite = true
                        clickListener.addToFavorite(item)
                    }
                    false -> {
                        item.isFavorite = false
                        clickListener.removeFromFavorite(item)
                    }
                }
            }
            mData[position].apply {
                mState.text = if (!state.isNullOrEmpty()) itemView.context.getString(R.string.comma, state) else ""
                mCity.text = local_names.ru // todo default ru
                mCountry.text = country
                mFavorite.isChecked = isFavorite
            }
        }


    }

}