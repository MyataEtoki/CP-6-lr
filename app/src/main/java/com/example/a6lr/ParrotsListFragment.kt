package com.example.a6lr

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class ParrotsListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ParrotAdapter
    private lateinit var database: ParrotDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_parrots_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        val addButton = view.findViewById<Button>(R.id.button_add_parrot)
        database = ParrotDatabase.getDatabase(requireContext())

        adapter = ParrotAdapter(
            onItemClick = { parrot ->
                // Клик (не долгое нажатие)
                // Здесь можно открыть детали, если нужно
            },
            onItemLongClick = { parrot ->
                // Долгое нажатие
                showOptionsDialog(parrot)
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Подписываемся на изменения из БД
        viewLifecycleOwner.lifecycleScope.launch {
            database.parrotDao().getAllParrots().collect { parrots ->
                adapter.submitList(parrots)
            }
        }

        // Обработка нажатия на кнопку "Добавить"
        addButton.setOnClickListener {
            val intent = Intent(requireContext(), AddParrotActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun showOptionsDialog(parrot: Parrot) {
        AlertDialog.Builder(requireContext())
            .setTitle("Выберите действие")
            .setItems(arrayOf("Просмотр", "Удалить", "Обновить")) { _, which ->
                when (which) {
                    0 -> // Просмотр
                        // Здесь можно открыть новый экран с деталями
                        // или просто показать Toast
                        android.widget.Toast.makeText(context, "Просмотр: ${parrot.name}", android.widget.Toast.LENGTH_SHORT).show()
                    1 -> {
                        val intent = Intent(requireContext(), DeleteParrotActivity::class.java).apply {
                            putExtra("parrotId", parrot.id)
                        }
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(requireContext(), UpdateParrotActivity::class.java).apply {
                            putExtra("parrotId", parrot.id)
                        }
                        startActivity(intent)
                    }
                }
            }
            .show()
    }
}