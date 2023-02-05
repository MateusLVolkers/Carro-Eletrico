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
import com.example.carroeletriconovo.data.local.CarsContract.CarEntry.TABLE_NAME
import com.example.carroeletriconovo.domain.Carro

class CarRepository (private val context: Context) {

    fun save(carro: Carro): Boolean{
        var isSaved = false
        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase


            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, carro.id)
                put(COLUMN_NAME_PRECO, carro.preco)
                put(COLUMN_NAME_BATERIA, carro.bateria)
                put(COLUMN_NAME_POTENCIA, carro.potencia)
                put(COLUMN_NAME_RECARGA, carro.recarga)
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

    fun delete(carro: Carro) {
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.writableDatabase

        db?.delete(TABLE_NAME,carro.id.toString(),null)


    }


    fun findCarById(id: Int): Carro {

        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        val columns = arrayOf(BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO)

        val filter = "$COLUMN_NAME_CAR_ID = ?"
        val filterValues = arrayOf(id.toString()) //somente para converter o parâmetro id em string

        val cursor = db.query(
            TABLE_NAME, // nome da tabela
            columns, //colunas a serem exibidas
            filter, // filtro (filter)
            filterValues, //
            null,
            null,
            null
        )

            var itemID: Long = 0
            var preco = ""
            var bateria = ""
            var potencia = ""
            var recarga = ""
            var urlPhoto = ""

            with(cursor){
                while (moveToNext()){
                     itemID = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                     Log.d("ID", itemID.toString())
                     preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                     Log.d("preco", preco)
                     bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                     Log.d("bateria", bateria)
                     potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                     Log.d("potencia", potencia)
                     recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                     Log.d("recarga", recarga)
                     urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                     Log.d("urlPhoto", urlPhoto)
                }
            }

        cursor.close()
        return Carro(
            id = itemID.toInt(),
            preco = preco,
            bateria = bateria,
            potencia = potencia,
            recarga = recarga,
            urlPhoto = urlPhoto,
            isFavorite = true
        )

    }

    fun saveIfNotExist(carro: Carro){
        val car = findCarById(carro.id)
        //Linha 70 -> o itemID é inicializado como 0
        if (car.id == ID_WHEN_NO_CAR){
            save(carro)
        }
    }

    fun getAllFavorites() : List<Carro>{
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        val columns = arrayOf(BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_URL_PHOTO)

        val cursor = db.query(
            TABLE_NAME, // nome da tabela
            columns, //colunas a serem exibidas
            null,
            null,
            null,
            null,
            null
        )

        val carroList = mutableListOf<Carro>()

        with(cursor){
            while (moveToNext()){
                val itemID = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                Log.d("ID", itemID.toString())
                val preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                Log.d("preco", preco)
                val bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                Log.d("bateria", bateria)
                val potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                Log.d("potencia", potencia)
                val recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                Log.d("recarga", recarga)
                val urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.d("urlPhoto", urlPhoto)

                carroList.add(
                    Carro(
                        id = itemID.toInt(),
                        preco = preco,
                        bateria = bateria,
                        potencia = potencia,
                        recarga = recarga,
                        urlPhoto = urlPhoto,
                        isFavorite = true
                    )
                )

            }
        }

        cursor.close()
        return carroList

    }

    companion object{
        const val ID_WHEN_NO_CAR = 0
    }

}
