package com.example.a6lr

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddParrotActivity : AppCompatActivity() {
    private lateinit var database: ParrotDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_parrot)

        database = ParrotDatabase.getDatabase(this)

        val nameInput = findViewById<EditText>(R.id.input_name)
        val speciesInput = findViewById<EditText>(R.id.input_species)
        val ageInput = findViewById<EditText>(R.id.input_age)
        val saveButton = findViewById<Button>(R.id.button_save)

        saveButton.setOnClickListener {
            val name = nameInput.text.toString()
            val species = speciesInput.text.toString()
            val age = ageInput.text.toString().toIntOrNull()

            if (name.isNotEmpty() && species.isNotEmpty() && age != null) {
                val newParrot = Parrot(name = name, species = species, age = age)
                lifecycleScope.launch {
                    database.parrotDao().insertParrot(newParrot)
                    Toast.makeText(this@AddParrotActivity, "Попугай добавлен!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }
}