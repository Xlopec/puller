package io

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import model.Weather
import java.io.File

class Writer(private val gson: Gson = GsonBuilder().setPrettyPrinting().create()) {

    fun write(data: List<Weather>, destination: File) {
        if (destination.exists()) {
            require(destination.canWrite() && destination.isFile) {
                "Write permission required for ${destination.absolutePath} or specified path is directory"
            }
        }

        println("Saving data into file ${destination.absolutePath}")

        destination.writeText(gson.toJson(data))

        println("Data was saved successfully")
    }

}