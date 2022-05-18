package ru.weather.weathertoday.view

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import ru.weather.weathertoday.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

const val DAY_FULL_MONTH_NAME = "dd MMMM"
const val DAY_WEEK_NAME_LONG = "dd EEEE"
const val HOUR_DOUBLE_DOT_MINUTE = "HH:mm"

fun Long.toDateFormatOf(format: String): String {
    val calendar = Calendar.getInstance()
    val timeZone = calendar.timeZone
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    return sdf.format(Date(this * 1000))
}

fun Double.toDegree() = (this - 273.15).roundToInt().toString()

fun Double.toPercentString(extraPart: String) = (this * 100).roundToInt().toString() + extraPart

fun String.provideIcon() = when (this) {
    "01n", "01d" -> R.drawable.w01d
    "02n", "02d" -> R.drawable.w02d
    "03n", "03d" -> R.drawable.w03d
    "04n", "04d" -> R.drawable.w04d
    "09n", "09d" -> R.drawable.w09d
    "10n", "10d" -> R.drawable.w10d
    "11n", "11d" -> R.drawable.w11d
    "13n", "13d" -> R.drawable.w13d
    "50n", "50d" -> R.drawable.w50d
    else -> R.drawable.ic_baseline_error
}


//функция расширение - возвращает поток стрингов, textChangeListener наследуется от TextWatcher
//методы там же, а тут переопределяется нужный нам метод после ввода Emitter (Flowable)
// получет в метод onNext Строку(дальше смотри в место использования)
fun TextInputEditText.createObservable(): Flowable<String> {
    return Flowable.create({
        addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                it.onNext(s.toString())
            }
        })
    }, BackpressureStrategy.BUFFER) //если слишком много печатать и запросы не успервают обрабатываться,
    // а количество запросоав в вочереди растет будет укладывать в буфер
}

private abstract class SimpleTextWatcher : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {
    }
}