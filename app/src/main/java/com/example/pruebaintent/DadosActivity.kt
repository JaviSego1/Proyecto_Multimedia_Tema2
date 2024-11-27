package com.example.pruebaintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
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
        binding = ActivityDadosBinding.inflate(layoutInflater)  // Aquí usas el binding correcto
        setContentView(binding.root)
        initEvent()

        // Configurar el Spinner
        val duracionOptions = arrayOf("3 segundos", "6 segundos", "10 segundos")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, duracionOptions)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDuracion.adapter = spinnerAdapter

        // Establecer un listener para el Spinner
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
        binding.imageButton.setOnClickListener {
            binding.txtResultado.visibility = View.VISIBLE
            game()
        }
    }

    private fun game() {
        sheduleRun()
    }

    private fun sheduleRun() {
        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        val msc = 1000

        // Llamamos a lanzar los dados durante el tiempo seleccionado
        val veces = duracionTirada / msc  // Determinar cuántas veces lanzar los dados
        for (i in 1..veces) {
            schedulerExecutor.schedule(
                {
                    throwDadoInTime()  // Lanzamos los tres dados.
                },
                msc * i.toLong(), TimeUnit.MILLISECONDS
            )
        }

        schedulerExecutor.schedule({ // El último hilo es mostrar el resultado.
            viewResult()
        },
            msc * (veces + 2).toLong(), TimeUnit.MILLISECONDS)  // Tiempo adicional para mostrar el resultado

        schedulerExecutor.shutdown()  // Ya no aceptamos más hilos.
    }

    private fun throwDadoInTime() {
        val numDados = Array(3) { Random.nextInt(1, 7) }
        val imagViews: Array<ImageView> = arrayOf(
            binding.imagviewDado1,
            binding.imagviewDado2,
            binding.imagviewDado3
        )

        sum = numDados.sum()  // Nos quedamos con la suma actual
        for (i in 0..2)  // Cambiamos las imágenes a razón de los aleatorios.
            selectView(imagViews[i], numDados[i])
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
    }
}
