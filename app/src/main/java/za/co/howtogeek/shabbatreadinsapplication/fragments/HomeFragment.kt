package za.co.howtogeek.shabbatreadingsapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import za.co.howtogeek.shabbatreadinsapplication.R
import za.co.howtogeek.shabbatreadinsapplication.fragments.AboutFragment
import za.co.howtogeek.shabbatreadinsapplication.fragments.LinksFragment
import za.co.howtogeek.shabbatreadinsapplication.readings5785.ReadingList_Fragment

private val TAG = "za.co.howtogeek.shabbatreadingsapplication.fragments -> HomeFragment -> "

class HomeFragment : Fragment() {

    private var aboutOrBibleHelp: Char = 'a'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.i("info", "HomeFragment -> onCreate()")
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // First Fruits of Zion Torah Portions cycles:
        view.findViewById<TextView>(R.id.ffoz_launcher).setOnClickListener {
            //Log.i(TAG, "onViewCreated: ffozLauncher: clicked")

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val readingList_Fragment = ReadingList_Fragment()
            fragmentTransaction.replace(R.id.fragment_container, readingList_Fragment)
            fragmentTransaction.addToBackStack(null) // Optional: Add to back stack

            fragmentTransaction.commit()
        }

        //Additional readings:
        view.findViewById<TextView>(R.id.additional_readings_launcher).setOnClickListener {
            //Log.i(TAG, "onViewCreated: additionalReadingsLauncher: clicked")

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val additionalReadingsFragment = AdditionalReadingsFragment()
            fragmentTransaction.replace(R.id.fragment_container, additionalReadingsFragment)
            fragmentTransaction.addToBackStack(null) // Optional: Add to back stack

            fragmentTransaction.commit()
        }

        //Links
        view.findViewById<TextView>(R.id.links_launcher).setOnClickListener {
            //Log.i(TAG, "onViewCreated: links_launcher: clicked")

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val linksFragment = LinksFragment()
            fragmentTransaction.replace(R.id.fragment_container, linksFragment)
            fragmentTransaction.addToBackStack(null) // Optional: Add to back stack

            fragmentTransaction.commit()
        }

        //About page:
        view.findViewById<TextView>(R.id.about_launcher).setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val bundle = Bundle()
            bundle.putChar("aboutOrBibleHelp", 'a')
            val aboutFragment = AboutFragment()
            aboutFragment.arguments = bundle
            fragmentTransaction.replace(R.id.fragment_container, aboutFragment)
            fragmentTransaction.addToBackStack(null) // Optional: Add to back stack

            fragmentTransaction.commit()
        }

        /**
         * links_launcher.setOnClickListener {
         *             // Handle button 1 click
         *
         *
         *             val fragmentManager = requireActivity().supportFragmentManager
         *             val fragmentTransaction = fragmentManager.beginTransaction()
         *
         *             val linksFragment = LinksFragment()
         *             fragmentTransaction.replace(R.id.fragment_container, linksFragment)
         *             fragmentTransaction.addToBackStack(null) // Optional: Add to back stack
         *
         *             fragmentTransaction.commit()
         *
         *
         *             //Toast.makeText(activity, "links_launcher clicked", Toast.LENGTH_SHORT).show()
         *         }
         */

        //Links: YouVersion explanation
        view.findViewById<TextView>(R.id.youversion_bible_settings_explanation).setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            val bundle = Bundle()
            bundle.putChar("aboutOrBibleHelp", 'b')
            val aboutFragment = AboutFragment()
            aboutFragment.arguments = bundle
            fragmentTransaction.replace(R.id.fragment_container, aboutFragment)
            fragmentTransaction.addToBackStack(null) // Optional: Add to back stack

            fragmentTransaction.commit()
        }


        val mysword_bible_setup = view.findViewById<TextView>(R.id.mysword_bible_setup)
        mysword_bible_setup.isEnabled = false


        /*mysword_bible_setup.setOnClickListener {
            // Handle button 2 click
            Toast.makeText(activity, "mysword_bible_setup clicked", Toast.LENGTH_SHORT).show()
        }*/

        // Set click listeners for buttons


        /*additional_readings_launcher.setOnClickListener {
            // Handle button 2 click
            Toast.makeText(activity, "additional_readings_launcher clicked", Toast.LENGTH_SHORT).show()
            // Example: Navigate to another fragment or activity
            /*val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, AnotherFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
             */
        }
         */
    }

}