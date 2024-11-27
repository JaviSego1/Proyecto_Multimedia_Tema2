package com.example.pruebaintent

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import com.example.pruebaintent.databinding.ActivityChistesBinding
import java.util.Locale

class ChistesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChistesBinding
    private lateinit var textToSpeech: TextToSpeech
    private val TOUCH_MAX_TIME = 500
    private var touchLastTime: Long = 0
    private lateinit var handler: Handler
    val MYTAG = "LOGCAT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChistesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureTextToSpeech()
        initHander()
        initEvent()

        binding.buttonVolver.setOnClickListener {
            finish()
        }
    }

    private fun initHander() {
        handler = Handler(Looper.getMainLooper())
        binding.progressBar.visibility = View.VISIBLE
        binding.btnExample.visibility = View.GONE

        Thread {
            Thread.sleep(3000)
            handler.post {
                binding.progressBar.visibility = View.GONE  // Ocultamos el ProgressBar
                val description = getString(R.string.describe).toString()
                speakMeDescription(description)  // Que nos comente de qué va esto
                Log.i(MYTAG, "Se ejecuta correctamente el hilo")
                binding.btnExample.visibility = View.VISIBLE
            }
        }.start()
    }

    private fun configureTextToSpeech() {
        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if (it != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.getDefault()
                Log.i(MYTAG, "Sin problemas en la configuración TextToSpeech")
            } else {
                Log.i(MYTAG, "Error en la configuración TextToSpeech")
            }
        })
    }

    private fun initEvent() {
        // Lista con 10 chistes
        val chistes = listOf(
            "¿Por qué el libro de matemáticas estaba triste? Porque tenía demasiados problemas.",
            "¿Qué le dice una impresora a otra? ¿Esa hoja es tuya o es una impresión mía?",
            "¿Por qué los pájaros no usan Facebook? Porque ya tienen Twitter.",
            "¿Qué hace una abeja en el gimnasio? ¡Zum-ba!",
            "¿Cómo se despiden los químicos? Ácido un placer.",
            "¿Qué hace una computadora tomando café? ¡Procesar sus ideas!",
            "¿Por qué el tomate no toma decisiones? Porque siempre se queda en salsa.",
            "¿Cómo se llama un oso sin dientes? ¡Oso gomoso!",
            "¿Qué pasa si tiras un pato al agua? ¡Nada!",
            "¿Por qué los esqueletos no luchan entre ellos? Porque no tienen agallas."
        )

        binding.btnExample.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - touchLastTime < TOUCH_MAX_TIME) {
                // Selección aleatoria de un chiste
                val chisteAleatorio = chistes.random()
                executorDoubleTouch(chisteAleatorio) // Pasamos el chiste seleccionado
                Log.i(MYTAG, "Escuchamos el chiste: $chisteAleatorio")
            } else {
                Log.i(MYTAG, "Hemos pulsado 1 vez.")
                speakMeDescription("Botón para escuchar un chiste")
            }
            touchLastTime = currentTime
        }
    }

    private fun speakMeDescription(s: String) {
        Log.i(MYTAG, "Intenta hablar")
        textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun executorDoubleTouch(chiste: String) {
        speakMeDescription(chiste)
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}
