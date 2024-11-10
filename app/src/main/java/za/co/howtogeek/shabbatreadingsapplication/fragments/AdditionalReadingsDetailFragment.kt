package za.co.howtogeek.shabbatreadingsapplication.fragments

import YouVersionTranslationFragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import za.co.howtogeek.shabbatreadingsapplication.R
import java.io.IOException

private val TAG = "fragments -> AdditionalReadingsDetailFragment -> "
private val FILENAME = "berasheet_additional_parashat_readings_with_links_commas.txt"
private val PREFERENCES = "preferences"
private val PARASHA_POSITION = "parashaPosition"
private val TRANSLATIONINDEX = "translationIndex"

class AdditionalReadingsDetailFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    //The text required for the intent to open the MySword Bible:
    private val mySwordPretext = "http://mysword.info/b?r="

    private var youVersionTranslationPreText = ""
    private var youVersionTranslationEndText = ""
    private var additionalReadings1 = ""
    private var additionalReadings2 = ""
    private var additionalReadings3 = ""
    private var additionalReadings4 = ""
    private var mySwordAdditionalReadings1 = ""
    private var mySwordAdditionalReadings2 = ""
    private var mySwordAdditionalReadings3 = ""
    private var mySwordAdditionalReadings4 = ""
    private var youVersionAdditionalReadings1: String? = null
    private var youVersionAdditionalReadings2: String? = null
    private var youVersionAdditionalReadings3: String? = null
    private var youVersionAdditionalReadings4: String? = null

    //the full Bible Strings:
    private var allYouVersionLinks: String? = null
    private var allMySwordLinks: String? = null

    //private var mNewShabbatReading: ShabbatReading? = null
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
        loadFromSharedPreferences()
        loadTranslation()

        //Log.i(TAG, "onCreate: parashaLine [before importReading()]: $parashaLine")
        if (parashaLine == null) {
            parashaLine = importReading(FILENAME, parashaPosition)
        }
        //Log.i(TAG, "onCreate: parashaLine [after importReading()]: $parashaLine")

        //Bereishit,Joshua,Psalms 1-8,Matthew 1-4,Romans 1-3,Jos.1.1#Psa.1.1#Mat.1.1#Rom.1.1,Jos_1_1#Psa_1_1#Mat_1_1#Rom_1_1

        val parashaElements = parashaLine!!.split(",")
        parashaName = parashaElements.get(0)
        //Log.i(TAG, "onCreate: parashaName: $parashaName")
        additionalReadings1 = parashaElements.get(1)
        //Log.i(TAG, "onCreate: additionalReadings1: $additionalReadings1")
        additionalReadings2 = parashaElements.get(2)
        //Log.i(TAG, "onCreate: additionalReadings2: $additionalReadings2")
        additionalReadings3 = parashaElements.get(3)
        //Log.i(TAG, "onCreate: additionalReadings3: $additionalReadings3")
        additionalReadings4 = parashaElements.get(4)
        //Log.i(TAG, "onCreate: additionalReadings4: $additionalReadings4")
        allYouVersionLinks = parashaElements.get(5)
        //Log.i(TAG, "onCreate: allYouVersionLinks: $allYouVersionLinks")
        allMySwordLinks = parashaElements.get(6)
        //Log.i(TAG, "onCreate: allMySwordLinks: $allMySwordLinks")

        //Assign readings:
        assignMySwordReadings()

        assignYouVersionReadings()
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_additional_readings_detail, container, false)
    }

    fun assignYouVersionReadings() {
        //Log.i(TAG, "assignYouVersionReadings: [called]")
          
                /* mySwordAdditionalReadings1 = ""
                 mySwordAdditionalReadings2 = ""
                 mySwordAdditionalReadings3 = ""
                 mySwordAdditionalReadings4 = ""
                 youVersionAdditionalReadings1 = ""
                 youVersionAdditionalReadings2 = ""
                 youVersionAdditionalReadings3 = ""
                 youVersionAdditionalReadings4 = ""

                 */

        val youVersionElements = allYouVersionLinks?.split("#")
        //Log.i(TAG, "assignYouVersionReadings: allYouVersionLinks: $allYouVersionLinks")
        youVersionAdditionalReadings1 = youVersionElements?.get(0) ?: "null"
        //Log.i(TAG, "assignYouVersionReadings: youVersionAdditionalReadings1: $youVersionAdditionalReadings1")
        youVersionAdditionalReadings2 = youVersionElements?.get(1) ?: "null"
        //Log.i(TAG, "assignYouVersionReadings: youVersionAdditionalReadings2: $youVersionAdditionalReadings2")
        youVersionAdditionalReadings3 = youVersionElements?.get(2) ?: "null"
        //Log.i(TAG, "assignYouVersionReadings: youVersionAdditionalReadings3: $youVersionAdditionalReadings3")
        youVersionAdditionalReadings4 = youVersionElements?.get(3) ?: "null"
        //Log.i(TAG, "assignYouVersionReadings: youVersionAdditionalReadings4: $youVersionAdditionalReadings4")
    }

    fun assignMySwordReadings() {
        //Log.i(TAG, "assignMySwordReadings: [called]")
        //Log.i(TAG, "assignMySwordReadings: allMySwordLinks: $allMySwordLinks")
        val mySwordElements = allMySwordLinks?.split("#")
        mySwordAdditionalReadings1 = mySwordElements?.get(0) ?: "null"
        //Log.i(TAG, "assignMySwordReadings: mySwordAdditionalReadings1: $mySwordAdditionalReadings1")
        mySwordAdditionalReadings2 = mySwordElements?.get(1) ?: "null"
        //Log.i(TAG, "assignMySwordReadings: mySwordAdditionalReadings2: $mySwordAdditionalReadings2")
        mySwordAdditionalReadings3 = mySwordElements?.get(2) ?: "null"
        //Log.i(TAG, "assignMySwordReadings: mySwordAdditionalReadings3: $mySwordAdditionalReadings3")
        mySwordAdditionalReadings4 = mySwordElements?.get(3) ?: "null"
        //Log.i(TAG, "assignMySwordReadings: mySwordAdditionalReadings4: $mySwordAdditionalReadings4")
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
        Bereishit[0], Joshua[1], Psalms 1-8[2], Matthew 1-4[3], Romans 1-3[4],
        Jos.1.1#Psa.1.1#Mat.1.1#Rom.1.1[5], Jos_1_1#Psa_1_1#Mat_1_1#Rom_1_1[6] -7-elements
         */

        /*
        //Assign readings:
        assignMySwordReadings()

        assignYouVersionReadings()
         */

        //Set the Additional Readings title text:
        //myTextView.text = myData?.text ?: "Default Text"
        val add_readings_shabbat_title_text = view.findViewById<TextView>(R.id.additionalReadingsTitleText)
        add_readings_shabbat_title_text.text = parashaName

        // YouVersion Bible selection button
        val bibleTranslationPreferencesTextView = view.findViewById<TextView>(R.id.additionalReadingsBibleTranslationPreferences)

        bibleTranslationPreferencesTextView.setOnClickListener {
            var youVersionTranslationFragment: YouVersionTranslationFragment = YouVersionTranslationFragment()
            var bundle = Bundle()
            bundle.putInt("callerFragment", 1) // 0 for ShabbatDetailFragment, 1 for AdditionalReaddingsDetailFragment
            youVersionTranslationFragment.setArguments(bundle) // pass the bundle to the fragmentarguments

            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, youVersionTranslationFragment)
                .commit()
        }

        //RadioGroups and RadioButtons:
        val addReadingsMySwordRadioButton = view.findViewById<TextView>(R.id.addReadingsMySwordRadioButton)
        val addReadingsYouVersionRadioButton = view.findViewById<TextView>(R.id.addReadingsYouVersionRadioButton)
        addReadingsMySwordRadioButton.isEnabled = true
        addReadingsYouVersionRadioButton.isEnabled = true

        val bibleSelectionRadioGroup = view.findViewById<RadioGroup>(R.id.addReadingsBibleSelectionRadioGroup)
        bibleSelectionRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.addReadingsMySwordRadioButton -> {
                    //Log.i(TAG, "onViewCreated: mySwordRadioButton selected.")
                    mySwordRadioButtonSelected = true
                    youVersionRadioButtonSelected = false
                }

                R.id.addReadingsYouVersionRadioButton -> {
                    //Log.i(TAG, "onViewCreated: youVersionRadioButton selected.")
                    mySwordRadioButtonSelected = false
                    youVersionRadioButtonSelected = true
                }
            }
        }

        /**
         * torahTextView.isClickable = true
         * torahTextView.setOnClickListener {
         */
        val additionalReadings1TextView = view.findViewById<TextView>(R.id.additionalReadings1TextView)
        additionalReadings1TextView.isClickable = true
        if (additionalReadings1.equals("null", ignoreCase = true)) {
            additionalReadings1TextView.isEnabled = false
            //Log.i(TAG, "onViewCreated: additionalReadings2 [disabled]")
        } else //Log.i(TAG, "onViewCreated: additionalReadings1TextView.ISNT_Enabled")
            additionalReadings1TextView.text = additionalReadings1

        /*val additionalReadings1TextView = view.findViewById<TextView>(R.id.additionalReadings1TextView)
        additionalReadings1TextView.isClickable = true
        if (additionalReadings1TextView.isEnabled) {
            //Log.i(TAG, "onViewCreated: additionalReadings1TextView.isEnabled")
        } else //Log.i(TAG, "onViewCreated: additionalReadings1TextView.ISNT_Enabled")
        additionalReadings1TextView.text = additionalReadings1
        if (additionalReadings1.equals("null", ignoreCase = true)) {
            additionalReadings1TextView.isEnabled = false
            //Log.i(TAG, "onViewCreated: additionalReadings1 [disabled]")
        }// else additionalReadings1TextView.text = additionalReadings1

         */

        additionalReadings1TextView.setOnClickListener {
            if (youVersionRadioButtonSelected) {
                //Log.i(TAG, "onViewCreated: additionalReadings1TextView CLICKED")
                try {
                    //Log.i(TAG, "onViewCreated: additionalReadings1TextView -> clicked!")
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempYouVersionIntent =
                        youVersionTranslationPreText + youVersionAdditionalReadings1 + youVersionTranslationEndText
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
                    val tempMySwordIntent = mySwordPretext + mySwordAdditionalReadings1
                    launchIntent.setData(Uri.parse(tempMySwordIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("market://details?id=com.riversoft.android.mysword"))
                    startActivity(intent)
                }
            }
        } //additionalReadings1TextView onClickListener

        val additionalReadings2TextView = view.findViewById<TextView>(R.id.additionalReadings2TextView)
        additionalReadings2TextView.isClickable = true
        if (additionalReadings2.equals("null", ignoreCase = true)) {
            additionalReadings2TextView.isEnabled = false
            //Log.i(TAG, "onViewCreated: additionalReadings2 [disabled]")
        } else additionalReadings2TextView.text = additionalReadings2

        additionalReadings2TextView.isClickable = true
        additionalReadings2TextView.setOnClickListener {
            if (youVersionRadioButtonSelected) {
                try {
                    //Log.i(TAG, "onViewCreated: additionalReadings2TextView -> clicked!")
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempYouVersionIntent = youVersionTranslationPreText + youVersionAdditionalReadings2 + youVersionTranslationEndText
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
                    val tempMySwordIntent = mySwordPretext + mySwordAdditionalReadings2
                    launchIntent.setData(Uri.parse(tempMySwordIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("market://details?id=com.riversoft.android.mysword"))
                    startActivity(intent)
                }
            }
        } //haftarahTextView onClickListener

        val additionalReadings3TextView = view.findViewById<TextView>(R.id.additionalReadings3TextView)
        additionalReadings3TextView.isClickable = true
        if (additionalReadings3.equals("null", ignoreCase = true)) {
            additionalReadings3TextView.isEnabled = false
            //Log.i(TAG, "onViewCreated: additionalReadings3 [disabled]")
        } else additionalReadings3TextView.text = additionalReadings3

        additionalReadings3TextView.isClickable = true
        additionalReadings3TextView.setOnClickListener {
            if (youVersionRadioButtonSelected) {
                try {
                    //Log.i(TAG, "onViewCreated: additionalReadings3TextView -> clicked!")
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempYouVersionIntent =
                        youVersionTranslationPreText + youVersionAdditionalReadings3 + youVersionTranslationEndText
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
                    val tempMySwordIntent = mySwordPretext + mySwordAdditionalReadings3
                    launchIntent.setData(Uri.parse(tempMySwordIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("market://details?id=com.riversoft.android.mysword"))
                    startActivity(intent)
                }
            }
        } //additionalReadings3TextView onClickListener

        val additionalReadings4TextView = view.findViewById<TextView>(R.id.additionalReadings4TextView)
        additionalReadings4TextView.isClickable = true
        if (additionalReadings4.equals("null", ignoreCase = true)) {
            additionalReadings4TextView.isEnabled = false
            //Log.i(TAG, "onViewCreated: additionalReadings4 [disabled]")
        } else additionalReadings4TextView.text = additionalReadings4

        additionalReadings4TextView.isClickable = true
        additionalReadings4TextView.setOnClickListener {
            if (youVersionRadioButtonSelected) {
                try {
                    //Log.i(TAG, "onViewCreated: additionalReadings4TextView -> clicked!")
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempYouVersionIntent =
                        youVersionTranslationPreText + youVersionAdditionalReadings4 + youVersionTranslationEndText
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
                    val tempMySwordIntent = mySwordPretext + mySwordAdditionalReadings4
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
}