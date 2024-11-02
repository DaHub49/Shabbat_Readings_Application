package za.co.howtogeek.shabbatreadingsapplication.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.co.howtogeek.shabbatreadingsapplication.adapters.ShabbatReadingAdapter
import za.co.howtogeek.shabbatreadingsapplication.R
import java.io.BufferedReader
import java.io.InputStreamReader

private val TAG ="fragments -> AdditionalReadingsFragment -> "
private val FILENAME = "berasheet_additional_parashat_readings_with_links_commas.txt"
private val PREFERENCES = "preferences"
private val PARASHA_POSITION = "parashaPosition" //The same SharedPreference variable can be used in both called
//fragments, as it will be changed before every detailed fragment.

class AdditionalReadingsFragment : Fragment(), ShabbatReadingAdapter.OnItemClickListener {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShabbatReadingAdapter
    private lateinit var parashaNames: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.i(TAG, "AdditionalReadingsFragment -> onCreate: called")
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reading_list_template, container, false)
        // Inflate the layout for this fragment
        //Log.i(TAG, "AdditionalReadingsFragment -> onCreateView: called")

        // Header TextView
        val readingListFragmentTitleText: TextView = view.findViewById(R.id.reading_list_fragment_title_text)
        readingListFragmentTitleText.text = getString(R.string.additional_readings)

        val context = requireContext()

        //[new]
        parashaNames = ArrayList<String>()
        // Initialize parashaNames

        var mLine: String?

        try {
            // Open the file from assets or external storage based on your requirement
            val assetManager = context.assets
            val inputStream = assetManager.open(FILENAME)
            val reader = BufferedReader(InputStreamReader(inputStream))

            //new:
            var index = 0;
            while (reader.readLine().also { mLine = it } != null) {
                //Bereishit[0]; Joshua[1]; Psalms 1-8[2]; Matthew 1-4[3]; Romans 1-3[4];
                // Jos.1.1#Psa.1.1#Mat.1.1#Rom.1.1[5]; Jos_1_1#Psa_1_1#Mat_1_1#Rom_1_1[6] -7-elements
                val tempElements = mLine!!.split(",")
                parashaNames.add(tempElements[0])
                //Log.i(TAG, "readFFOZFileSaveToArrayList: parashaNames.get($index): " + parashaNames.get(index))
                index++
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //Output arrayList
        for (line in parashaNames) {
            //Log.i(TAG, "ReadingListFragment -> readTextFile -> for: " + line)
        }

        // RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter =
            ShabbatReadingAdapter(requireContext(), parashaNames, this) // Initialize adapter early
        recyclerView.adapter = adapter

        return view
    }

    override fun onItemClick(position: Int) {
        // Handle item click
        //Log.i(TAG, "onItemClick: [called]")
        val parashaName = parashaNames[position]
        //Log.i(TAG, "onItemClick -> parashaName: $parashaName")

        //update SharedPreferences [27/10/24]:
        sharedPreferences = requireActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(PARASHA_POSITION, position)
        editor.commit()

        val additionalReadingsDetailFragment = AdditionalReadingsDetailFragment()
        //val bundle = Bundle()

        //bundle.putInt("parashaPosition", position)
        //shabbatDetailFragment.arguments = bundle
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, additionalReadingsDetailFragment)
            .addToBackStack(null).commit()
    }
}