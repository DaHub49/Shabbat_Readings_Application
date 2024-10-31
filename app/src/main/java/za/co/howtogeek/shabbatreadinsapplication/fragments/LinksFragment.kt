package za.co.howtogeek.shabbatreadinsapplication.fragments

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import za.co.howtogeek.shabbatreadingsapplication.adapters.ShabbatReadingAdapter
import za.co.howtogeek.shabbatreadingsapplication.fragments.HomeFragment
import za.co.howtogeek.shabbatreadinsapplication.R
import za.co.howtogeek.shabbatreadinsapplication.adapters.LinksAdapter

private val TAG = "LinksFragment -> "

class LinksFragment : Fragment(), ShabbatReadingAdapter.OnItemClickListener  { /*@Override
public void onItemClick(int position){
    String clickedItem = data.get(position);
    Log.i(TAG, "onItemClick -> Clicked item: " + clickedItem);
}
 */
    /**
     * LinksFragment.java:
     * Author: Dylan Martin, George, South Africa
     *
     * LinksFragment displays various links to different sources and teachings related to the app
     * and connects the links to TextViews
     *
     */

    override fun onItemClick(position: Int) {

        // Handle item click
        Log.i(TAG, "onItemClick: [called]")
        val linkName = linksList[position]
        Log.i(TAG, "onItemClick -> linkName: $linkName")
                // Handle item click

        openLinkInBrowser(requireContext(), linksList[position])

        /*val fragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

         */


    }

    /**
     * "https://www.youtube.com/@andrewhodkinson1"
     * "https://bethtikkun.com/category/media/"
     * "https://www.youtube.com/@bethtikkunmessianic"
     * "http://www.thecreationgospel.com/"
     * "https://www.youtube.com/@thecreationgospel"
     * "https://ffoz.org/"
     * "https://www.youtube.com/@firstfruitsofzion"
     * "https://firmisrael.org/"
     * "https://www.youtube.com/@FIRMisrael"
     * "https://www.youtube.com/@grantluton"
     * "https://torahtodayministries.org/"
     */

    fun openLinkInBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Handle the case where no browser is available
            Toast.makeText(context, "No browser found", Toast.LENGTH_SHORT).show()
        }
    }

    private lateinit var links_recycler_view: RecyclerView
    private lateinit var adapter: ShabbatReadingAdapter
    private lateinit var linksList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reading_list_template, container, false)

        val readingListFragmentTitleText: TextView = view.findViewById(R.id.reading_list_fragment_title_text)
        readingListFragmentTitleText.text = "Links:"

        linksList = ArrayList<String>()

        //populate list:
        linksList.add("https://www.youtube.com/@andrewhodkinson1")
        linksList.add("https://bethtikkun.com/category/media/")
        linksList.add("https://www.youtube.com/@bethtikkunmessianic")
        linksList.add("http://www.thecreationgospel.com/")
        linksList.add("https://www.youtube.com/@thecreationgospel")
        linksList.add("https://ffoz.org/")
        linksList.add("https://www.youtube.com/@firstfruitsofzion")
        linksList.add("https://firmisrael.org/")
        linksList.add("https://www.youtube.com/@FIRMisrael")
        linksList.add("https://www.youtube.com/@grantluton")
        linksList.add("https://torahtodayministries.org/")

        links_recycler_view = view.findViewById(R.id.recyclerView)
        links_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        adapter = ShabbatReadingAdapter(requireContext(), linksList, this)
        links_recycler_view.adapter = adapter

        return view
    }

}