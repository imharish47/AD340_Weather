package com.company47.ad340weather.utils

import android.content.Context

enum class TempDisplaySetting{
    Fahrenheit,Celsius
}


class TempDisplaySettingManager(content:Context){
    private val preferences=content.getSharedPreferences("settings",Context.MODE_PRIVATE)

    fun updateSetting(setting:TempDisplaySetting){
        preferences.edit().putString("key_temp_display",setting.name).commit()
    }

    fun getTempDisplaySetting():TempDisplaySetting{
val settingValue=preferences.getString("key_temp_display",TempDisplaySetting.Fahrenheit.name)?:TempDisplaySetting.Fahrenheit.name
    return TempDisplaySetting.valueOf(settingValue)
    }
}