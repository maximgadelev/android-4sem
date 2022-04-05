package com.example.web_app.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.web_app.App
import com.example.web_app.R
import com.example.web_app.di.AppComponent

class MainActivity : AppCompatActivity() {
    lateinit var appComponent: AppComponent

    private lateinit var controller: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as App).appComponent
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller =
            (supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment).navController
    }

    override fun onNavigateUp(): Boolean {
        return controller.navigateUp()
    }
}