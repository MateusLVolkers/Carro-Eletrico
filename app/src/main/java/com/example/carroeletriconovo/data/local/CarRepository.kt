package com.example.carroeletriconovo.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.carroeletriconovo.data.local.CarsContract.CarEntry.COLUMN_NAME_BATERIA
import com.example.carroeletriconovo.data.local.CarsContract.CarEntry.COLUMN_NAME_CAR_ID
import com.example.carroeletriconovo.data.local.CarsContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.carroeletriconovo.data.local.CarsContract.CarEntry.COLUMN_NAME_PRECO
import com.example.carroeletriconovo.data.local.CarsContract.CarEntry.COLUMN_NAME_RECARGA
import com.example.carroeletriconovo.data.local.CarsContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.example.carroeletriconovo.domain.Carro

class CarRepository (val context: Context) {

    fun save(carro: Carro): Boolean{
        var isSaved = false
        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, carro.id)
                put(CarsContract.CarEntry.COLUMN_NAME_PRECO, carro.preco)
                put(CarsContract.CarEntry.COLUMN_NAME_BATERIA, carro.bateria)
                put(CarsContract.CarEntry.COLUMN_NAME_POTENCIA, carro.potencia)
                put(CarsContract.CarEntry.COLUMN_NAME_RECARGA, carro.recarga)
                put(CarsContract.CarEntry.COLUMN_NAME_URL_PHOTO, carro.urlPhoto)
            }
            val newRegister = db?.insert(CarsContract.CarEntry.TABLE_NAME, null, values)

            if (newRegister != null){
                isSaved = true
            }
        } catch (e: Exception){
                e.message?.let {
                    Log.e("Erro", it)
                }
        }
        return isSaved
    }


    fun findCarById(id: Int): Carro{
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        val columns = arrayOf(BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO)

        val filter = "${COLUMN_NAME_CAR_ID} = ?"
        val filterValues = arrayOf(id.toString()) //somente para converter o parâmetro id em string
        val cursor = db.query(
            CarsContract.CarEntry.TABLE_NAME, // nome da tabela
            columns, //colunas a serem exibidas
            filter, // filtro (filter)
            filterValues, //
            null,
            null,
            null
        )

        val itemCarFavorite = mutableListOf<Carro>()
            with(cursor){
                while (moveToNext()){
                    val itemID = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                }
            }
        cursor.close()

    }

}