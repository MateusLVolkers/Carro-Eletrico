package com.example.carroeletriconovo.data

import com.example.carroeletriconovo.domain.Carro

object CarList {

    val list = listOf<Carro>(
        Carro(
            id = 1,
            preco = "R$: 100.00,00",
            bateria = "500 Kwh",
            potencia = "400 cv",
            recarga = "30 minutos",
            urlPhoto = "www.google.com.br",
            isFavorite = false),
        Carro(
            id = 2,
            preco = "R$: 200.00,00",
            bateria = "400 Kwh",
            potencia = "600 cv",
            recarga = "15 minutos",
            urlPhoto = "www.google.com.br",
            isFavorite = false),
        Carro(
            id = 3,
            preco = "R$: 50.00,00",
            bateria = "200 Kwh",
            potencia = "100 cv",
            recarga = "20 minutos",
            urlPhoto = "www.google.com.br",
            isFavorite = false),
        Carro(
            id = 4,
            preco = "R$: 80.00,00",
            bateria = "250 Kwh",
            potencia = "150 cv",
            recarga = "25 minutos",
            urlPhoto = "www.google.com.br",
            isFavorite = false),
        Carro(
            id = 5,
            preco = "R$: 150.00,00",
            bateria = "375 Kwh",
            potencia = "255 cv",
            recarga = "23 minutos",
            urlPhoto = "www.google.com.br",
            isFavorite = false)
    )


}