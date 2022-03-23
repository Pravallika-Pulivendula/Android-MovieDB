package com.everest.moviedb.network

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<Int?>? {
        val listType: Type = object : TypeToken<ArrayList<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Int?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}