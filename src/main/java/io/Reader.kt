package io

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.Location
import java.io.File


class Reader(private val gson: Gson = Gson()) {

    private companion object {
        private val TYPE = object : TypeToken<List<Location>>() {}.type
    }

    fun read(input: File): List<Location> {
        require(input.exists() && input.canRead()) {
            "Specified path '${input.absolutePath}' points to non-existing file or read permission is missing"
        }

        return gson.fromJson<List<Location>>(input.bufferedReader(), TYPE)
    }

}