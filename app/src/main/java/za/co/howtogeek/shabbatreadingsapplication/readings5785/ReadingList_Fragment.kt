package za.co.howtogeek.shabbatreadingsapplication.readings5785


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
import za.co.howtogeek.shabbatreadingsapplication.fragments.ShabbatDetailFragment
import za.co.howtogeek.shabbatreadingsapplication.R
import java.io.BufferedReader
import java.io.InputStreamReader

private val TAG = "readings5785 -> ReadingListFragment -> "
private val FILENAME = "messianic_berasheet_5785.txt"
private val PREFERENCES = "preferences"
private val PARASHA_POSITION = "parashaPosition"
private const val PARENTFRAGMENT = 1 //0 for ReadingList_Fragment, 1 for AdditionalReadingsFragment

class ReadingList_Fragment : Fragment(), ShabbatReadingAdapter.OnItemClickListener {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShabbatReadingAdapter
    private lateinit var parashaNames: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reading_list_template, container, false)

        // Header TextView
        val readingListFragmentTitleText: TextView = view.findViewById(R.id.reading_list_fragment_title_text)
        readingListFragmentTitleText.text = getString(R.string.messianic_reading_cycle)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        editor.putInt("parentFragment", PARENTFRAGMENT)
        editor.commit()

        val shabbatDetailFragment = ShabbatDetailFragment()
        val bundle = Bundle()

        //bundle.putInt("parashaPosition", position)
        shabbatDetailFragment.arguments = bundle
        val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, shabbatDetailFragment)
            .addToBackStack(null).commit()
    }

}