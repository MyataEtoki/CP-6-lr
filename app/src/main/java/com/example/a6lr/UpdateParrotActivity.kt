package com.example.a6lr
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class UpdateParrotActivity : AppCompatActivity() {
    private lateinit var database: ParrotDatabase
    private var parrotId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_parrot)

        database = ParrotDatabase.getDatabase(this)
        parrotId = intent.extras?.getLong("parrotId", -1) ?: -1

        if (parrotId == -1L) {
            finish()
            return
        }

        val nameInput = findViewById<EditText>(R.id.input_name)
        val speciesInput = findViewById<EditText>(R.id.input_species)
        val ageInput = findViewById<EditText>(R.id.input_age)
        val saveButton = findViewById<Button>(R.id.button_save)

        saveButton.text = "Обновить"

        lifecycleScope.launch {
            val parrot = database.parrotDao().getParrotById(parrotId)
            if (parrot != null) {
                nameInput.setText(parrot.name)
                speciesInput.setText(parrot.species)
                ageInput.setText(parrot.age.toString())
            }
        }

        saveButton.setOnClickListener {
            val name = nameInput.text.toString()
            val species = speciesInput.text.toString()
            val age = ageInput.text.toString().toIntOrNull()

            if (name.isNotEmpty() && species.isNotEmpty() && age != null) {
                val updatedParrot = Parrot(id = parrotId, name = name, species = species, age = age)
                lifecycleScope.launch {
                    database.parrotDao().updateParrot(updatedParrot)
                    Toast.makeText(this@UpdateParrotActivity, "Попугай обновлён!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
    }
}