package com.example.carroeletriconovo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carroeletriconovo.R
import com.example.carroeletriconovo.databinding.CarroRvBinding
import com.example.carroeletriconovo.domain.Carro

class CarAdapter(private val carros: List<Carro>) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemLister: (Carro) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CarroRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    //Troca a informação da view pela informação do item da lista de acordo com a posição
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(carros[position])
    }

    //Qtd total de itens na lista
    override fun getItemCount(): Int = carros.size

    inner class ViewHolder(val binding: CarroRvBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.starFavId.setOnClickListener {
                val carro = carros[adapterPosition]
                carItemLister(carro)
                setupFavorito(carro)
            }
        }

        fun render(carro: Carro) {
            binding.txtValorPrecoId.text = carro.preco
            binding.bateriaValorId.text = carro.bateria
            binding.recargaValorId.text = carro.recarga
            binding.potenciaValorId.text = carro.potencia

            Glide.with(binding.root)
                .load(carro.urlPhoto)
                .into(binding.imgCarId)

            if (carro.isFavorite) {
                binding.starFavId.setImageResource(R.drawable.ic_baseline_star_selected_24)
            }
        }

        private fun setupFavorito(carro: Carro) {
            carro.isFavorite = !carro.isFavorite

            if (carro.isFavorite) {
                binding.starFavId.setImageResource(R.drawable.ic_baseline_star_selected_24)
            } else {
                binding.starFavId.setImageResource(R.drawable.ic_baseline_star_border_24)
            }
        }
    }

}


