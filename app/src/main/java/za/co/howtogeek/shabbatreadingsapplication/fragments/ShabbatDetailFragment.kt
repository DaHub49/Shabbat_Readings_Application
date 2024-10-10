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
         *
         *     private var mySwordTorahReadings = ""
         *     private var mySwordHaftarahReadings = ""
         *     private var mySwordGospelReadings = ""
         *     private var youVersionTorahURL: String? = null
         *     private var youVersionHaftarahURL: String? = null
         *     private var youVersionNTURL: String? = null
         */
        val parashaElements = parashaLine!!.split(",")
        parashaName = parashaElements.get(0)
        mNewShabbatReading = ShabbatReading(
            id = parashaPosition,
            readingSet = 0,
            parashaName = parashaElements.get(0),
            gregorianDate = parashaElements.get(1),
            hebrewDate = parashaElements.get(2),
            torahPortion = parashaElements.get(3),
            haftarahPortion = parashaElements.get(4),
            gospelPortion = parashaElements.get(5),
            youVersion = parashaElements.get(6),
            mySword = parashaElements.get(7)
        )

        Log.i(TAG, "onViewCreated: parashaElements.get(0): ${parashaElements.get(0)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(1): ${parashaElements.get(1)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(2): ${parashaElements.get(2)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(3): ${parashaElements.get(3)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(4): ${parashaElements.get(4)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(5): ${parashaElements.get(5)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(6): ${parashaElements.get(6)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(7): ${parashaElements.get(7)}")

        val shabbat_title_text = view.findViewById<TextView>(R.id.shabbat_title_text)
        shabbat_title_text.setText(mNewShabbatReading!!.parashaName)

        val gregorianDateTextView = view.findViewById<TextView>(R.id.gregorianDateTextView)
        gregorianDateTextView.setText(mNewShabbatReading!!.gregorianDate)

        val biblicalDateTextView = view.findViewById<TextView>(R.id.biblicalDateTextView)
        biblicalDateTextView.setText(mNewShabbatReading!!.hebrewDate)

        val torahTextView = view.findViewById<TextView>(R.id.torahTextView)
        torahTextView.setText(mNewShabbatReading!!.torahPortion)

        val haftarahTextView = view.findViewById<TextView>(R.id.haftarahTextView)
        haftarahTextView.setText(mNewShabbatReading!!.haftarahPortion)

        val britChadashahTextView = view.findViewById<TextView>(R.id.britChadashahTextView)
        britChadashahTextView.setText(mNewShabbatReading!!.gospelPortion)






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