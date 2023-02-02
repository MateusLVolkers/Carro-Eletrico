package com.example.carroeletriconovo.ui.fragment

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.carroeletriconovo.R
import com.example.carroeletriconovo.data.CarsAPI
import com.example.carroeletriconovo.data.local.CarRepository
import com.example.carroeletriconovo.domain.Carro
import com.example.carroeletriconovo.ui.CalcularAutonomia
import com.example.carroeletriconovo.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFragment : Fragment() {

    lateinit var listaCarros: RecyclerView
    lateinit var fabCalcular: FloatingActionButton
    lateinit var progressBar: ProgressBar
    lateinit var imgNoWifi: ImageView
    lateinit var txtNoWifi: TextView
    lateinit var carsAPI: CarsAPI

    var carrosArray : ArrayList<Carro> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.car_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()
        setupView(view)
        setupListener()
    }

    override fun onResume() {
        super.onResume()
        if(checkInternet(context)){
            //callService()
            getAllCars()
        } else{
            emptyState()
        }

    }

    fun emptyState(){
        listaCarros.isVisible = false
        progressBar.isVisible = false
        imgNoWifi.isVisible = true
        txtNoWifi.isVisible = true
    }

    fun setupRetrofit(){
        val builder = Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/MateusLVolkers/cars-api/main/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsAPI = builder.create(CarsAPI::class.java)
    }

    fun getAllCars(){
        carsAPI.getAllCars().enqueue(object: Callback<List<Carro>> {
            override fun onResponse(call: Call<List<Carro>>, response: Response<List<Carro>>) {
                if (response.isSuccessful){
                    progressBar.isVisible = false
                    imgNoWifi.isVisible = false
                    txtNoWifi.isVisible = false

                    response.body()?.let {
                        setupList(it)
                    }
                } else{
                    Toast.makeText(context, R.string.no_wifi, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Carro>>, t: Throwable) {
                Toast.makeText(context, R.string.no_wifi, Toast.LENGTH_LONG).show()
            }


        })
    }

    fun setupView(view: View){
        view.apply {
            listaCarros = findViewById(R.id.recyclerView_id)
            fabCalcular = findViewById(R.id.fab_calcular)
            progressBar = findViewById(R.id.progress)
            txtNoWifi = findViewById(R.id.txt_no_wifi)
            imgNoWifi = findViewById(R.id.img_wifi_off)
        }
    }

    fun setupList(list: List<Carro>){
        val carroAdapter = CarAdapter(list)

        listaCarros.apply {
            visibility = View.VISIBLE
            adapter = carroAdapter
        }

        carroAdapter.carItemLister = { carro ->
           val isSaved = CarRepository(requireContext()).saveIfNotExist(carro)
        }
    }

    fun setupListener(){
        fabCalcular.setOnClickListener{
            startActivity(Intent(context, CalcularAutonomia::class.java))
        }
    }


    fun checkInternet (context: Context?): Boolean{
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val network = connectivityManager.activeNetwork?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network)?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else{
            @Suppress{"DEPRECATION"}
            val networkInfo = connectivityManager.activeNetworkInfo?: return false
            return networkInfo.isConnected
        }

    }

//    fun callService(){
//        GetCarInformation().execute("https://raw.githubusercontent.com/MateusLVolkers/cars-api/main/cars.json")
//        progressBar.visibility = View.VISIBLE
//    }
//    inner class GetCarInformation : AsyncTask<String, String, String>(){
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//            Log.d("AsyncTask", "Iniciando")
//        }
//
//        override fun doInBackground(vararg url: String?): String {
//
//            var urlConnection: HttpURLConnection? = null
//
//            try {
//                val urlBase = URL(url[0])
//                urlConnection = urlBase.openConnection() as HttpURLConnection
//                urlConnection.connectTimeout = 60000
//                urlConnection.readTimeout = 60000
//                urlConnection.setRequestProperty("Accept", "application/json")
//
//                val responseCode = urlConnection.responseCode
//
//                if (responseCode == HttpURLConnection.HTTP_OK){
//                    var response = urlConnection.inputStream.bufferedReader().use { it.readText()}
//                        publishProgress(response)
//                } else{
//                    Log.d("Erro", "Erro no responseCode")
//                }
//
//            } catch (e: Exception){
//                Log.d("Erro", "Erro na url connection")
//            } finally {
//                urlConnection?.disconnect()
//            }
//            return ""
//        }
//
//        override fun onProgressUpdate(vararg values: String?) = try {
//            val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray
//
//            for(i in 0 until jsonArray.length()){
//                val id = jsonArray.getJSONObject(i).getString("id")
//                val preco = jsonArray.getJSONObject(i).getString("preco")
//                val recarga = jsonArray.getJSONObject(i).getString("recarga")
//                val potencia = jsonArray.getJSONObject(i).getString("potencia")
//                val bateria = jsonArray.getJSONObject(i).getString("bateria")
//                val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")
//
//                val model =  Carro(id = id.toInt(), preco = preco, recarga = recarga, potencia = potencia, bateria = bateria, urlPhoto = urlPhoto )
//
//                carrosArray.add(model)
//
//            }
//
//            //progressBar.isVisible = false
//            //imgNoWifi.isVisible = false
//            //txtNoWifi.isVisible = false
//            //setupList()
//
//        } catch (e: Exception){}
//
//    }

}