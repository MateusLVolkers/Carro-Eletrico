package com.example.carroeletriconovo.ui

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.carroeletriconovo.R

class CalcularAutonomia: AppCompatActivity() {

    lateinit var edtPercorrido: EditText
    lateinit var edtKwh: EditText
    lateinit var btnCalcular: Button
    lateinit var txtResultado: TextView
    lateinit var btnClose: ImageView
    lateinit var resultAnterior: TextView
    var precoKwh: Double = 0.0
    var kmPercorrido: Double = 0.0
    var resultado: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_autonomia)

        setupView()
        setupListener()
        setupCachedResult()


    }

    fun setupView(){
        edtPercorrido = findViewById(R.id.edt_autonomia)
        edtKwh = findViewById(R.id.edt_valor_kwh)
        btnCalcular = findViewById(R.id.btn_calcular)
        txtResultado = findViewById(R.id.txt_resultado_id)
        btnClose = findViewById(R.id.btn_close)
        resultAnterior = findViewById(R.id.txt_resultado_anterior_id)
    }

    fun validarPreco(entrada: EditText): Boolean{
        val temp = entrada.text.toString()

        return if(temp.isNullOrBlank()){
            Toast.makeText(this, R.string.toast_valor_valido, Toast.LENGTH_SHORT).show()
            false
        } else {
            precoKwh = temp.toDouble()
            true
        }
    }

    fun validarKmPercorrido(entrada:EditText): Boolean {

        val temp = entrada.text.toString()

        return if(temp.isNullOrBlank()){
            Toast.makeText(this, R.string.toast_valor_valido, Toast.LENGTH_SHORT).show()
            false
        } else {
            kmPercorrido = temp.toDouble()
            true
        }
    }

    fun calcularesultado(preco: Double, distancia: Double){
        resultado = preco/distancia
        txtResultado.text = "R$/KM: $resultado"
        saveSharedPref(resultado)
    }

    fun setupListener(){
        btnCalcular.setOnClickListener {
            if (validarPreco(edtKwh).not()){
                return@setOnClickListener
            }
            if (validarKmPercorrido(edtPercorrido).not()){
                return@setOnClickListener
            }
            calcularesultado(precoKwh, kmPercorrido)
        }
        btnClose.setOnClickListener {
            finish()
        }
    }

    fun saveSharedPref(result: Double){
        val sharePref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharePref.edit()){
            putFloat(getString(R.string.saved_calc), result.toFloat())
            apply()

        }
    }

    fun getSharedPref() : Float{
        val sharePref = getPreferences(Context.MODE_PRIVATE)
        return sharePref.getFloat(getString(R.string.saved_calc), 0.0f)

        //val calculo = sharePref.getFloat(getString(R.string.saved_calc), 0.0f)
        //return calculo
    }

    fun setupCachedResult(){
        val valorCalcAnterior = getSharedPref()
        resultAnterior.text = valorCalcAnterior.toString()
    }

}