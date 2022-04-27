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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.basecomponents.R
import com.example.basecomponents.databinding.FragmentSenderBinding

class SenderFragment : Fragment(), SensorEventListener {

    companion object {
        fun newInstance() = SenderFragment()
    }

    private val viewModel: GyroscopeViewModel by activityViewModels()
    private lateinit var mSensorManager: SensorManager
    private lateinit var mGyroscope: Sensor

    private var _binding: FragmentSenderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSenderBinding.inflate(inflater, container, false)
//        viewModel = ViewModelProvider(this)[SenderViewModel::class.java]
//        binding.lifecycleOwner = viewLifecycleOwner
//        binding.interComponentViewModel = viewModel
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        mSensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        viewModel.text.observe(viewLifecycleOwner){
            binding.sentText.text = it
        }
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
        val x: Float? = p0?.values?.get(0)
        val y: Float? = p0?.values?.get(1)
        val z: Float? = p0?.values?.get(2)

        viewModel.changeCoord(x, y, z)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

}