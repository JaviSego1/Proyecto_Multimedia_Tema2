package com.example.pruebaintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.view.animation.AnimationUtils
import com.example.pruebaintent.databinding.ActivityDadosBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class DadosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDadosBinding
    private var sum: Int = 0
    private var duracionTirada: Int = 3000  // Default to 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDadosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initEvent()

        val duracionOptions = arrayOf("3 segundos", "6 segundos", "10 segundos")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, duracionOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDuracion.adapter = spinnerAdapter

        binding.spinnerDuracion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                duracionTirada = when (position) {
                    0 -> 3000  // 3 segundos
                    1 -> 6000  // 6 segundos
                    2 -> 10000  // 10 segundos
                    else -> 3000  // Default
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.buttonVolver.setOnClickListener {
            finish()
        }
    }

    private fun initEvent() {
        binding.txtResultado.visibility = View.INVISIBLE
        binding.txtFraseMotivadora.visibility = View.INVISIBLE  // Invisible initially

        binding.imageButton.setOnClickListener {
            binding.txtResultado.visibility = View.VISIBLE
            binding.txtFraseMotivadora.visibility = View.VISIBLE  // Show the motivational phrase
            game()
        }
    }

    private fun game() {
        sheduleRun()
    }

    private fun sheduleRun() {
        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        val msc = 1000

        val veces = duracionTirada / msc
        for (i in 1..veces) {
            schedulerExecutor.schedule(
                {
                    throwDadoInTime()
                },
                msc * i.toLong(), TimeUnit.MILLISECONDS
            )
        }

        schedulerExecutor.schedule({
            viewResult()
        },
            msc * (veces + 2).toLong(), TimeUnit.MILLISECONDS)

        schedulerExecutor.shutdown()
    }

    private fun throwDadoInTime() {
        val numDados = Array(3) { Random.nextInt(1, 7) }
        val imagViews: Array<ImageView> = arrayOf(
            binding.imagviewDado1,
            binding.imagviewDado2,
            binding.imagviewDado3
        )

        sum = numDados.sum()  // Nos quedamos con la suma actual
        for (i in 0..2) {
            selectView(imagViews[i], numDados[i])

            // Cargar la animación de rotación
            val rotateAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_dice)

            // Aplicar la animación al ImageView
            imagViews[i].startAnimation(rotateAnim)
        }
    }

    private fun selectView(imgV: ImageView, v: Int) {
        when (v) {
            1 -> imgV.setImageResource(R.drawable.dado1)
            2 -> imgV.setImageResource(R.drawable.dado2)
            3 -> imgV.setImageResource(R.drawable.dado3)
            4 -> imgV.setImageResource(R.drawable.dado4)
            5 -> imgV.setImageResource(R.drawable.dado5)
            6 -> imgV.setImageResource(R.drawable.dado6)
        }
    }

    private fun viewResult() {
        binding.txtResultado.text = sum.toString()
        println(sum)

        // Mostrar frase motivadora según el número obtenido
        val frase = when (sum) {
            3 -> "¡A veces el inicio es difícil, pero lo lograrás!"
            4 -> "¡Cada paso que das te acerca más a tu meta!"
            5 -> "¡No te detengas, el camino ya está trazado!"
            6 -> "¡La suerte está contigo, sigue adelante!"
            7 -> "¡Un pequeño avance es un gran paso hacia el éxito!"
            8 -> "¡Lo estás haciendo bien, cada esfuerzo cuenta!"
            9 -> "¡La victoria está cerca, no pares ahora!"
            10 -> "¡Eres fuerte, lo estás logrando!"
            11 -> "¡Sigue luchando, los buenos resultados llegarán!"
            12 -> "¡Tu perseverancia está dando frutos!"
            13 -> "¡No hay límites, solo oportunidades!"
            14 -> "¡Sigue adelante, el éxito está al alcance!"
            15 -> "¡Cada intento te acerca más a la victoria!"
            16 -> "¡Con cada paso, te estás acercando a tus sueños!"
            17 -> "¡No hay nada que te detenga, sigue adelante!"
            18 -> "¡Lo has logrado, tu esfuerzo ha sido recompensado!"
            else -> "¡Sigue así, nunca pares de intentarlo!"
        }

        binding.txtFraseMotivadora.text = frase
    }
}
