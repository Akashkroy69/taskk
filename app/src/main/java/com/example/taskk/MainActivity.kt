package com.example.taskk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.taskk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)
        setSupportActionBar(activityMainBinding.toolbarID)

        //for toolbar configuration
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.IdNavHostFragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfigurationBuilder = AppBarConfiguration.Builder(navController.graph)
        appBarConfigurationBuilder.setOpenableLayout(activityMainBinding.drawerLayoutId)
        val appBarConfigurator = appBarConfigurationBuilder.build()
        activityMainBinding.toolbarID.setupWithNavController(navController,appBarConfigurator)

        activityMainBinding.drawerNavigationViewId.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.IdNavHostFragmentContainer)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}