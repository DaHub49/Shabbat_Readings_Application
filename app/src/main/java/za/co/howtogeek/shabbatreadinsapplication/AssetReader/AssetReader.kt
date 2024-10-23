package za.co.howtogeek.shabbatreadingsapplication.AssetReader

import android.content.Context
import java.io.IOException

class AssetReader(private val context: Context) {

    fun readTextFileFromAssets(fileName: String): String {
        return try {
            // Open the file from assets directory
            val inputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            // Convert the bytes to a string using UTF-8 encoding
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            "" // Return empty string on error
        }
    }

    fun readTextFileLineByLine(fileName: String): List<String> {
        val lines = mutableListOf<String>()
        try {
            // Open the file from assets directory
            val inputStream = context.assets.open(fileName)
            val reader = inputStream.bufferedReader()

            // Read each line using readLine() until null
            reader.useLines { lines.addAll(it) }

            // Close the reader and input stream
            reader.close()
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return lines
    }
}