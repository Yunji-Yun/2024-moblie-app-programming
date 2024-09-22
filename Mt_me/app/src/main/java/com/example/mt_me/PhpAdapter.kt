package com.example.mt_me

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mt_me.databinding.ItemMinfoBinding

class PhpViewHolder(val binding: ItemMinfoBinding) : RecyclerView.ViewHolder(binding.root)

class PhpAdapter (val context: Context, val itemList: ArrayList<MinfoData>): RecyclerView.Adapter<PhpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhpViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PhpViewHolder(ItemMinfoBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: PhpViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            tvName.text = data.MtName
            tvC.text = data.Course

            root.setOnClickListener {
                Toast.makeText(context, "Root", Toast.LENGTH_LONG).show()
                Intent(context, MtActivity::class.java).apply {
                    putExtra("name", tvName.text)
                    putExtra("course", tvC.text)
                }.run {
                    context.startActivity(this)
                }
            }

            tvName.setOnClickListener {
                Toast.makeText(context, "Name", Toast.LENGTH_LONG).show()
            }

            imageView.setOnClickListener {
                Toast.makeText(context, "Image", Toast.LENGTH_LONG).show()
            }
        }
    }
}