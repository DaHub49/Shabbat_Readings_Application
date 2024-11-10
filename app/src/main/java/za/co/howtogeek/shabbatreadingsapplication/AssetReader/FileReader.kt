package za.co.howtogeek.shabbatreadingsapplication.AssetReader

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class FileReader {
    companion object {
        fun readFile(inputStream: InputStream): ArrayList<String> {
            val parashaNames = ArrayList<String>()
            try {
                val reader = BufferedReader(InputStreamReader(inputStream))
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    parashaNames.add(line!!)
                }
                reader.close()
            } catch (e: Exception) {
                //Log.e("FileReader", "Error reading file: ${e.message}", e)
            }
            return parashaNames
        }
    }
}