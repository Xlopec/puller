package runner

import api.WeatherProvider
import io.Reader
import io.Writer
import java.io.File


private data class Args(val input: File, val apiKey: String, val outFile: File, val maxRate: Int) {

    private companion object {
        // 60 calls per minute
        private const val MAX_CALLS_PER_MINUTE = 60
    }

    constructor(args: Array<String>) : this(File(args[0]), args[1], File(args[2]),
            args.takeIf { it.size == 4 }?.let { it[3].toInt() } ?: MAX_CALLS_PER_MINUTE)

    init {
        require(input.exists() && input.canRead()) {
            "Specified path '${input.absolutePath}' points to non-existing file or read permission is missing"
        }

        require(apiKey.isNotBlank()) {
            "Blank api key"
        }

        require(maxRate > 0) {
            "max rate per minute <= 0, was $maxRate"
        }
    }
}


fun main(args: Array<String>) {

    require(args.size in 2..4) {
        "Invalid input arguments amount, 0 - input file, 1 - api key, 2 - out file, 3 - max calls per minute (optional)"
    }

    val inputArgs = Args(args)
    val provider = WeatherProvider(inputArgs.apiKey, inputArgs.maxRate)

    provider.fetchWeather(Reader().read(inputArgs.input))
            .doOnSubscribe { println("Starting fetching job") }
            .subscribe(
                    { Writer().write(it, inputArgs.outFile) },
                    { it.printStackTrace() }
            )
}

