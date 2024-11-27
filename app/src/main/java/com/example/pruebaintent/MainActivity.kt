package com.example.pruebaintent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var buttonLlamada: Button
    private lateinit var buttonAlarma: Button
    private lateinit var buttonUrl: Button
    private lateinit var buttonCorreo: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val enlace = "https://www.ujaen.es/"
        buttonLlamada = findViewById(R.id.buttonLlamada)
        buttonAlarma = findViewById(R.id.buttonAlarma)
        buttonUrl = findViewById(R.id.buttonUrl)
        buttonCorreo = findViewById(R.id.buttonCorreo)


        buttonLlamada.setOnClickListener{
            startActivity(Intent(this, ConfActivity::class.java))
        }


        buttonUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(enlace))
            startActivity(intent)
        }

        buttonAlarma.setOnClickListener {
            val calendario = Calendar.getInstance()
            calendario.add(Calendar.MINUTE, 2)

            val hora = calendario.get(Calendar.HOUR_OF_DAY)
            val minuto = calendario.get(Calendar.MINUTE)
            val mensaje = "Despertador"

            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MINUTES, minuto)
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
                Toast.makeText(
                    this, "La alarma esta configurada para las $hora:$minuto con el mensaje: $mensaje",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, "No hay aplicaciones de alarma disponibles", Toast.LENGTH_SHORT)
                    .show()
            }

        }

        buttonCorreo.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("javiersegoviamartinez@gmail.com"))
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay aplicaciones de correo disponibles", Toast.LENGTH_SHORT).show()
            }
        }


    }
}






