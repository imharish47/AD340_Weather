package com.company47.ad340weather.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.company47.ad340weather.R
import com.company47.ad340weather.utils.TempDisplaySettingManager
import com.company47.ad340weather.utils.formatTempForDisplay


class ForecastDetailsFragment : Fragment() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val args: ForecastDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_forecast_details, container, false)
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val FDA_TempTV: TextView = layout.findViewById(R.id.FDA_tempTV)
        val FDA_DESTV: TextView = layout.findViewById(R.id.FDA_desTV)

        FDA_TempTV.text =
            formatTempForDisplay(args.temp, tempDisplaySettingManager.getTempDisplaySetting())
        FDA_DESTV.text = args.description
        return layout
    }


}