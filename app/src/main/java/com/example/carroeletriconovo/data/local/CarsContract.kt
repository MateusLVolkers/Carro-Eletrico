package com.example.carroeletriconovo.data.local

import android.provider.BaseColumns

object CarsContract {

    object CarEntry: BaseColumns{
        const val TABLE_NAME = "car"
        const val COLUMN_NAME_CAR_ID = "ID"
        const val COLUMN_NAME_PRECO = "Preço"
        const val COLUMN_NAME_BATERIA = "bateria"
        const val COLUMN_NAME_POTENCIA = "Potência"
        const val COLUMN_NAME_RECARGA = "Recarga"
        const val COLUMN_NAME_URL_PHOTO = "UrlPhoto"
    }

    const val TABLE_CAR = "CREATE TABLE ${CarEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTERGER PRIMARY KEY," +
                "${CarEntry.COLUMN_NAME_CAR_ID} TEXT" +
                "${CarEntry.COLUMN_NAME_PRECO} TEXT," +
                "${CarEntry.COLUMN_NAME_BATERIA} TEXT," +
                "${CarEntry.COLUMN_NAME_POTENCIA} TEXT," +
                "${CarEntry.COLUMN_NAME_RECARGA} TEXT," +
                "${CarEntry.COLUMN_NAME_URL_PHOTO} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"

}