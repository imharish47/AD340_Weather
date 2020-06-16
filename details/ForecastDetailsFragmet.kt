package com.company47.ad340weather.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.company47.ad340weather.databinding.FragmentForecastDetailsBinding
import com.company47.ad340weather.utils.TempDisplaySettingManager
import com.company47.ad340weather.utils.formatTempForDisplay


class ForecastDetailsFragment : Fragment() {

    private var _binding: FragmentForecastDetailsBinding? = null

    //this property only valid in onCreate and onDestroy
    private val binding: FragmentForecastDetailsBinding get() = _binding!!
//
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val args: ForecastDetailsFragmentArgs by navArgs()

    private lateinit var viewModelFactory: ForecastDetailsViewModelFactory
    private val viewModel: ForecastDetailsViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        viewModelFactory = ForecastDetailsViewModelFactory(args)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<ForecastDetailsViewState> { viewState ->
            //update UI
            binding.tempText.text = formatTempForDisplay(
                viewState.temp,
                tempDisplaySettingManager.getTempDisplaySetting()
            )
            binding.descText.text = viewState.description
            binding.dateText.text = viewState.date
            binding.forecastIcon.load(viewState.iconUrl)
        }
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}