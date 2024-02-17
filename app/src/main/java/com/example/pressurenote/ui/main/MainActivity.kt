package com.example.pressurenote.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pressurenote.R
import com.example.pressurenote.ui.healthlist.HealthListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, HealthListFragment())
                .commit()

        }
    }
}