package rendering.graphics.utils

import java.io.File
import java.io.IOException

class FileUtils {
    fun loadAsString(path: String): String {
        val result = StringBuilder()

        try {
            val file = File(path)
            val lines = file.readLines()
            for (line in lines) {
                result.append(line + "\n")
            }
        } catch (e: IOException) {
            System.err.println("Error: Could not find the file at $path \n $e")
        }

        return result.toString()
    }
}