package com.example.basecomponents.intercomponent_communication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GyroscopeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    fun changeCoord(x: Float?, y: Float?, z: Float?) {
        _text.value = "X: $x; Y: $y; Z: $z;"
    }
}