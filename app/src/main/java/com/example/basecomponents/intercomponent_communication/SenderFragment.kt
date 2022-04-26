package com.example.basecomponents.intercomponent_communication

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_GYROSCOPE
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basecomponents.R
import com.example.basecomponents.databinding.FragmentSenderBinding

class SenderFragment : Fragment(), SensorEventListener {

    companion object {
        fun newInstance() = SenderFragment()
        var i = 0
    }

    private lateinit var viewModel: SenderViewModel
    private lateinit var mSensorManager: SensorManager
    private lateinit var mGyroscope: Sensor

    private var _binding: FragmentSenderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSenderBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[SenderViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        binding.senderViewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SenderViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onStart() {
        super.onStart()
        mSensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mGyroscope = mSensorManager.getDefaultSensor(TYPE_GYROSCOPE)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        i++
        val text = i.toString() + " SensorChanged: "+ (p0?.sensor?.name ?: "unknown sensor")
        binding.sentText.text = text
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
//        i++
//        val text = i.toString() + " SensorChanged: "+ (p0?.name ?: "unknown sensor")
//        binding.sentText.text = text
    }

}