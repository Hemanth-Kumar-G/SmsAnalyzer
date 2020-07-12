package com.ankit.smsanalyzer.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ankit.smsanalyzer.R
import com.ankit.smsanalyzer.databinding.ItemSmsBinding

class SmsItemAdapter(private val items: List<SmsItem>) : RecyclerView.Adapter<SmsItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsItemViewHolder {
        return SmsItemViewHolder(DataBindingUtil.inflate<ItemSmsBinding>(LayoutInflater.from(parent.context), R.layout.item_sms,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SmsItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

}