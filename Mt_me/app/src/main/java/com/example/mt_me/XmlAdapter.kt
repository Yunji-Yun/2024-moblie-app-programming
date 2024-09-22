package com.example.mt_me

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mt_me.databinding.ItemMainBinding

class XmlViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)
class XmlAdapter(val datas:MutableList<myXmlItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return XmlViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as XmlViewHolder).binding
        val model = datas!![position]

        binding.name.text = model.mntnnm
        binding.mntninfodscrt.text = model.mntninfodscrt
        binding.crcmrsghtnginfoetcdscrt.text = model.crcmrsghtnginfoetcdscrt

        // 이미지 불러오기
        var image = model.hndfmsmtnmapimageseq.toString().trim()
        Glide.with(binding.root)
            .load(image)
            .override(400, 300)
            .into(binding.urlImage)
    }
}