package com.company47.ad340weather.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

    //region String formatter for Displaying
fun formatTempForDisplay(temp: Float, tempDisplaySetting: TempDisplaySetting): String {
    return when (tempDisplaySetting) {
        TempDisplaySetting.Fahrenheit -> String.format("%.2f  F°", temp)
        TempDisplaySetting.Celsius -> {
            val nTemp = (temp - 32f) * (5f / 9f)
            String.format("%.2f C°", nTemp)
        }
    }
}
//endregion


    //region Celsius/Fahrenheit chooser AlertDialog
fun disAlertDialog(context: Context, tempDisplaySettingManager: TempDisplaySettingManager) {
    val dailog: AlertDialog = AlertDialog.Builder(context)
        .setTitle("Choose Display Units")
        .setMessage("Choose Temperature unit")
        .setCancelable(true)
        .setPositiveButton("°F") { _, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        .setNeutralButton("°C") { _, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
        }
        .setOnDismissListener {
            Toast.makeText(context, "Settings will take effect after restart", Toast.LENGTH_SHORT)
                .show()
        }
        .show()
}
//endregion