package com.example.a6lr
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ParrotAdapter(
    private val onItemClick: (Parrot) -> Unit,
    private val onItemLongClick: (Parrot) -> Unit
) : ListAdapter<Parrot, ParrotAdapter.ViewHolder>(ParrotDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parrot, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parrot = getItem(position)
        holder.bind(parrot)

        holder.itemView.setOnClickListener {
            onItemClick(parrot)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick(parrot)
            true // Обработка события
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: TextView = itemView.findViewById(R.id.parrot_name)
        private val speciesText: TextView = itemView.findViewById(R.id.parrot_species)
        private val ageText: TextView = itemView.findViewById(R.id.parrot_age)

        fun bind(parrot: Parrot) {
            nameText.text = parrot.name
            speciesText.text = parrot.species
            ageText.text = "Возраст: ${parrot.age}"
        }
    }
}

class ParrotDiffCallback : DiffUtil.ItemCallback<Parrot>() {
    override fun areItemsTheSame(oldItem: Parrot, newItem: Parrot): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Parrot, newItem: Parrot): Boolean {
        return oldItem == newItem
    }
}