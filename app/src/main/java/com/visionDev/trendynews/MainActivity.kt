package com.visionDev.trendynews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.visionDev.trendynews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vb = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        setSupportActionBar(vb.mainToolbar)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            vb.mainDrawerLayout,
            R.string.drawer_open,
            R.string.drawer_close
        )
        vb.mainDrawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        actionBarDrawerToggle?.onOptionsItemSelected(item) ?: false || super.onOptionsItemSelected(
            item
        )

    override fun onBackPressed() {
        val navController = supportFragmentManager.findFragmentById(R.id.main_host)
            ?.let {
                (it as NavHostFragment)
                    .navController
            }
        Log.i(TAG, "onSupportNavigateUp: $navController")
        if (navController?.navigateUp() != true) super.onBackPressed()
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}