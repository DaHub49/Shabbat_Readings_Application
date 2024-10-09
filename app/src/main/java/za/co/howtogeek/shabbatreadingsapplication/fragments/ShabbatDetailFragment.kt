package za.co.howtogeek.shabbatreadingsapplication.fragments

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import za.co.howtogeek.shabbatreadingsapplication.R
import za.co.howtogeek.shabbatreadingsapplication.objects.ShabbatReading
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader


/**
 * ShabbatDetailFragment.kt:
 * Author: Dylan Martin, South Africa
 *
 * ShabbatDetailFragment displays the week's readings of the user's selection
 * The arguments passed through are:
 * 1. "parshaPosition"- the position of the line in the text file
 * 2. and "readingSet"- the int to know which file to open.
 * 3. NB (NOTE TO SELF): FILES ALSO NEED TO BE AMENDED HERE
 * 4. " " JUST REMEMBER TO FIND ALL OCCURRENCES OF OLD TEXT FILES AND THEN CHANGE TO NEW ONES
 */
class ShabbatDetailFragment : Fragment() {

    private val TAG = "fragments -> ShabbatDetailFragment ->"

    private val FILENAME = "ffoz_bamidbar_5784.txt"

    private var fullMySwordReadings: String? = null
    private var fullYouVersionReadings: String? =
        null //, fullEnglishYouVersionReadings, fullXhosaYouVersionReadings;

    //The text required for the intent to open the MySword Bible:
    private val mySwordPretext = "http://mysword.info/b?r="

    //The text required for the intent to open the YouVersion Bible, as well as the Bible code (HCSB) at the end of the text:
    private val youVersionEnglishPreText = "https://bible.com/bible/316/"
    private val youVersionEnglishEndText = ".TS2009"

    private val youVersionEnglishHCSBPreText = "https://bible.com/bible/72/"
    private val youVersionEnglishHCSBEndText = ".HCSB"

    //The text required for the intent to open the YouVersion Bible, as well as the Bible code (XHO75- Xhosa Bible) at the end of the text:
    private val youVersionXhosaPreText = "https://www.bible.com/bible/281/"
    private val youVersionXhosaEndText = ".XHO75"

    //Zulu: https://bible.com/bible/286/deu.1.1.ZUL59
    private val youVersionZuluPreText = "https://www.bible.com/bible/286/"
    private val youVersionZuluEndText = ".ZUL59"

    //https://www.bible.com/bible/6/deu.1.1.AFR83
    private val youVersionAfrikaansPreText = "https://www.bible.com/bible/6/"
    private val youVersionAfrikaansEndText = ".AFR83"

    private val youVersionNorthernSothoPreText = "https://www.bible.com/bible/607/"
    private val youVersionNorthernSothoEndText = ".CSEB24"

    private val youVersionTsongaPreText = "https://www.bible.com/bible/607/"
    private val youVersionTsongaEndText = ".TSO29"

    private val youVersionSouthernNdebelePreText = "https://www.bible.com/bible/450/"
    private val youVersionSouthernNdebeleEndText = ".SND12"

    private var youVersionTranslationPreText = ""
    private var youVersionTranslationEndText = ""
    private var mySwordTorahReadings = ""
    private var mySwordHaftarahReadings = ""
    private var mySwordGospelReadings = ""
    private var youVersionTorahURL: String? = null
    private var youVersionHaftarahURL: String? = null
    private var youVersionNTURL: String? = null
    private var mNewShabbatReading: ShabbatReading? = null
    private var selectedBibleTranslation = 0

    private var parashaLine: String? = null
    private var parashaPosition: Int = 0
    private var parashaName = "[Parasha Name]"

    private val selectedFragment: String? = null

    private var mPreferences: SharedPreferences? = null

    //booleans to check if RadioButtons are checked:
    var mySwordRadioButtonSelected: Boolean = false
    var youVersionRadioButtonSelected: Boolean = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            parashaPosition = arguments?.getInt("parashaPosition")!!
            Log.i(TAG, "ShabbatDetailFragment -> onCreate[step 1] -> parashaPosition: " + parashaPosition)

            parashaName = ""
            importFFOZFile()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView[Step 2]: called")
        return inflater.inflate(R.layout.fragment_shabbat_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated[Step 3]: -> CALLED!")

        /**
         * private var parashaLine: String? = null
         *private var parashaPosition: Int = 0
         *private var parashaName = "[Parasha Name]"
         *
         * "Bamidbar, [0]
         * 8 Jun. 2024, [1]
         * 2 Sivan, [2]
         * Num. 1:1–4:20, [3]
         * HOS. 2:1–22(1:10–2:20), [4]
         * Mt. 4:1–17, [5]
         * Num.1.1–4.20#Hos.2.1–22#Mat.4.1–17, [6]
         * Num_1_1–4_20#Hos_2_1–22#Mat_4_1–17" [7]
         *
         * Error possibly lies in one of these:
         *
         * Beha’alotcha[0],
         * 22 Jun. 2024[1],
         * 16 Sivan[2],
         * Num. 8:1–12:16[3],
         * Zec. 2:14(10)–4:7*[4],
         * Mt. 14:14–21[5],
         * Num.8.1–12.16#Zec.2.14–4.7#Mat.14.14–21[6],
         * Num_8_1–12_16#Zec_2_14–4_7#Mat_14_14–21[7]
         *
         * Shelach[0],
         * 29 Jun. 2024[1],
         * 23 Sivan[2],
         * Num. 13:1–15:41[3],
         * Josh. 2:1–24[4],
         * Mt. 10:1–14[5],
         * Num.13.1–15.41#Jos.2.1–24#Mat.10.1–14[6],
         * Num_13_1–15_41#Jos_2_1–24#Mat_10_1–14[7]
         *
         * Korach,6 Jul. 2024,30 Sivan,Num. 16:1–18:32,Isa. 66:1–24,MK. 9:40–50,Num.16.1–18.32#Isa.66.1–24#Mar.9.40–50,Num_16_1–18_32#Isa_66_1–24#Mat_9_40–50
         *
         * Korach[0],
         * 6 Jul. 2024[1],
         * 30 Sivan[2],
         * Num. 16:1–18:32[3],
         * Isa. 66:1–24[4],
         * MK. 9:40–50[5],
         * Num.16.1–18.32#Isa.66.1–24#Mar.9.40–50[6],
         * Num_16_1–18_32#Isa_66_1–24#Mat_9_40–50[7]
         *
         *  parashaElements[0] | Korach
         *  parashaElements[1] | 6 Jul. 2024
         *  parashaElements[2] | 30 Sivan
         *  parashaElements[3] | Num. 16:1–18:32
         *  parashaElements[4] | Isa. 66:1–24
         *  parashaElements[5] | MK. 9:40–50
         *  parashaElements[6] | Num.16.1–18.32#Isa.66.1–24#Mar.9.40–50
         *  parashaElements[7] | Num_16_1–18_32#Isa_66_1–24#Mat_9_40–50
         *
         * Chukat[0],
         * 13 Jul. 2024[1],
         * 7 Tammuz[2],
         * Num. 19:1–22:1[3],
         * Jdg. 11:1–33[4],
         * Jn. 2:1–12[5],
         * Num.19.1–22.1#Jdg.11.1–33#Joh.2.1–12[6],
         * Num_19_1–22_1#Jdg_11_1–33#Joh_2_1–12[7]
         *
         * Balak[0],
         * 20 Jul. 2024[1],
         * 14 Tammuz[2],
         * Num. 22:2–25:9[3],
         * Mic. 5:6(7)–6:8[4],
         * Mt. 21:1–11[5],
         * Num.22.2–25.9#Mic.5.6–6.8#Mat.21.1–11[6],
         * Num_22_2–25_9#Mic_5_6–6_8#Mat_21_1–11[7]
         * Pinchas,27 Jul. 2024,21 Tammuz,Num. 25:10–30:1(29:40),Jer. 1:1–2:3,Jn. 2:13–22,Num.25.10–30.1#Jer.1.1–2.3#Joh.2.13–22,Num_25_10–30_1#Jer_1_1–2_3#Joh_2_13–22
         */

        val parashaElements = parashaLine!!.split(",")
        parashaName = parashaElements.get(0)

        Log.i(TAG, "onViewCreated: parashaElements.get(0): ${parashaElements.get(0)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(1): ${parashaElements.get(1)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(2): ${parashaElements.get(2)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(3): ${parashaElements.get(3)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(4): ${parashaElements.get(4)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(5): ${parashaElements.get(5)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(6): ${parashaElements.get(6)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(7): ${parashaElements.get(7)}")

        val shabbat_title_text = view.findViewById<TextView>(R.id.shabbat_title_text)
        shabbat_title_text.setText(parashaName)



    }

    private fun loadReading() {



        when (selectedBibleTranslation) {
            -1, 0 -> {
                youVersionTranslationPreText = youVersionEnglishPreText
                youVersionTranslationEndText = youVersionEnglishEndText
            }

            1 -> {
                youVersionTranslationPreText = youVersionXhosaPreText
                youVersionTranslationEndText = youVersionXhosaEndText
            }

            2 -> {
                youVersionTranslationPreText = youVersionAfrikaansPreText
                youVersionTranslationEndText = youVersionAfrikaansEndText
            }

            3 -> {
                youVersionTranslationPreText = youVersionZuluPreText
                youVersionTranslationEndText = youVersionZuluEndText
            }

            4 -> {
                youVersionTranslationPreText = youVersionNorthernSothoPreText
                youVersionTranslationEndText = youVersionNorthernSothoEndText
            }

            5 -> {
                youVersionTranslationPreText = youVersionTsongaPreText
                youVersionTranslationEndText = youVersionTsongaEndText
            }

            6 -> {
                youVersionTranslationPreText = youVersionSouthernNdebelePreText
                youVersionTranslationEndText = youVersionSouthernNdebeleEndText
            }

            7 -> {
                youVersionTranslationPreText = youVersionEnglishHCSBPreText
                youVersionTranslationEndText = youVersionEnglishHCSBEndText
            }

            else -> {
                youVersionTranslationPreText = youVersionEnglishPreText
                youVersionTranslationEndText = youVersionEnglishEndText
            }
        }
    } //loadFromSharedPreferences

    private fun importFFOZFile() {
        //var reader: BufferedReader? = null
        Log.i(TAG, "importFFOZFile: -> CALLED!")

        loadFileContent()

    } //importFFOZFile()

    fun readLinesFromAssets(context: Context?, fileName: String): List<String> {
        return try {
            val assetManager: AssetManager = requireContext().assets
            val inputStream = assetManager.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            return reader.readLines().also { reader.close() } // Read all lines and close the reader
        } catch (e: IOException) {
            Log.e(TAG, "Error reading file from assets", e)
            emptyList() // Return an empty list in case of an error
        }
    }

    // Usage in an Activity or Fragment
    fun loadFileContent() {
        parashaLine = ""
        val lines = readLinesFromAssets(context, FILENAME)
        var indexInt = 0;
        lines.forEach { line ->
            println(line) // Print each line or handle as needed
            if (indexInt == parashaPosition){
                Log.i(TAG, "\nloadFileContent: indexInt ($indexInt) == parashaPosition ($parashaPosition)\n")
                parashaLine = line
            }
            Log.i(TAG, "\nloadFileContent: parashaLine: $parashaLine")
            indexInt++
        }

        Log.i(TAG, "end of method -> loadFileContent -> parashaLine: $parashaLine")
    }

    companion object {
        /*fun newInstance(parashaName: String, parashaPosition: Int): ShabbatDetailFragment {
            val fragment = ShabbatDetailFragment()
            val args = Bundle()
            args.putString("parashaName", parashaName)
            args.putInt("parashaPosition", parashaPosition)
            fragment.arguments = args
            return fragment
        }
         */
    }
}