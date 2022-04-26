package com.example.basecomponents.intercomponent_communication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.basecomponents.R

class ReceiverFragment : Fragment() {

    companion object {
        fun newInstance() = ReceiverFragment()
    }

    private lateinit var viewModel: ReceiverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_receiver, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReceiverViewModel::class.java)
        // TODO: Use the ViewModel
    }

}