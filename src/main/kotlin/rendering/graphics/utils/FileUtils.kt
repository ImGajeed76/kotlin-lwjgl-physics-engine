package rendering.graphics.utils

import RESOURCE_FOLDER_PATH
import java.io.File
import java.io.IOException

class FileUtils {
    fun loadAsString(path: String): String {
        val newPath = RESOURCE_FOLDER_PATH + path
        val result = StringBuilder()

        try {
            val file = File(newPath)
            val lines = file.readLines()
            for (line in lines) {
                result.append(line + "\n")
            }
        } catch (e: IOException) {
            System.err.println("Error: Could not find the file at $newPath \n $e")
        }

        return result.toString()
    }
}