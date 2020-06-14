package com.company47.ad340weather.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.company47.ad340weather.R
import com.company47.ad340weather.databinding.ActivityMainBinding
import com.company47.ad340weather.utils.TempDisplaySettingManager
import com.company47.ad340weather.utils.disAlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager;
    private lateinit var binding: ActivityMainBinding
    //  private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        val view: View = binding.root
        setContentView(view)
        tempDisplaySettingManager = TempDisplaySettingManager(this)

        val navController = findNavController(R.id.nav_host_frag)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.toolbar).setupWithNavController(
            navController,
            appBarConfiguration
        )
        findViewById<BottomNavigationView>(R.id.bottomNavView).setupWithNavController(navController)
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
    // endregion

}