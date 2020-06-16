package com.company47.ad340weather.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.company47.ad340weather.Location
import com.company47.ad340weather.LocationRepository
import com.company47.ad340weather.R


class LocationEntryFragment : Fragment() {

private lateinit var locationRepository:LocationRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        locationRepository= LocationRepository(requireContext())
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)
        val zipCodeEditText: EditText = view.findViewById(R.id.zipcode_ET)
        val enterButton: Button = view.findViewById(R.id.submitBtn)

        //submitting zipCode and navigation to CurrentForecastFrag
        enterButton.setOnClickListener {
            val zipcode: String = zipCodeEditText.text.toString()

            if (zipcode.length != 5) {
                Toast.makeText(requireContext(), "Enter valid zipcode", Toast.LENGTH_SHORT).show()
            } else {
                locationRepository.saveLocation(Location.Zipcode(zipcode))

                findNavController().navigateUp()
            }
        }

        return view
    }


}