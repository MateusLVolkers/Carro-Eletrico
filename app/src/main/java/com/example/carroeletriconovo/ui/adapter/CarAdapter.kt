package com.example.carroeletriconovo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carroeletriconovo.R
import com.example.carroeletriconovo.domain.Carro

class CarAdapter(val carros: List<Carro>, val isFavoriteScreen: Boolean = false) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemLister: (Carro) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carro_rv, parent, false)
        return ViewHolder(view)
    }

    //Troca a informação da view pela informação do item da lista de acordo com a posição
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.preco.text = carros[position].preco
        holder.bateria.text = carros[position].bateria
        holder.recarga.text = carros[position].recarga
        holder.potencia.text = carros[position].potencia

        if (isFavoriteScreen){
            holder.favorito.setImageResource(R.drawable.ic_baseline_star_selected_24)
        }

        holder.favorito.setOnClickListener{
            val carro = carros[position]
            carItemLister(carro)
            setupFavorito(carro, holder)
        }
    }

    private fun setupFavorito(carro: Carro, holder: ViewHolder) {
        carro.isFavorite = !carro.isFavorite

        if (carro.isFavorite) {
            holder.favorito.setImageResource(R.drawable.ic_baseline_star_selected_24)
        } else {
            holder.favorito.setImageResource(R.drawable.ic_baseline_star_border_24)
        }
    }

    //Qtd total de itens na lista
    override fun getItemCount(): Int = carros.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val preco: TextView
        val bateria: TextView
        val recarga: TextView
        val potencia: TextView
        val favorito: ImageView

        init {
            view.apply {
                preco = findViewById(R.id.txt_valor_preco_id)
                bateria = findViewById(R.id.bateria_valor_id)
                recarga = findViewById(R.id.recarga_valor_id)
                potencia = findViewById(R.id.potencia_valor_id)
                favorito = findViewById(R.id.star_fav_id)
            }
        }
    }

}


