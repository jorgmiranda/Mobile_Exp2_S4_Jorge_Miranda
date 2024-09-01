package com.example.exp2_s3_jorge_miranda.classes

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    private val gson = Gson();

    // Guardar un objeto en SharedPreferences
    fun <T> saveObject(key: String, value:T){
        val json = gson.toJson(value)
        sharedPreferences.edit().putString(key,json).apply()

    }

    // Obtener un objeto de SharedPreferences
    fun <T> getObject(key:String, type: Class<T>): T?{
        val json = sharedPreferences.getString(key, null) ?: return null
        return gson.fromJson(json, type)
    }

    //Guardar una lista en SharedPreferences
    fun <T> saveList(key: String, list: List<T>){
        val json = gson.toJson(list)
        sharedPreferences.edit().putString(key,json).apply()
    }

    //Obtener lista de SharedPreferences
    fun <T> getList(key:String, typeToken: TypeToken<List<T>>):List<T>?{
        val json = sharedPreferences.getString(key, null) ?: return null
        return gson.fromJson(json, typeToken.type)
    }

    // Eliminar un valor de SharedPreferences
    fun removeValue(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}