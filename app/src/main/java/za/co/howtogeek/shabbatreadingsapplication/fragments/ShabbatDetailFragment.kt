package za.co.howtogeek.shabbatreadingsapplication.fragments

import android.content.Context
import android.content.SharedPreferences
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

    private val TAG = "za.co.howtogeek.shabbatreadingsapplication.fragments -> "

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

    private var parashaPosition = 0
    private var parashaName = "Parasha"

    private val selectedFragment: String? = null

    private var mPreferences: SharedPreferences? = null

    //booleans to check if RadioButtons are checked:
    var mySwordRadioButtonSelected: Boolean = false
    var youVersionRadioButtonSelected: Boolean = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            parashaName = arguments?.getString("parashaName").toString()
            Log.i(TAG, "ShabbatDetailFragment -> onCreate -> parashaName: " + parashaName)
            parashaPosition = arguments?.getInt("parashaPosition")!!
            Log.i(TAG, "ShabbatDetailFragment -> onCreate -> parashaPosition: " + parashaPosition)
        }
        val context = requireContext()

        mPreferences = context.getSharedPreferences("", Context.MODE_PRIVATE)
        //selectedBibleTranslation = arguments?.getInt("bibleTranslationInt")!!
        //loadFromSharedPreferences()
    }

    /*private fun loadFromSharedPreferences() {
        if(mPreferences != null) {
            mPreferences = requireContext().getSharedPreferences(
                "BibleTranslationPreferences",
                Context.MODE_PRIVATE
            )
            parshaPosition = mPreferences.getInt("parshaPosition", 0)
            val editor: SharedPreferences.Editor = mPreferences.edit()
            editor.putString("fragment", "ShabbatDetailFragment")
            editor.apply()
        }

     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        var reader: BufferedReader? = null

        //mNewShabbatReading = ShabbatReading()

        try {
            reader =
                BufferedReader(InputStreamReader(requireActivity().assets.open("ffoz_bamidbar_5784.txt")))

            //Bamidbar[0],
            //8 Jun. 2024[1],
            // 2 Sivan[2],
            // Num. 1:1–4:20[3],
            // HOS. 2:1–22(1:10–2:20)[4],
            // Mt. 4:1–17[5],
            // Num.1.1–4.20#Hos.2.1–22#Mat.4.1–17[6],
            // Num_1_1–4_20#Hos_2_1–22#Mat_4_1–17[7]
            //will cycle through each line of the text file and compare names until it finds the correct position:
            var mLine = ""
            //WORKING! SORT OUT ASSIGNMENT!
            for (i in 0..parashaPosition) {
                if (i == parashaPosition)
                    mLine = reader.readLine()
            }
            val lineElements =
                mLine.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            /**
             * ToDo Consider adding more elements to original array
             */

            //Bamidbar[0],
            //8 Jun. 2024[1],
            // 2 Sivan[2],
            // Num. 1:1–4:20[3],
            // HOS. 2:1–22(1:10–2:20)[4],
            // Mt. 4:1–17[5],
            // Num.1.1–4.20#Hos.2.1–22#Mat.4.1–17[6],
            // Num_1_1–4_20#Hos_2_1–22#Mat_4_1–17[7]

            var index: Int = 0
            mNewShabbatReading = ShabbatReading(index, 0,
                //1: parashaName:
                lineElements[0],
                //2: Gregorian Date:
                lineElements[1],
                //3: Hebrew Date:
                lineElements[2],
                //4: TorahPortion:
                lineElements[3],
                //5: haftarahPortion:
                lineElements[4],
                //6: gospelPortion:
                lineElements[5],
                //7: youVersion:
                lineElements[6],
                //6: mySword:
                lineElements[7]
            )
            //start check with Haftarah Books 1st, then New Testament:

            //now check for Ezekiel for YouVersion: (if contains("Eze") replace ("ezk)
            fullYouVersionReadings = if (lineElements[6].contains("Eze")) {
                lineElements[6].replace("Eze", "ezk")
            } else lineElements[6]

            //either way, at this point in time, fullYouVersionReadings will still be equal to lineElements[6], but with a replacement if necessary
            //so use the placeholder in the next conditional statement if necessary:

            //To make intent work correctly with the YouVersion Bible for the book of John:
            if (fullYouVersionReadings!!.contains("Joh")) {
                val phYouVersionReadings = fullYouVersionReadings
                fullYouVersionReadings = phYouVersionReadings!!.replace("Joh", "Jhn")
            } else if (fullYouVersionReadings!!.contains("Mar")) {
                val phYouVersionReadings = fullYouVersionReadings
                //Log.i(TAG, "ShabbatDetailFragment - before altered fullYouVersionReadings: " + fullYouVersionReadings);
                //Log.i(TAG, "ShabbatDetailFragment - phYouVersionReadings: " + phYouVersionReadings);
                fullYouVersionReadings = phYouVersionReadings!!.replace("Mar", "Mrk")
                //Log.i(TAG, "ShabbatDetailFragment - altered fullYouVersionReadings: " + fullYouVersionReadings);
            }

            fullMySwordReadings = lineElements[7]
            //assignYouVersionReadings()
            //assignMySwordReadings()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    } //importFFOZFile()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shabbat_detail, container, false)
    }

    companion object {
        fun newInstance(parashaName: String, parashaPosition: Int): ShabbatDetailFragment {
            val fragment = ShabbatDetailFragment()
            val args = Bundle()
            args.putString("parashaName", parashaName)
            args.putInt("parashaPosition", parashaPosition)
            fragment.arguments = args
            return fragment
        }
    }
}