package com.example.pruebaintent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebaintent.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val enlace = "https://www.ujaen.es/"

        binding.buttonDados.setOnClickListener{
            startActivity(Intent(this, DadosActivity::class.java))
        }

        binding.buttonConfiguracion.setOnClickListener{
            startActivity(Intent(this, ConfiguracionActivity::class.java))
        }

        binding.buttonLlamada.setOnClickListener{
            startActivity(Intent(this, ConfActivity::class.java))
        }


        binding.buttonUrl.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(enlace))
            startActivity(intent)
        }

        binding.buttonAlarma.setOnClickListener {
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

        binding.buttonCorreo.setOnClickListener {
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






