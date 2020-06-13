package com.company47.ad340weather.location

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.company47.ad340weather.R
import com.company47.ad340weather.`interface`.AppNavigator


class LocationEntryFragment : Fragment() {

    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)
        val zipCodeEditText: EditText = view.findViewById(R.id.zipcode_ET)
        val enterButton: Button = view.findViewById(R.id.submitBtn)

        enterButton.setOnClickListener {
            val zipcode: String = zipCodeEditText.text.toString()

            if (zipcode.length != 5) {
                Toast.makeText(requireContext(), "Enter valid zipcode", Toast.LENGTH_SHORT).show()
            } else {
                appNavigator.navigateToCurrentForecast(zipcode)
            }
        }

        return view
    }


}