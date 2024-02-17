package com.example.pressurenote.ui.healthlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pressurenote.databinding.IndicationRecyclerItemBinding
import com.example.pressurenote.model.Indication
import javax.inject.Inject

class IndicationAdapter @Inject constructor() :
    RecyclerView.Adapter<IndicationAdapter.IndicationViewHolder>() {

    private lateinit var binding: IndicationRecyclerItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicationViewHolder {
        binding = IndicationRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return IndicationViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: IndicationViewHolder, position: Int) {
        holder.bind(differ.currentList[position], position % 2)
    }

    inner class IndicationViewHolder(private val binding: IndicationRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(indication: Indication, color: Int) {
            binding.apply {
                root.setBackgroundColor(color)
                indicationTime.text = indication.time
                indicationUpperPressure.text = indication.upperPressure.toString()
                indicationLowerPressure.text = indication.lowerPressure.toString()
                indicationPulse.text = indication.pulse.toString()
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Indication>() {
        override fun areItemsTheSame(oldItem: Indication, newItem: Indication): Boolean {
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: Indication, newItem: Indication): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}