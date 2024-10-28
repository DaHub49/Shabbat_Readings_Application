import android.content.Intent
import androidx.fragment.app.replace
import za.co.howtogeek.shabbatreadingsapplication.adapters.ShabbatReadingAdapter
import za.co.howtogeek.shabbatreadingsapplication.fragments.ShabbatDetailFragment
import za.co.howtogeek.shabbatreadinsapplication.R


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.co.howtogeek.shabbatreadinsapplication.adapters.YouVersionTranslationsAdapter

/**
 * A simple [Fragment] subclass that allows the user to select the desired YouVersion
 * translation and save to SharedPreferences.
 *
 * Creation date: 16/10/2024 @08:51
 * Update date: 20/10/2024 @06:30: Goal -> To add SharedPreferences to the Bible Settings Fragment
 *
 * translationIndex
 *
 * 0: TS2009
 * 1: English NASB
 * 2: English HCSB
 * 3: Xhosa
 * 4: Afrikaans
 * 5: Zulu
 * 6: Northern Sotho
 * 7: Tsonga
 * 8: Southern Ndebele
 *
 */
private val TAG = "settings -> YouVersionTranslationFragment -> "
private val PREFERENCES = "my_prefs"

class YouVersionTranslationFragment : Fragment(), YouVersionTranslationsAdapter.OnItemClickListener {

    private lateinit var translation_recycler_view: RecyclerView
    //val list1 = ArrayList<String>() // Creates an empty ArrayList of type String
    private lateinit var translations: ArrayList<String>
        // Initialize parashaNames

    //SharedPreferences:
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun OnItemClickListener(translationIndex: Int) {
        Log.i(TAG, "OnTranslationClickListener -> translationIndex: $translationIndex")

        val sharedPreferences = requireActivity().getSharedPreferences("bibletranslationpreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val defaultTranslationIndex = 0 // Your default value

        if (!sharedPreferences.contains("translationIndex")) { // Replace with your desired key
            editor.putInt("translationIndex", translationIndex) // Replace with your desired key
            editor.apply() // or editor.commit()
        }

        val bundle = Bundle().apply {
            putInt("translationIndex", translationIndex)
        }

        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val shabbatDetailFragment = ShabbatDetailFragment()
        fragmentTransaction.replace(R.id.fragment_container, shabbatDetailFragment)
        fragmentTransaction.commit()
    }

    //2. DO GEMINI STEP2 IN ADAPTER @27/10/24 11:36

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Load the string array into the ArrayList
        translations = ArrayList(resources.getStringArray(R.array.youversion_translations).toList())

    } // onCreate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{

        val view = inflater.inflate(R.layout.fragment_you_version_translation, container, false)

        /**
         * sharedPreferences = requireActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
         * val editor = sharedPreferences.edit()
         * editor.putInt("parashaPosition", position)
         * editor.commit()
         */

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

        // Load saved integer
        val bibleTranslationInt = sharedPreferences.getInt("bibleTranslationString", 0)

        //0. HeaderTextView:
        var reading_list_fragment_title_text: TextView = view.findViewById(R.id.you_version_translation_fragment_title)

        //1. RecyclerView:
        translation_recycler_view = view.findViewById<RecyclerView>(R.id.translation_recycler_view)

        //2. vertical layout manager
        translation_recycler_view.layoutManager = LinearLayoutManager(requireContext())

        //4. Pass ArrayList to adapter:
        val adapter =
            YouVersionTranslationsAdapter(requireContext(), this)
        translation_recycler_view.adapter = adapter

        return view

    }

    fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "pref_bible_translation") {
            val selectedTranslation = sharedPreferences?.getString(key, "youversion_english") ?: "youversion_english"
            // Store or use the selectedTranslation value as needed
        }
    }
    //populate the views now that the layout has been inflated:
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated: [called]")
    }

}