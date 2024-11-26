package kr.apo2073.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject

class JsonGenerator {
    fun ToJsonArray(string: String):JsonArray {
        return Gson().fromJson(string, JsonArray::class.java)
    }
    fun ToJsonObject(string: String):JsonObject {
        return Gson().fromJson(string, JsonObject::class.java)
    }
}