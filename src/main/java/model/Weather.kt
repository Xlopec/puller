package model

import com.google.gson.annotations.SerializedName

data class Wind(@SerializedName("speed") val speed: Double,
                @SerializedName("deg") val degrees: Double)

data class Location(val lat: Double, val lon: Double)

data class Weather(@SerializedName("coord") val location: Location,
                   @SerializedName("wind") val wind: Wind)