package com.example.pruebaintent

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class ConfActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var zonaTelefono: EditText
    private lateinit var editarTelefono: ImageView
    private var telefono: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_activity_explicit)
        enableEdgeToEdge()

        sharedPreferences = getSharedPreferences("PreferenciasApp", MODE_PRIVATE)

        zonaTelefono = findViewById(R.id.zonaTelefono)
        val botonLlamar = findViewById<Button>(R.id.buttonLlamar)
        editarTelefono = findViewById(R.id.editarTelefono)

        val numeroGuardado = sharedPreferences.getString("numeroTelefono", "")
        zonaTelefono.setText(numeroGuardado)
        zonaTelefono.isEnabled = false

        editarTelefono.setOnClickListener {
            zonaTelefono.isEnabled = true
            zonaTelefono.requestFocus()
        }


        botonLlamar.setOnClickListener {
            val numero = zonaTelefono.text.toString()
            if (numero.isBlank()) {
                Toast.makeText(this, "Introduce un número de teléfono", Toast.LENGTH_SHORT).show()
            } else {
                sharedPreferences.edit().putString("numeroTelefono", numero).apply()
                telefono = numero
                hacerLlamada(numero)
            }
        }

        val botonRegresar = findViewById<Button>(R.id.buttonVolver)
        botonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun hacerLlamada(numero: String) {
        val llamadaIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$numero"))
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 101)
        } else {
            startActivity(llamadaIntent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            telefono?.let { hacerLlamada(it) }
        } else {
            Toast.makeText(this, "Permiso de llamada no otorgado", Toast.LENGTH_SHORT).show()
        }
    }

}

