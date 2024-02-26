package com.example.pressurenote.ui.healthlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pressurenote.databinding.AlertDialogAddIndicationBinding
import com.example.pressurenote.databinding.FragmentHealthListBinding
import com.example.pressurenote.model.AppState
import com.example.pressurenote.model.Health
import com.example.pressurenote.model.Indication
import com.example.pressurenote.ui.healthlist.adapters.health.HealthAdapter
import com.example.pressurenote.utils.Utils.getDate
import com.example.pressurenote.utils.Utils.getTime
import com.example.pressurenote.utils.isVisible
import com.example.pressurenote.utils.toastFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HealthListFragment : Fragment() {
    private var _binding: FragmentHealthListBinding? = null
    private val binding get() = _binding!!

    private var _bindingAddIndicationDialog: AlertDialogAddIndicationBinding? = null
    private val bindingAddIndicationDialog get() = _bindingAddIndicationDialog!!

    private val alertDialog: AlertDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setView(bindingAddIndicationDialog.root)
            .create()
    }

    private val viewModel: HealthListViewModel by viewModels()

    @Inject
    lateinit var adapter: HealthAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthListBinding.inflate(inflater, container, false)
        _bindingAddIndicationDialog = AlertDialogAddIndicationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewModel.healthLiveData.observe(viewLifecycleOwner) { appState ->
            renderData(appState)
        }

        binding.btnAddIndication.setOnClickListener {
            showAddIndicationAlertDialog()
        }
    }

    private fun initAdapter() {
        binding.rvIndications.adapter = adapter
        adapter.setOnItemClickListener {
            viewModel.deleteIndication(it)
        }
    }

    private fun renderData(appState: AppState<List<Health>, DatabaseError>) {
        binding.apply {
            when (appState) {
                is AppState.Loading -> {
                    pbMainLoading.isVisible(true, tvEmptyHealthList)
                    pbMainLoading.isVisible(true, mainLayout)
                }

                is AppState.Success -> {
                    if (appState.data.isEmpty()) {
                        pbMainLoading.isVisible(false, tvEmptyHealthList)
                        mainLayout.isVisible(false, tvEmptyHealthList)
                    } else {
                        pbMainLoading.isVisible(false, mainLayout)
                        tvEmptyHealthList.isVisible(false, mainLayout)
                    }

                    adapter.differ.submitList(appState.data)
                }

                is AppState.Error -> {
                    tvEmptyHealthList.isVisible(false, mainLayout)
                    pbMainLoading.isVisible(false, mainLayout)
                    toastFragment("Error: ${appState.error}")
                }
            }
        }
    }

    private fun showAddIndicationAlertDialog() {

        bindingAddIndicationDialog.apply {
            btnAddIndication.setOnClickListener {
                if (!textUpperInputEditAlertDialog.text.isNullOrEmpty() &&
                    !textLowerInputEditAlertDialog.text.isNullOrEmpty() &&
                    !textHeartRateInputEditAlertDialog.text.isNullOrEmpty()
                ) {
                    val indication =
                        Indication(
                            date = getDate(),
                            time = getTime(),
                            upperPressure = textUpperInputEditAlertDialog.text.toString(),
                            lowerPressure = textLowerInputEditAlertDialog.text.toString(),
                            pulse = textHeartRateInputEditAlertDialog.text.toString()
                        )

                    viewModel.setIndication(indication)
                    clearDialogFields()
                    alertDialog.dismiss()
                }
            }
            btnCancelIndication.setOnClickListener {
                clearDialogFields()
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }

    private fun clearDialogFields() {
        bindingAddIndicationDialog.apply {
            textUpperInputEditAlertDialog.text?.clear()
            textUpperInputEditAlertDialog.clearFocus()

            textLowerInputEditAlertDialog.text?.clear()
            textLowerInputEditAlertDialog.clearFocus()

            textHeartRateInputEditAlertDialog.text?.clear()
            textHeartRateInputEditAlertDialog.clearFocus()
        }
    }

    override fun onDestroy() {
        _binding = null
        _bindingAddIndicationDialog = null
        super.onDestroy()
    }

}