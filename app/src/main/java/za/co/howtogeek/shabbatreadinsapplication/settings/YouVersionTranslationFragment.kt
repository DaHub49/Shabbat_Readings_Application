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

/**
 * A simple [Fragment] subclass that allows the user to select the desired YouVersion
 * translation and save to SharedPreferences.
 *
 * Creation date: 16/10/2024 @08:51
 * Update date: 20/10/2024 @06:30: Goal -> To add SharedPreferences to the Bible Settings Fragment
 *
 */
private val TAG = "settings -> YouVersionTranslationFragment -> "

class YouVersionTranslationFragment : Fragment(), ShabbatReadingAdapter.OnItemClickListener {

    private lateinit var translation_recycler_view: RecyclerView
    private lateinit var adapter: ShabbatReadingAdapter
    //val list1 = ArrayList<String>() // Creates an empty ArrayList of type String
    private lateinit var translations: ArrayList<String>
        // Initialize parashaNames

    //SharedPreferences:
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Load the string array into the ArrayList
        translations = ArrayList(resources.getStringArray(R.array.youversion_translations).toList())

    } // onCreate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{

        val view = inflater.inflate(R.layout.fragment_you_version_translation, container, false)

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("BibleTranslationPreferences", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Load saved integer
        val bibleTranslationInt = sharedPreferences.getInt("bibleTranslationInt", 0)

        //0. HeaderTextView:
        var reading_list_fragment_title_text: TextView = view.findViewById(R.id.you_version_translation_fragment_title)

        //1. RecyclerView:
        translation_recycler_view = view.findViewById<RecyclerView>(R.id.translation_recycler_view)

        //2. vertical layout manager
        translation_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        //4. Pass ArrayList to adapter:
        adapter = ShabbatReadingAdapter(
            requireContext(),
            translations,
            this)

        translation_recycler_view.adapter = adapter

        return view

    }

    override fun onItemClick(position: Int) {

        val bundle = Bundle().apply {
            putInt("parashaPosition", position)
        }

        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val shabbatDetailFragment = ShabbatDetailFragment()
        fragmentTransaction.replace(R.id.fragment_container, shabbatDetailFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        //val intent: Intent = s, ShabbatReadingActivity.class)
        //findNavController().navigate(R.id.action_ReadingListFragment_to_ShabbatDetailFragment, bundle)

    }



    /*override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "pref_bible_translation") {
            val selectedTranslation = sharedPreferences?.getString(key, "youversion_english") ?: "youversion_english"
            // Store or use the selectedTranslation value as needed
        }
    }
     */

    //populate the views now that the layout has been inflated:
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated: [called]")
    }

}