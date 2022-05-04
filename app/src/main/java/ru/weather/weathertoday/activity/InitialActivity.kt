package ru.weather.weathertoday.activity

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.weather.weathertoday.*


const val GEO_LOCATION_REQUEST_CODE_SUCCESS = 1

class InitialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        checkPermission()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG, "Request code : $requestCode")
        if (requestCode == GEO_LOCATION_REQUEST_CODE_SUCCESS && permissions.isNotEmpty()) {

            val preferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)
            preferences.edit().apply {
                putBoolean(IS_STARTED_UP, true)
                apply()
            }

            Log.d(TAG, " start MainActivity")

            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
        //todo надо или нет вызов super ?
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Требуются данные о местоположении")
                .setMessage("Разрешить приложению доступ GPS для определения вашего местоположения?")
                .setPositiveButton("Ok") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        GEO_LOCATION_REQUEST_CODE_SUCCESS
                    )
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                        GEO_LOCATION_REQUEST_CODE_SUCCESS
                    )
                }
                .setNegativeButton("cancel") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }.create().show()
        }
    }

}
