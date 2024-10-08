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
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Bamidbar_5782_Fragment- a centralised fragment that contains the both the First Fruits of Zion and Lion Lamb Ministries' reading plans based off the Book of Numbers (Bamidbar)
 */
private val TAG = "za.co.howtogeek.shabbatreadingsapplication.readings5784 -> ReadingListFragment -> "

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
     * Current update date: 08 October, 2024
     *
     * 08/10/24 13:21: Testing GitHub works correctly?
     */

   /**
     * changes 08/10 07:56
     */

    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: ShabbatReadingAdapter
    //val list1 = ArrayList<String>() // Creates an empty ArrayList of type String
    private var parashaNames = ArrayList<String>()

    /*
    override fun onItemClick(item: String) {
        Log.i(TAG, "onItemClick: $item")

        /* Item click listener
        // ReadingList_Fragment.kt
        val action = HomeFragmentDirections.actionHomeToDetails(itemId = 12345)
        findNavController().navigate(action)
         */


        //val action = HomeFragmentDirections.action_readingList_Fragment_to_shabbat_detail_fragment(title = item)

        //findNavController().navigate(R.id.action_ReadingListFragment_to_HomeFragment)
        //findNavController().navigate(R.id.action_readingList_Fragment_to_shabbat_detail_fragment())
    }
     */

    private fun readFFOZFileSaveToArrayList(context: Context, filename: String) : ArrayList<String> {

        //ChatGPT 18/07:
        // Initialize an empty ArrayList to store lines
        //val lines = ArrayList<String>()
        // Read the file using Kotlin extension function readLines()
        //File(filename).useLines { lines.addAll(it) }
        //ChatGPT end

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

        //val shabbatReadingAdapter = ShabbatReadingAdapter {shabbatReading -> adapterOnClick(shabbatReading) }
        //shabbatReadingAdapter.populateArrayList(parashaNames)
        //val readingsAdapter = ShabbatReadingAdapter(shabbatReadingAdapterOnparashaNames)

        //4. Pass ArrayList to adapter:
        adapter = ShabbatReadingAdapter(
            parashaNames,
            this)

        recyclerView.adapter = adapter /** MyAdapter(items) { item ->
            // Handle item click and navigate
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(item.id)
            navController.navigate(action)
            }
            */



        return view

    }

    override fun onItemClick(position: Int) {
        // Handle item click and use the position
        val clickedItem = parashaNames[position]
        Toast.makeText(context, "Clicked: ${clickedItem.get(position)}", Toast.LENGTH_SHORT).show()
        // You can also pass this position to another activity or fragment if needed

    }

    //populate the views now that the layout has been inflated:
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i(TAG, "onViewCreated: [called]")
    }

    /*


        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.

        //CustomAdapter(private val context: Context, private val dataList: List<String>)
        adapter = CustomAdapter(context, parashaNames)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object: ShabbatReadingAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, text: String) {
                Log.d("ShabbatReadingAdapter", "Selected item position: $position, Text: $text")
            }
        })

        return view
    }

    //ChatGPT Step2 (see ShabbatReadingAdapter for all steps)
    override fun onItemClick(position: Int) {
        // Create a new instance of your fragment and pass the position
        //val shabbatDetailFragment = ShabbatDetailFragment.newInstance("Custom Name", position)

        //Use Navigation to navigate??
        findNavController().navigate(R.id.readings_fragment_to_shabbat_detail_fragment)

        /* Example: Navigate to the fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
         */
    }

     */

    /*
    private fun importAdditionalReadings5784File() {

        /*
        val inputStream = context?.assets?.open("formatted_bamidbar_additional_parashat_readings.txt")
        val size = inputStream?.available()
        val buffer = size?.let { ByteArray(it) }
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charsets.UTF_8) // Convert the buffer to a string
    } catch (e: IOException) {
        e.printStackTrace()
        "" // Return empty string on error
    }

    //To create a simplified list of just the names of the weekly portions:
    parashaNames = ArrayList()

    //do reading, usually loop until end of file read:
    var mLine = ""
    while ((reader.readLine().also { mLine = it }) != null) {
        //Bamidbar,Isaiah 55-60,Job 29-34,Acts 1-2,James,Isa.55.1#Job.29.1#Act.1.1#Jam.1.1
        // ,Isa_55_1#Job_29_1#Act_1_1#Jam_1_1

        //break the line of the text file into an array and get the Parasha name to display:

        val tempElements =
            mLine.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        parashaNames.add(tempElements[0])
            }
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
        } */
    } //importExtendedReadings5784File

     */
}