package com.tdr.app.kimikoscanvas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.tdr.app.kimikoscanvas.databinding.ActivityMainBinding
import com.tdr.app.kimikoscanvas.utils.FirebaseUtils

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding =
            DataBindingUtil.setContentView(
                this,
                R.layout.activity_main
            )

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.listFragment))

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    // Remove event listener attached to database reference
    override fun onDestroy() {
        super.onDestroy()
        FirebaseUtils().removeListener()
    }
}
