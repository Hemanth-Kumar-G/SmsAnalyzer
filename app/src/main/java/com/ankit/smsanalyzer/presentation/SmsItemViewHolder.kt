package com.ankit.smsanalyzer.presentation

import androidx.recyclerview.widget.RecyclerView
import com.ankit.smsanalyzer.databinding.ItemSmsBinding

class SmsItemViewHolder(private val binding: ItemSmsBinding ) :RecyclerView.ViewHolder(binding.root){
    fun bind(smsItem: SmsItem) {
        binding.smsItem = smsItem
    }

}