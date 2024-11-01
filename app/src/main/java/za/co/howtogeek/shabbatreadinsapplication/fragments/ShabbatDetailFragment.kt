package za.co.howtogeek.shabbatreadingsapplication.fragments

import YouVersionTranslationFragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import java.io.IOException


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

/* translationIndex

Example String array:
   <string-array name="youversion_ts2009_translations">
        <item>English TS2009</item>
        <item>https://bible.com/bible/316/</item>
        <item>.TS2009</item>
    </string-array>
 *
 * 0: TS2009 youversion_ts2009_translations
 * 1: English NASB youversion_nasb_translations
 * 2: English HCSB youversion_hcsb_translations
 * 3: Xhosa youversion_xhosa_translations
 * 4: Zulu youversion_zulu_translations
 * 5: Afrikaans youversion_afrikaans_translations
 * 6: Northern Sotho youversion_northern_sotho_translations
 * 7: Tsonga youversion_tsonga_translations
 * 8: Southern Ndebele youversion_southern_ndebele_translations
 */

/**
 * Links:
 * 1. https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android
 * 2. https://bible.com/bible/100/rom.12.1.NASB1995
 * 3. https://bible.com/bible/3854/gen.1_1.1.CSEB24
 */
private val TAG = "fragments -> ShabbatDetailFragment ->"
private val FILENAME = "ffoz_berasheet_5785.txt"
private val PREFERENCES = "preferences"
private val PARASHA_POSITION = "parashaPosition"
private val TRANSLATIONINDEX = "translationIndex"

class ShabbatDetailFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    private var fullMySwordReadings: String = "null"
    private var fullYouVersionReadings: String? = null

    //The text required for the intent to open the MySword Bible:
    private val mySwordPretext = "http://mysword.info/b?r="

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
    private var translationIndex: Int = 0
    private var parashaName = "[Parasha Name]"

    //booleans to check if RadioButtons are checked:
    var mySwordRadioButtonSelected: Boolean = false
    var youVersionRadioButtonSelected: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parashaName = ""
        //val context = requireContext()

        //Log.i(TAG, "onCreate [before call to loadFromSharedPreferences]: parashaPosition[initially assigned 0]: $parashaPosition and translationIndex: $translationIndex")
        loadFromSharedPreferences()
        //Log.i(TAG, "onCreate [after call to loadFromSharedPreferences]: parashaPosition: $parashaPosition and translationIndex: $translationIndex")

        //Log.i(TAG, "onCreate: initiating loadTranslation()")
        loadTranslation()
        //Log.i(TAG, "onCreate: loaded translation: $translationIndex")
    }

    private fun importReading(filename: String, index: Int): String? {
        var lineAtIndex: String? = null
        try {
            requireContext().assets.open(filename).bufferedReader().useLines { lines ->

                for (i in 0..index) {
                    if (i == index) {
                        lineAtIndex = lines.elementAt(i)
                        //Log.i(TAG, "importReading -> lineAtIndex: $lineAtIndex")
                        break
                    }
                }
            }
        } catch (e: IOException) {
            //Log.e("TAG", "Error reading file: ${e.message}", e)
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

        //Log.i(TAG, "onViewCreated: parashaLine: $parashaLine")
        if (parashaLine==null){
            parashaLine = importReading(FILENAME, parashaPosition)
        }
        //Log.i(TAG, "onViewCreated: parashaLine: $parashaLine")

        val parashaElements = parashaLine!!.split(",")
        parashaName = parashaElements.get(0)
        mNewShabbatReading = ShabbatReading(
            id = parashaPosition,
            readingSet = 1,
            parashaName = parashaElements.get(0),
            gregorianDate = parashaElements.get(1),
            hebrewDate = parashaElements.get(2),
            torahPortion = parashaElements.get(3),
            haftarahPortion = parashaElements.get(4),
            gospelPortion = parashaElements.get(5),
            youVersion = parashaElements.get(6),
            mySword = parashaElements.get(7)
        )

        /*Log.i(TAG, "onViewCreated: parashaElements.get(0): ${parashaElements.get(0)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(1): ${parashaElements.get(1)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(2): ${parashaElements.get(2)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(3): ${parashaElements.get(3)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(4): ${parashaElements.get(4)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(5): ${parashaElements.get(5)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(6): ${parashaElements.get(6)}")
        Log.i(TAG, "onViewCreated: parashaElements.get(7): ${parashaElements.get(7)}")
         */

        val shabbat_title_text = view.findViewById<TextView>(R.id.shabbat_title_text)
        shabbat_title_text.setText(mNewShabbatReading!!.parashaName)

        val shabbat_reading_set = view.findViewById<TextView>(R.id.shabbat_reading_set)
        val readingSet = mNewShabbatReading!!.readingSet
        shabbat_reading_set.setText(R.string.first_fruits_of_zion)

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
            /**
             * var youVersionTranslationFragment: YouVersionTranslationFragment = YouVersionTranslationFragment()
             *var bundle = Bundle()
             *bundle.putInt("callerFragment", 1) // 0 for ShabbatDetailFragment, 1 for AdditionalReaddingsDetailFragment
             *youVersionTranslationFragment.setArguments(bundle) // pass the bundle to the fragmentarguments
             */

            var youVersionTranslationFragment: YouVersionTranslationFragment = YouVersionTranslationFragment()
            var bundle = Bundle()
            bundle.putInt("callerFragment", 0) // 0 for ShabbatDetailFragment, 1 for AdditionalReaddingsDetailFragment
            youVersionTranslationFragment.setArguments(bundle) // pass the bundle to the fragmentarguments
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, youVersionTranslationFragment)
                .commit()
        }

        //RadioGroups and RadioButtons:
        val mySwordRadioButton = view.findViewById<TextView>(R.id.mySwordRadioButton)
        val youVersionRadioButton = view.findViewById<TextView>(R.id.youVersionRadioButton)
        mySwordRadioButton.isEnabled = true
        youVersionRadioButton.isEnabled = true

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
                        youVersionTranslationPreText + youVersionTorahURL + youVersionTranslationEndText
                    //Log.i(TAG, "onViewCreated -> torahTextView -> tempYouVersionIntent: $tempYouVersionIntent")
                    launchIntent.setData(Uri.parse(tempYouVersionIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    //Log.i(TAG, "onViewCreated: [ELSE]")
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android"))
                    startActivity(intent)
                }
            } else if (mySwordRadioButtonSelected) {
                try {
                    //Log.i(TAG, "onViewCreated: mySword clicked!")
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
                    val tempYouVersionIntent = youVersionTranslationPreText + youVersionHaftarahURL + youVersionTranslationEndText
                    //Log.i(TAG, "onViewCreated -> haftarahTextView -> tempYouVersionIntent: $tempYouVersionIntent")
                    launchIntent.setData(Uri.parse(tempYouVersionIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    //Log.i(TAG, "onViewCreated: [ELSE]")
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android"))
                    startActivity(intent)
                }
            } else if (mySwordRadioButtonSelected) {
                try {
                    //Log.i(TAG, "onViewCreated: mySword clicked!")
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
                        youVersionTranslationPreText + youVersionNTURL + youVersionTranslationEndText
                    //Log.i(TAG, "onViewCreated -> britChadashahTextView -> tempYouVersionIntent: $tempYouVersionIntent")
                    launchIntent.setData(Uri.parse(tempYouVersionIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    //Log.i(TAG, "onViewCreated: [ELSE]")
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.sirma.mobile.bible.android"))
                    startActivity(intent)
                }
            } else if (mySwordRadioButtonSelected) {
                try {
                    //Log.i(TAG, "onViewCreated: mySword clicked!")
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
        //Log.i(TAG, "assignYouVersionReadings: [called]")
        var placeholderString: String = ""
        var replacementString: String = ""

        val youVersionElements =
            mNewShabbatReading?.youVersion?.split("#")
        youVersionTorahURL = youVersionElements?.get(0) ?: "null"
        //Log.i(TAG, "assignYouVersionReadings -> youVersionTorahURL: $youVersionTorahURL")
        youVersionHaftarahURL = youVersionElements?.get(1) ?: "null"
        //Log.i(TAG, "assignYouVersionReadings -> youVersionHaftarahURL: $youVersionHaftarahURL")

        /**
         * 3 Strings:  1: originalString [youVersionElements?.get(2)],
         *             2: substringToReplace ["joh"],
         *             3: replacementString ["jhn"]
         *
         * val modifiedString = if (originalString.contains(substringToReplace)) {
         *     originalString.replace(substringToReplace, replacementString)
         * } else {
         *     originalString
         * }
         *
         * println(modifiedString) // Output: This is a test string with a new text to replace.
         */

        placeholderString = youVersionElements?.get(2) ?: "null"
        replacementString = if (placeholderString.contains("Joh")){
            Log.i(TAG, "assignYouVersionReadings: CONTAINS 'Joh'!")
            placeholderString.replace("Joh", "Jhn")
        } else
            placeholderString

        youVersionNTURL = replacementString
        Log.i(TAG, "assignYouVersionReadings -> youVersionNTURL: $youVersionNTURL")
    }

    fun assignMySwordReadings() {
        //fullMySwordReadings
        ////Log.i(TAG, "assignMySwordReadings -> [error here]");
        //Log.i(TAG, "assignMySwordReadings: mNewShabbatReading?.mySword?: $fullMySwordReadings")
        val mySwordElements =
            mNewShabbatReading?.mySword?.split("#")
        mySwordTorahReadings = mySwordElements?.get(0) ?: "null"
        //Log.i(TAG, "assignMySwordReadings -> mySwordTorahReadings: $mySwordTorahReadings")
        mySwordHaftarahReadings = mySwordElements?.get(1) ?: "null"
        //Log.i(TAG, "assignMySwordReadings -> mySwordHaftarahReadings: $mySwordHaftarahReadings")
        mySwordGospelReadings = mySwordElements?.get(2) ?: "null"
        //Log.i(TAG, "assignMySwordReadings -> mySwordGospelReadings: $mySwordGospelReadings")
    }

    fun loadFromSharedPreferences() {
        //Log.i(TAG, "loadFromSharedPreferences: [called]")
        sharedPreferences = requireActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

        parashaPosition = sharedPreferences.getInt(PARASHA_POSITION, 0)
        //Log.i(TAG, "loadFromSharedPreferences -> parashaPosition: $parashaPosition")

        //Log.i(TAG, "loadFromSharedPreferences -> selectedBibleTranslation: $selectedBibleTranslation")
        translationIndex = sharedPreferences.getInt(TRANSLATIONINDEX, 0)
        selectedBibleTranslation = translationIndex
        //Log.i(TAG, "loadFromSharedPreferences -> translationIndex: $translationIndex")
        //Log.i(TAG, "loadFromSharedPreferences -> selectedBibleTranslation: $selectedBibleTranslation ")


    }

    fun loadTranslation() {
        //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation")
        when (selectedBibleTranslation) {
            -1, 0 -> {
                //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation [when -1, 0]")
                val youVersionTS2009Array = resources.getStringArray(R.array.youversion_ts2009_translations)
                youVersionTranslationPreText = youVersionTS2009Array[1]
                youVersionTranslationEndText = youVersionTS2009Array[2]
            }

            1 -> {
                //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation [when 1]")
                val youVersionNASBArray = resources.getStringArray(R.array.youversion_nasb_translations)
                youVersionTranslationPreText = youVersionNASBArray[1]
                youVersionTranslationEndText = youVersionNASBArray[2]
            }

            2 -> {
                //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation [when 2]")
                val youVersionHCSBArray = resources.getStringArray(R.array.youversion_hcsb_translations)
                youVersionTranslationPreText = youVersionHCSBArray[1]
                youVersionTranslationEndText = youVersionHCSBArray[2]
            }

            3 -> {
                //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation [when 3]")
                val youVersionXhosaArray = resources.getStringArray(R.array.youversion_xhosa_translations)
                youVersionTranslationPreText = youVersionXhosaArray[1]
                youVersionTranslationEndText = youVersionXhosaArray[2]
            }

            4 -> {
                //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation [when 4]")
                val youVersionZuluArray = resources.getStringArray(R.array.youversion_zulu_translations)
                youVersionTranslationPreText = youVersionZuluArray[1]
                youVersionTranslationEndText = youVersionZuluArray[2]
            }

            5 -> {
                //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation [when 5]")
                val youVersionAfrikaansArray = resources.getStringArray(R.array.youversion_afrikaans_translations)
                youVersionTranslationPreText = youVersionAfrikaansArray[1]
                youVersionTranslationEndText = youVersionAfrikaansArray[2]
            }

            6 -> {
                //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation [when 6]")
                val youVersionNorthernSothoArray = resources.getStringArray(R.array.youversion_northern_sotho_translations)
                youVersionTranslationPreText = youVersionNorthernSothoArray[1]
                youVersionTranslationEndText = youVersionNorthernSothoArray[2]
            }

            7 -> {
                //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation [when 7]")
                val youVersionTsongaArray = resources.getStringArray(R.array.youversion_tsonga_translations)
                youVersionTranslationPreText = youVersionTsongaArray[1]
                youVersionTranslationEndText = youVersionTsongaArray[2]
            }

            8 -> {
                //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation [when 8]")
                val youVersionSouthernNdebeleArray = resources.getStringArray(R.array.youversion_southern_ndebele_translations)
                youVersionTranslationPreText = youVersionSouthernNdebeleArray[1]
                youVersionTranslationEndText = youVersionSouthernNdebeleArray[2]
            }

            else -> {
                //Log.i(TAG, "loadTranslation: selectedBibleTranslation: $selectedBibleTranslation [else]")
                val youVersionTS2009Array = resources.getStringArray(R.array.youversion_ts2009_translations)
                youVersionTranslationPreText = youVersionTS2009Array[1]
                youVersionTranslationEndText = youVersionTS2009Array[2]
            }
        }
    } //loadFromSharedPreferences

}