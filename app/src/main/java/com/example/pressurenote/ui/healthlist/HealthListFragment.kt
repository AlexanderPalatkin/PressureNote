package com.example.pressurenote.ui.healthlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pressurenote.databinding.FragmentHealthListBinding
import com.example.pressurenote.model.Indication
import com.example.pressurenote.ui.healthlist.adapters.HealthAdapter
import com.example.pressurenote.utils.Utils.getTime
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HealthListFragment : Fragment() {
    private var _binding: FragmentHealthListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HealthListViewModel by viewModels()

    @Inject
    lateinit var adapter: HealthAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.healthLiveData.observe(viewLifecycleOwner) {
            adapter.differ.submitList(it)
        }

        binding.apply {
            rvIndications.adapter = adapter

            btnAddIndication.setOnClickListener {
                val indication =
                    Indication(
                        time = getTime(),
                        upperPressure = (100..200).random(),
                        lowerPressure = (60..120).random(),
                        pulse = (50..150).random()
                    )
                viewModel.setIndication(indication)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}