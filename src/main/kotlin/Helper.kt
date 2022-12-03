import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

fun processInputLineByLine(path: String, handler: (String)->Any) {
    val file = object {}.javaClass.classLoader.getResource(path)?.file
    try {
        BufferedReader(FileReader(file!!)).use { br ->
            br.lines().forEach { handler(it) }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun readInput(name: String) = File("src/inputs", "$name.txt")
    .readLines()