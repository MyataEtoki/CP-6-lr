package com.example.a6lr

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ParrotAdapter(private val parrots: List<String>) : RecyclerView.Adapter<ParrotAdapter.ViewHolder>() {

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view.findViewById(android.R.id.text1))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = parrots[position]
    }

    override fun getItemCount() = parrots.size
}