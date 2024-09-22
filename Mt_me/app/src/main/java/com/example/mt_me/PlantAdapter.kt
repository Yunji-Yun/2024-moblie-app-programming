package com.example.mt_me

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mt_me.databinding.ItemMain2Binding

class PlantViewHolder(val binding: ItemMain2Binding): RecyclerView.ViewHolder(binding.root)
class PlantAdapter(val datas:MutableList<myPlantItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantViewHolder(ItemMain2Binding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as PlantViewHolder).binding
        val model = datas!![position]

        binding.fskname.text = model.fskname
        binding.fsguide.text = model.fsguide
        binding.fslifetime.text = model.fslifetime
    }
}