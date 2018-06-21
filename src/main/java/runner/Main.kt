package runner

import api.WeatherProvider


/**
 * 0 - lat
 * 1 - lon
 * 2 - Api Key
 */
fun main(args: Array<String>) {

    val provider = WeatherProvider( "API_KEY")

    provider.fetchWeather(30.0, 30.0).subscribe(
            { println(it) },
            { it.printStackTrace() }
    )

}

