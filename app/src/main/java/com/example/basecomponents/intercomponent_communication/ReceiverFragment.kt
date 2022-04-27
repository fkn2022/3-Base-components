package com.example.basecomponents.intercomponent_communication

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.basecomponents.R
import com.example.basecomponents.databinding.FragmentReceiverBinding

class ReceiverFragment : Fragment() {

    companion object {
        fun newInstance() = ReceiverFragment()
    }

    private val viewModel: GyroscopeViewModel by activityViewModels()

    private var _binding: FragmentReceiverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiverBinding.inflate(inflater, container, false)
//        viewModel = ViewModelProvider(this)[ReceiverViewModel::class.java]
//        binding.lifecycleOwner = viewLifecycleOwner
//        binding.interComponentViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.text.observe(viewLifecycleOwner) {
            binding.receivedText.text = it
        }
    }
}