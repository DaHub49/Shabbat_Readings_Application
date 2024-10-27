package za.co.howtogeek.shabbatreadingsapplication.fragments

import YouVersionTranslationFragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import za.co.howtogeek.shabbatreadingsapplication.objects.ShabbatReading
import za.co.howtogeek.shabbatreadinsapplication.R
import java.io.BufferedReader
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

/**
 * Links:
 * 1. https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android
 * 2. https://bible.com/bible/100/rom.12.1.NASB1995
 * 3. https://bible.com/bible/3854/gen.1_1.1.CSEB24
 */
private val TAG = "fragments -> ShabbatDetailFragment ->"
private val FILENAME = "ffoz_berasheet_5785.txt"

class ShabbatDetailFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    //private lateinit var editor: SharedPreferences.Editor

    private var fullMySwordReadings: String = "null"
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

    //private var mPreferences: SharedPreferences? = null

    //booleans to check if RadioButtons are checked:
    var mySwordRadioButtonSelected: Boolean = false
    var youVersionRadioButtonSelected: Boolean = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parashaName = ""
        val context = requireContext()

        //importFFOZFile()
        //arguments?.let {
        //}
        if(arguments==null){ //if(!arguments) ??
            sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val loadedValue = sharedPreferences.getInt("parashaPosition", 0)
            Log.i(TAG, "onCreate: loadedValue: $loadedValue")
            parashaPosition = loadedValue
        } else {
            parashaPosition = arguments?.getInt("parashaPosition")!!
            Log.i(TAG, "onCreate -> parashaPosition after assignment from Bundle: " + parashaPosition)

            parashaName = ""
            parashaLine = importReading(FILENAME, parashaPosition)
            Log.i(TAG, "onCreate: parashaLine: $parashaLine")

            /*val context = requireContext() // Inside a Fragment
            val line = readLineAtIndex(context, FILENAME, parashaPosition) // Read line at index 3

            if (line != null) {
                Log.i(TAG, "onCreate: line == null")
            } else {
                Log.i(TAG, "onCreate: line not found")
            }

             */
        }


    }

    fun readLineAtIndex(context: Context, filename: String, index: Int): String? {
        var lineAtIndex: String? = null
        try {
            context.assets.open(filename).bufferedReader().useLines { lines ->
                for ((currentIndex, line) in lines.withIndex()) {
                    if (currentIndex == index) {
                        lineAtIndex = line
                        Log.i(TAG, "readLineAtIndex: lineAtIndex: $lineAtIndex")
                        break
                    }
                }
            }
        } catch (e: IOException) {
            Log.e("TAG", "Error reading file: ${e.message}", e)
        }
        return lineAtIndex
    }

    private fun importReading(filename: String, index: Int): String? {
        var lineAtIndex: String? = null
        try {
            requireContext().assets.open(filename).bufferedReader().useLines { lines ->

                /**
                 * for (i in 1..3) {
                 *     println(i)
                 * }
                 *
                 * private var parashaLine: String? = null
                 *     private var parashaPosition: Int = 0
                 *     private var parashaName = "[Parasha Name]"
                 */

                /*for (i in lines) {
                    if (i == parashaPosition) {
                        println("Line at parashaPosition: $i")
                        break // Exit the loop once the line is found
                    }
                }
                 */

                for (i in 0..index) {
                    if (i == index) {
                        lineAtIndex = lines.elementAt(i)
                        Log.i(TAG, "importReading -> lineAtIndex: $lineAtIndex")
                        break
                    }
                }
            }
        } catch (e: IOException) {
            Log.e("TAG", "Error reading file: ${e.message}", e)
        }
        return lineAtIndex
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shabbat_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the position (or other data) passed from ReadingList_Fragment
        //val position = arguments?.let { ShabbatDetailFragmentArgs.fromBundle(it).position }

        //    private var mySwordTorahReadings = ""
        //    private var mySwordHaftarahReadings = ""
        //     private var mySwordGospelReadings = ""
        //    private var youVersionTorahURL: String? = null
        //    private var youVersionHaftarahURL: String? = null
        //     private var youVersionNTURL: String? = null
        Log.i(TAG, "onViewCreated: parashaLine: $parashaLine")
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

        //Assign readings:
        assignMySwordReadings()

        assignYouVersionReadings()

        // YouVersion Bible selection button
        val bibleTranslationPreferencesTextView = view.findViewById<TextView>(R.id.bibleTranslationPreferencesTextView)
        bibleTranslationPreferencesTextView.setOnClickListener {
            val youVersionTranslationFragment: YouVersionTranslationFragment = YouVersionTranslationFragment()
            /*val bundle = Bundle()
            bundle.putInt("parashaPosition", position)
             */
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, youVersionTranslationFragment)
                .addToBackStack(null).commit()
        }

        //RadioGroups and RadioButtons:
        val mySwordRadioButton = view.findViewById<TextView>(R.id.mySwordRadioButton)
        val youVersionRadioButton = view.findViewById<TextView>(R.id.youVersionRadioButton)
        mySwordRadioButton.isEnabled = true

        val bibleSelectionRadioGroup = view.findViewById<RadioGroup>(R.id.bibleSelectionRadioGroup)
        bibleSelectionRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.mySwordRadioButton -> {
                    mySwordRadioButtonSelected = true
                    youVersionRadioButtonSelected = false
                }

                R.id.youVersionRadioButton -> {
                    mySwordRadioButtonSelected = false
                    youVersionRadioButtonSelected = true
                }
            }
        }

        val torahTextView = view.findViewById<TextView>(R.id.torahTextView)
        //Receives the member variables and disables the buttons if the value is 'null'
        val torahPortion: String = mNewShabbatReading!!.torahPortion.toString()
        if (torahPortion.equals("null", ignoreCase = true)) {
            torahTextView.isEnabled = false
        } else torahTextView.text = torahPortion

        val haftarahTextView = view.findViewById<TextView>(R.id.haftarahTextView)
        haftarahTextView.setText(mNewShabbatReading!!.haftarahPortion)

        val britChadashahTextView = view.findViewById<TextView>(R.id.britChadashahTextView)
        britChadashahTextView.setText(mNewShabbatReading!!.gospelPortion)

        torahTextView.isClickable = true
        torahTextView.setOnClickListener {
            if (youVersionRadioButtonSelected) {
                try {
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempYouVersionIntent =
                        youVersionEnglishPreText + youVersionTorahURL + youVersionEnglishEndText
                    Log.i(
                        TAG,
                        "onViewCreated -> torahTextView -> tempYouVersionIntent: $tempYouVersionIntent"
                    )
                    launchIntent.setData(Uri.parse(tempYouVersionIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    //https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android
                    //intent.setData(Uri.parse("market://details?id=com.sirma.mobile.bible.android"))
                    Log.i(TAG, "onViewCreated: [ELSE]")
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android"))
                    startActivity(intent)
                }
            } else if (mySwordRadioButtonSelected) {
                try {
                    Log.i(TAG, "onViewCreated: mySword clicked!")
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempMySwordIntent = mySwordPretext + mySwordTorahReadings
                    launchIntent.setData(Uri.parse(tempMySwordIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("market://details?id=com.riversoft.android.mysword"))
                    startActivity(intent)
                }
            }
        } //torahTextView onClickListener

        haftarahTextView.isClickable = true
        haftarahTextView.setOnClickListener {
            if (youVersionRadioButtonSelected) {
                try {
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempYouVersionIntent =
                        youVersionEnglishPreText + youVersionHaftarahURL + youVersionEnglishEndText
                    Log.i(
                        TAG,
                        "onViewCreated -> haftarahTextView -> tempYouVersionIntent: $tempYouVersionIntent"
                    )
                    launchIntent.setData(Uri.parse(tempYouVersionIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    //https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android
                    //intent.setData(Uri.parse("market://details?id=com.sirma.mobile.bible.android"))
                    Log.i(TAG, "onViewCreated: [ELSE]")
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android"))
                    startActivity(intent)
                }
            } else if (mySwordRadioButtonSelected) {
                try {
                    Log.i(TAG, "onViewCreated: mySword clicked!")
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempMySwordIntent = mySwordPretext + mySwordHaftarahReadings
                    launchIntent.setData(Uri.parse(tempMySwordIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("market://details?id=com.riversoft.android.mysword"))
                    startActivity(intent)
                }
            }
        } //haftarahTextView onClickListener

        britChadashahTextView.isClickable = true
        britChadashahTextView.setOnClickListener {
            if (youVersionRadioButtonSelected) {
                try {
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempYouVersionIntent =
                        youVersionEnglishPreText + youVersionNTURL + youVersionEnglishEndText
                    Log.i(
                        TAG,
                        "onViewCreated -> britChadashahTextView -> tempYouVersionIntent: $tempYouVersionIntent"
                    )
                    launchIntent.setData(Uri.parse(tempYouVersionIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    //https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android
                    //intent.setData(Uri.parse("market://details?id=com.sirma.mobile.bible.android"))
                    Log.i(TAG, "onViewCreated: [ELSE]")
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android"))
                    startActivity(intent)
                }
            } else if (mySwordRadioButtonSelected) {
                try {
                    Log.i(TAG, "onViewCreated: mySword clicked!")
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempMySwordIntent = mySwordPretext + mySwordGospelReadings
                    launchIntent.setData(Uri.parse(tempMySwordIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("market://details?id=com.riversoft.android.mysword"))
                    startActivity(intent)
                }
            }
        } //gospelTextView onClickListener

    } //onViewCreated

    fun assignYouVersionReadings() {
        Log.i(TAG, "assignYouVersionReadings: [called]")
        val youVersionElements =
            mNewShabbatReading?.youVersion?.split("#")
        youVersionTorahURL = youVersionElements?.get(0) ?: "null"
        Log.i(TAG, "assignYouVersionReadings -> youVersionTorahURL: $youVersionTorahURL")
        youVersionHaftarahURL = youVersionElements?.get(1) ?: "null"
        Log.i(TAG, "assignMySwordReadings -> mySwordHaftarahReadings: $youVersionHaftarahURL")
        youVersionNTURL = youVersionElements?.get(2) ?: "null"
        Log.i(TAG, "assignMySwordReadings -> mySwordGospelReadings: $youVersionNTURL")
    }

    fun assignMySwordReadings() {
        //fullMySwordReadings
        //Log.i(TAG, "assignMySwordReadings -> [error here]");
        Log.i(TAG, "assignMySwordReadings: mNewShabbatReading?.mySword?: $fullMySwordReadings")
        val mySwordElements =
            mNewShabbatReading?.mySword?.split("#")
        mySwordTorahReadings = mySwordElements?.get(0) ?: "null"
        Log.i(TAG, "assignMySwordReadings -> mySwordTorahReadings: $mySwordTorahReadings")
        mySwordHaftarahReadings = mySwordElements?.get(1) ?: "null"
        Log.i(TAG, "assignMySwordReadings -> mySwordHaftarahReadings: $mySwordHaftarahReadings")
        mySwordGospelReadings = mySwordElements?.get(2) ?: "null"
        Log.i(TAG, "assignMySwordReadings -> mySwordGospelReadings: $mySwordGospelReadings")
    }

    fun loadReading() {
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

    fun readLinesFromAssets(context: Context?, fileName: String): List<String> {
        return try {
            val assetManager: AssetManager = requireContext().assets
            val inputStream = assetManager.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            return reader.readLines()
                .also { reader.close() } // Read all lines and close the reader
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
            if (indexInt == parashaPosition) {
                Log.i(
                    TAG,
                    "\nloadFileContent: indexInt ($indexInt) == parashaPosition ($parashaPosition)\n"
                )
                parashaLine = line
            }
            Log.i(TAG, "\nloadFileContent: parashaLine: $parashaLine")
            indexInt++
        }

        Log.i(TAG, "end of method -> loadFileContent -> parashaLine: $parashaLine")
    }

    companion object {
        //20/10:
        var selectedIndex: Int? = null

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