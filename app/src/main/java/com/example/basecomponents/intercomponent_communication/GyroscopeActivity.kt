package com.example.basecomponents.intercomponent_communication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.basecomponents.R

class GyroscopeActivity : AppCompatActivity() {

    private lateinit var viewModel: GyroscopeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gyroscope)

        viewModel = ViewModelProvider(this)[GyroscopeViewModel::class.java]
    }
}