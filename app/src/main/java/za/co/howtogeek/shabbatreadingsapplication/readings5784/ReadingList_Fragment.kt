package za.co.howtogeek.shabbatreadingsapplication.readings5784

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.co.howtogeek.shabbatreadingsapplication.R
import za.co.howtogeek.shabbatreadingsapplication.adapters.ShabbatReadingAdapter
import za.co.howtogeek.shabbatreadingsapplication.fragments.ShabbatDetailFragment
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Bamidbar_5782_Fragment- a centralised fragment that contains the both the First Fruits of Zion and Lion Lamb Ministries' reading plans based off the Book of Numbers (Bamidbar)
 */
private val TAG = "readings5784 -> ReadingListFragment -> "

class ReadingList_Fragment : Fragment(), ShabbatReadingAdapter.OnItemClickListener {
    /**
     * ReadingList_Fragment.java:
     * Author: Dylan Martin, South Africa
     *
     * ReadingList_Fragment is a central fragment and loads the appropriate readings depending on what the user chose from the HomeFragment.
     * The same fragment is used for both the First Fruit of Zion readings, as well as additional readings.
     *
     * The app will know which reading set to load from the int argument that was passed with the creation of the new fragment.
     * '0' for additional readings, and '1' for First Fruits of Zion.
     *+
     * Current update date: 10 October, 2024
     */

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShabbatReadingAdapter
    //val list1 = ArrayList<String>() // Creates an empty ArrayList of type String
    private var parashaNames = ArrayList<String>()

    private fun readFFOZFileSaveToArrayList(context: Context, filename: String) : ArrayList<String> {

        Log.i(TAG, "readFFOZFileSaveToArrayList: [initiated]")


        parashaNames = ArrayList<String>()
        // Initialize parashaNames

        var mLine: String?

        try {
            // Open the file from assets or external storage based on your requirement
            val assetManager = context.assets
            val inputStream = assetManager.open(filename)
            val reader = BufferedReader(InputStreamReader(inputStream))

            //new:
            var index = 0;
            while (reader.readLine().also { mLine = it } != null) {
                val tempElements = mLine!!.split(",")
                parashaNames.add(tempElements[0])
                Log.i(TAG, "readFFOZFileSaveToArrayList: parashaNames.get($index): " + parashaNames.get(index))
                index++
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //Output arrayList
        for (line in parashaNames) {
            Log.i(TAG, "ReadingListFragment -> readTextFile -> for: " + line)
        }

        return parashaNames as ArrayList<String>

    } //readTextFile


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.i(TAG, "onCreate: [called]")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_reading_list_template, container, false)

        //0. HeaderTextView:
        var reading_list_fragment_title_text: TextView = view.findViewById(R.id.reading_list_fragment_title_text)

        //1. RecyclerView:
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        //2. vertical layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //3. initialize and populate ArrayList:
        parashaNames = readFFOZFileSaveToArrayList(requireContext(), "ffoz_bamidbar_5784.txt")

        //4. Set reading_list_fragment_title_text to corresponding readings:
        reading_list_fragment_title_text.setText(getString(R.string.first_fruit_of_zion))

        //4. Pass ArrayList to adapter:
        adapter = ShabbatReadingAdapter(
            parashaNames,
            this)

        recyclerView.adapter = adapter

        return view

    }

    override fun onItemClick(position: Int) {

        val bundle = Bundle().apply {
            putInt("parashaPosition", position)
        }

        findNavController().navigate(R.id.action_ReadingListFragment_to_ShabbatDetailFragment, bundle)

    }

    //populate the views now that the layout has been inflated:
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated: [called]")
    }

}