package com.example.pruebaintent

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebaintent.databinding.ActivityConfiguracionBinding

class ConfiguracionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = arrayOf("Primer Curso", "Segundo Curso", "Tercer Curso")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.button.setOnClickListener {
            Toast.makeText(this, "Informacion enviada", Toast.LENGTH_SHORT).show()
        }

        binding.toggleButtonFormulario.setOnCheckedChangeListener { _, isChecked ->
            val status = if (isChecked) "Activado" else "Desactivado"
            Toast.makeText(this, "Estado: $status", Toast.LENGTH_SHORT).show()
        }

        binding.buttonVolver.setOnClickListener {
            finish()
        }
    }
}
