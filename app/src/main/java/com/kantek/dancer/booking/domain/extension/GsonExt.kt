package com.kantek.dancer.booking.domain.extension

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream

inline fun <reified T> String.toObject() = Gson().fromJson(this, T::class.java)!!

fun <T> String.toObjects(clazz: Class<T>): MutableList<T> {
    return Gson().fromJson(this, TypeToken.getParameterized(MutableList::class.java, clazz).type)!!
}

fun <E> MutableList<E>.toJson() = Gson().toJson(this)!!

infix fun Context.toJsonFromFile(jsonFileName: String): String? {
    val result: String? = try {
        val fis: InputStream = assets.open(jsonFileName)
        val len: Int = fis.available()
        val bytes = ByteArray(len)
        fis.read(bytes)
        fis.close()
        String(bytes, Charsets.UTF_8)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return result
}

fun <E> E.toJson() = Gson().toJson(this)!!