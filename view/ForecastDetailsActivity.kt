package com.company47.ad340weather.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.company47.ad340weather.R
import com.company47.ad340weather.utils.TempDisplaySettingManager
import com.company47.ad340weather.utils.disAlertDialog
import com.company47.ad340weather.utils.formatTempForDisplay

class ForecastDetailsActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)
        setTitle(R.string.forecastDetailsTitle)
        tempDisplaySettingManager = TempDisplaySettingManager(this)
        val FDA_TempTV: TextView = findViewById(R.id.FDA_tempTV)
        val FDA_DESTV: TextView = findViewById(R.id.FDA_desTV)

       //region GET-Intent from MainActivty
        val temp = intent.getFloatExtra("key_temp", 0f)
        val desc = intent.getStringExtra("key_desc")
        FDA_TempTV.text = formatTempForDisplay(temp, tempDisplaySettingManager.getTempDisplaySetting())
        FDA_DESTV.text = desc
        //endregion

    }


    //region MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.tempDisplaySettings -> {
                disAlertDialog(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }
    //endregion



}