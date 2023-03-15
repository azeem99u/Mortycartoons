package com.azeem.morty.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.azeem.morty.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics

class NavGraphActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_graph)

        FirebaseCrashlytics.getInstance().setUserId("12345")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHostFragment.navController

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.characterListFragment,
                R.id.episodeListFragment,
                R.id.characterSearchFragment,
                R.id.drawer_layout
            ),drawerLayout
        )

        setupActionBarWithNavController(
            navController = navController,
            configuration = appBarConfiguration
        )
        navigationView.setupWithNavController(navController)

        navigationView.setCheckedItem(
            navController.graph.startDestinationId
        )
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}