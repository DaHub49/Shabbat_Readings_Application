package za.co.howtogeek.shabbatreadinsapplication.readings5785


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.co.howtogeek.shabbatreadingsapplication.AssetReader.FileReader
import za.co.howtogeek.shabbatreadingsapplication.adapters.ShabbatReadingAdapter
import za.co.howtogeek.shabbatreadinsapplication.R

private val TAG = "readings5785 -> ReadingListFragment -> "

class ReadingList_Fragment : Fragment(), ShabbatReadingAdapter.OnItemClickListener {

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
        readingListFragmentTitleText.text = getString(R.string.first_fruit_of_zion)

        val context = requireContext()
        //initialize parashaNames
        parashaNames = try {
            FileReader.readFile(context.assets.open("ffoz_berasheet_5785.txt")) as ArrayList<String>
        } catch (e: Exception) {
            Log.e(TAG, "Error reading file: ${e.message}", e)
            arrayListOf<String>() // Return an empty ArrayList in case of error
        }
        Log.i(TAG, "onCreateView: parashaNames populated?!")

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
        Log.i(TAG, "onItemClick: [called]")
        val parashaName = parashaNames[position]
        Log.i(TAG, "onItemClick -> parashaName: $parashaName")
        // Assuming you're using Safe Args for navigation
        //val action = ReadingListFragmentactionDirections.actionReadingListFragmentToShabbatDetailFragment(position)
        //findNavController().navigate(action)
    }

    /*
        override fun onItemClick(position: Int) {
            // Handle item click
            Log.i(TAG, "onItemClick: [called]")
            val parashaName = parashaNames[position]
            Log.i(TAG, "onItemClick -> parashaName: $parashaName")
            // Assuming you're using Safe Args for navigation
            //val action = ReadingListFragmentactionDirections.actionReadingListFragmentToShabbatDetailFragment(position)
            //findNavController().navigate(action)
        }*/

}