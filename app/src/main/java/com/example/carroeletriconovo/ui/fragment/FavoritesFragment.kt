package com.example.carroeletriconovo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.carroeletriconovo.R
import com.example.carroeletriconovo.data.local.CarRepository
import com.example.carroeletriconovo.domain.Carro
import com.example.carroeletriconovo.ui.adapter.CarAdapter

class FavoritesFragment : Fragment() {

    lateinit var listaCarrosFavoritos: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favoritos_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()


    }


    fun setupView(view: View){
        view.apply {
            listaCarrosFavoritos = findViewById(R.id.rv_lista_carros_favoritos_id)
        }
    }

    fun getCarsOnLocalDb(): List<Carro>{
        val repository = CarRepository(requireContext())
        val carList = repository.getAllFavorites()
        return carList
    }

    fun setupList(){
        val cars = getCarsOnLocalDb()
        val carroAdapterFavoritos = CarAdapter(cars)

        listaCarrosFavoritos.apply {
            isVisible = true
            adapter = carroAdapterFavoritos
        }
    }

}