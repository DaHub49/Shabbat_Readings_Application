package za.co.howtogeek.shabbatreadingsapplication.fragments

/*import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
//import za.co.howtogeek.shabbatreadingsapplication.fragments.BibleSelectionFragment
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Kotlin_ShabbatDetailFragment : Fragment() {
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
    private var mNewShabbatReading: NewShabbatReading? = null
    private var selectedBibleTranslation = 0
    private var parshaPosition = 0

    private val selectedFragment: String? = null

    var mPreferences: SharedPreferences? = null

    //booleans to check if RadioButtons are checked:
    var mySwordRadioButtonSelected: Boolean = false
    var youVersionRadioButtonSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadFromSharedPreferences()
    } //onCreate()

    private fun loadFromSharedPreferences() {
        mPreferences = requireActivity().getSharedPreferences(
            "BibleTranslationPreferences",
            Context.MODE_PRIVATE
        )
        parshaPosition = mPreferences.getInt("parshaPosition", 0)
        val editor: SharedPreferences.Editor = mPreferences.edit()
        editor.putString("fragment", "ShabbatDetailFragment")
        editor.apply()

        selectedBibleTranslation = mPreferences.getInt("bibleTranslationInt", 0)

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

        mNewShabbatReading = NewShabbatReading()

        try {
            reader =
                BufferedReader(InputStreamReader(requireActivity().assets.open("ffoz_bamidbar_5784.txt")))

            //will cycle through each line of the text file and compare names until it finds the correct position:
            var mLine = ""
            //WORKING! SORT OUT ASSIGNMENT!
            for (i in 0..parshaPosition) {
                mLine = reader.readLine()
            }
            val lineElements =
                mLine.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            //
            mNewShabbatReading = NewShabbatReading()
            mNewShabbatReading.setParashaName(lineElements[0])
            mNewShabbatReading.setGregorianDate(lineElements[1])
            mNewShabbatReading.setHebrewDate(lineElements[2])
            mNewShabbatReading.setTorahPortion(lineElements[3])
            mNewShabbatReading.setHaftarahPortion(lineElements[4])
            mNewShabbatReading.setGospelPortion(lineElements[5])


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
            assignYouVersionReadings()
            assignMySwordReadings()
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
        val root: View = inflater.inflate(R.layout.fragment_shabbat_detail, container, false)

        importFFOZFile()

        val shabbat_reading_set = root.findViewById<TextView>(R.id.shabbat_reading_set)
        val shabbat_title_text = root.findViewById<TextView>(R.id.shabbat_title_text)
        val gregorianDateTextView = root.findViewById<TextView>(R.id.gregorianDateTextView)
        val biblicalDateTextView = root.findViewById<TextView>(R.id.biblicalDateTextView)

        val torahTextView = root.findViewById<TextView>(R.id.torahTextView)
        val haftarahTextView = root.findViewById<TextView>(R.id.haftarahTextView)
        val britChadashahTextView = root.findViewById<TextView>(R.id.britChadashahTextView)
        val bibleTranslationPreferences =
            root.findViewById<TextView>(R.id.bibleTranslationPreferences)

        bibleTranslationPreferences.setOnClickListener {
            val bibleSelectionFragment: BibleSelectionFragment = BibleSelectionFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, bibleSelectionFragment)
                .addToBackStack(null).commit()
        }

        val mySwordRadioButton: RadioButton =
            root.findViewById<RadioButton>(R.id.mySwordRadioButton)
        val youVersionRadioButton: RadioButton =
            root.findViewById<RadioButton>(R.id.youVersionRadioButton)

        mySwordRadioButton.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (isChecked) {
                    mySwordRadioButtonSelected = true //mySwordRB
                    youVersionRadioButton.setChecked(false)
                    youVersionRadioButtonSelected = false
                }
            }
        })

        youVersionRadioButton.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                if (isChecked) {
                    youVersionRadioButtonSelected = true //mySwordRB
                    mySwordRadioButton.setChecked(false)
                    mySwordRadioButtonSelected = false
                }
            }
        })

        shabbat_reading_set.text = "First Fruits of Zion"

        shabbat_title_text.setText(mNewShabbatReading.getParashaName())

        gregorianDateTextView.setText(mNewShabbatReading.getGregorianDate())

        biblicalDateTextView.setText(mNewShabbatReading.getHebrewDate())

        //Receives the member variables and disables the buttons if the value is 'null'
        val torahPortion: String = mNewShabbatReading.getTorahPortion()
        if (torahPortion.equals("null", ignoreCase = true)) {
            torahTextView.isEnabled = false
        } else torahTextView.text = torahPortion

        val haftarahPortion: String = mNewShabbatReading.getHaftarahPortion()
        if (haftarahPortion.equals("null", ignoreCase = true)) {
            haftarahTextView.isEnabled = false
        } else haftarahTextView.text = haftarahPortion

        val ntPortion: String = mNewShabbatReading.getGospelPortion()
        if (ntPortion.equals("null", ignoreCase = true)) {
            britChadashahTextView.isEnabled = false
        } else britChadashahTextView.text = ntPortion


        /**
         * FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
         * BibleFragment bibleFragment = new BibleFragment();
         * bibleFragment.show(fragmentManager, CHOOSE_BIBLE);
         */


        //opening different Bibles, depending on which radio Buttons are selected:
        torahTextView.setOnClickListener {
            if (youVersionRadioButtonSelected) {
                try {
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempYouVersionIntent =
                        youVersionTranslationPreText + youVersionTorahURL + youVersionTranslationEndText
                    launchIntent.setData(Uri.parse(tempYouVersionIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("market://details?id=com.sirma.mobile.bible.android"))
                    startActivity(intent)
                }
            } else if (mySwordRadioButtonSelected) {
                try {
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

        haftarahTextView.setOnClickListener {
            if (youVersionRadioButtonSelected) {
                try {
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempYouVersionIntent =
                        youVersionTranslationPreText + youVersionHaftarahURL + youVersionTranslationEndText
                    launchIntent.setData(Uri.parse(tempYouVersionIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("market://details?id=com.sirma.mobile.bible.android"))
                    startActivity(intent)
                }
            } else if (mySwordRadioButtonSelected) {
                try {
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
            /*else {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        BibleFragment bibleFragment = new BibleFragment();
                        bibleFragment.show(fragmentManager, CHOOSE_BIBLE);
                    }*/
        } //haftarahTextView onClickListener

        britChadashahTextView.setOnClickListener {
            if (youVersionRadioButtonSelected) //YouVersion
            {
                try {
                    val tempYouVersionIntent =
                        youVersionTranslationPreText + youVersionNTURL + youVersionTranslationEndText
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    launchIntent.setData(Uri.parse(tempYouVersionIntent))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("market://details?id=com.sirma.mobile.bible.android"))
                    startActivity(intent)
                }
            } else if (mySwordRadioButtonSelected) //MySword
            {
                try {
                    val launchIntent = Intent(Intent.ACTION_VIEW)
                    val tempNTReadings = mySwordPretext + mySwordGospelReadings
                    launchIntent.setData(Uri.parse(tempNTReadings))
                    startActivity(launchIntent)
                } catch (e: Exception) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse("market://details?id=com.riversoft.android.mysword"))
                    startActivity(intent)
                }
            }
            /*else {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        BibleFragment bibleFragment = new BibleFragment();
                        bibleFragment.show(fragmentManager, CHOOSE_BIBLE);
                    }*/
        }
        return root
    } //onCreateView()

    private fun assignYouVersionReadings() {
        val youVersionElements =
            fullYouVersionReadings!!.split("#".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
        youVersionTorahURL = youVersionElements[0]
        youVersionHaftarahURL = youVersionElements[1]
        //Log.i(TAG, "assignYouVersionReadings -> youVersionElements[1]: " + youVersionElements[1]);
        youVersionNTURL = youVersionElements[2]
        //Log.i(TAG, "assignYouVersionReadings -> youVersionElements[2]: " + youVersionElements[2]);
    }

    private fun assignMySwordReadings() {
        //fullMySwordReadings
        //Log.i(TAG, "assignMySwordReadings -> [error here]");
        val mySwordElements =
            fullMySwordReadings!!.split("#".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
        mySwordTorahReadings = mySwordElements[0]
        mySwordHaftarahReadings = mySwordElements[1]
        mySwordGospelReadings = mySwordElements[2]
        //Log.i(TAG, "assignMySwordReadings -> [fullMySwordString]: " + fullMySwordReadings);
    }

    companion object {
        /**
         * ShabbatDetailFragment.java:
         * Author: Dylan Martin, George, South Africa
         *
         * ShabbatDetailFragment displays the week's readings of the user's selection
         * The arguments passed through are:
         * 1. "parshaPosition"- the position of the line in the text file
         * 2. and "readingSet"- the int to know which file to open.
         * 3. NB (NOTE TO SELF): FILES ALSO NEED TO BE AMENDED HERE
         * 4. " " JUST REMEMBER TO FIND ALL OCCURRENCES OF OLD TEXT FILES AND THEN CHANGE TO NEW ONES
         */
        private const val CHOOSE_BIBLE = "CHOOSE BIBLE"
    }
}

 */