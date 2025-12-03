package com.example.a6lr

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class DeleteParrotActivity : AppCompatActivity() {
    private lateinit var database: ParrotDatabase
    private var parrotId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = ParrotDatabase.getDatabase(this)
        parrotId = intent.extras?.getLong("parrotId", -1) ?: -1

        if (parrotId == -1L) {
            finish()
            return
        }

        AlertDialog.Builder(this)
            .setTitle("Удалить попугая?")
            .setMessage("Вы уверены, что хотите удалить этого попугая?")
            .setPositiveButton("Да") { _, _ ->
                lifecycleScope.launch {
                    val parrot = database.parrotDao().getParrotById(parrotId)
                    if (parrot != null) {
                        database.parrotDao().deleteParrot(parrot)
                        Toast.makeText(this@DeleteParrotActivity, "Попугай удалён!", Toast.LENGTH_SHORT).show()
                    }
                }
                finish()
            }
            .setNegativeButton("Нет") { _, _ -> finish() }
            .show()
    }
}