package com.example.a6lr

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ViewParrotActivity : AppCompatActivity() {
    private lateinit var database: ParrotDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_parrot)

        database = ParrotDatabase.getDatabase(this)

        val parrotId = intent.extras?.getLong("parrotId", -1) ?: -1
        if (parrotId == -1L) {
            Toast.makeText(this, "Ошибка: попугай не найден", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val nameText = findViewById<TextView>(R.id.view_parrot_name)
        val speciesText = findViewById<TextView>(R.id.view_parrot_species)
        val ageText = findViewById<TextView>(R.id.view_parrot_age)

        lifecycleScope.launch {
            val parrot = database.parrotDao().getParrotById(parrotId)
            if (parrot != null) {
                nameText.text = parrot.name
                speciesText.text = parrot.species
                ageText.text = parrot.age.toString()
            } else {
                Toast.makeText(this@ViewParrotActivity, "Попугай не найден", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}