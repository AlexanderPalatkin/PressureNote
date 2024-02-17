package com.example.pressurenote.ui.healthlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pressurenote.databinding.HealthRecyclerItemBinding
import com.example.pressurenote.model.Health
import javax.inject.Inject

class HealthAdapter @Inject constructor(private val adapter: IndicationAdapter) :
    RecyclerView.Adapter<HealthAdapter.HealthViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthViewHolder {
        val binding = HealthRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HealthViewHolder(binding, adapter)
    }

    override fun onBindViewHolder(holder: HealthViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class HealthViewHolder(
        private val binding: HealthRecyclerItemBinding,
        private val adapter: IndicationAdapter
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(health: Health) {
            binding.apply {
                tvDate.text = health.date
                adapter.differ.submitList(health.indications)
                rvIndications.adapter = adapter
            }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Health>() {
        override fun areItemsTheSame(oldItem: Health, newItem: Health): Boolean {
            return oldItem.indications == newItem.indications
        }

        override fun areContentsTheSame(oldItem: Health, newItem: Health): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)
}