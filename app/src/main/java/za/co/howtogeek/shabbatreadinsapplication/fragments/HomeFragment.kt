package za.co.howtogeek.shabbatreadingsapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import za.co.howtogeek.shabbatreadinsapplication.R
import za.co.howtogeek.shabbatreadinsapplication.readings5785.ReadingList_Fragment

class HomeFragment : Fragment() {

    private val TAG = "za.co.howtogeek.shabbatreadingsapplication.fragments -> HomeFragment -> "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("info", "HomeFragment -> onCreate()")
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

        // Accessing TextView buttons from the fragment layout
        view.findViewById<TextView>(R.id.ffoz_launcher).setOnClickListener {
            Log.i(TAG, "onViewCreated: ffozLauncher: clicked")

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val readingList_Fragment = ReadingList_Fragment()
            fragmentTransaction.replace(R.id.fragment_container, readingList_Fragment)
            fragmentTransaction.addToBackStack(null) // Optional: Add to back stack

            fragmentTransaction.commit()
        }

        view.findViewById<TextView>(R.id.additional_readings_launcher).setOnClickListener {
            Log.i(TAG, "onViewCreated: additionalReadingsLauncher: clicked")

            val fragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val additionalReadingsFragment = AdditionalReadingsFragment()
            fragmentTransaction.replace(R.id.fragment_container, additionalReadingsFragment)
            fragmentTransaction.addToBackStack(null) // Optional: Add to back stack

            fragmentTransaction.commit()
        }
        val links_launcher = view.findViewById<TextView>(R.id.links_launcher)
        val about_launcher = view.findViewById<TextView>(R.id.about_launcher)
        val youversion_bible_settings_explanation = view.findViewById<TextView>(R.id.youversion_bible_settings_explanation)
        val mysword_bible_setup = view.findViewById<TextView>(R.id.mysword_bible_setup)

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

        links_launcher.setOnClickListener {
            // Handle button 1 click
            Toast.makeText(activity, "links_launcher clicked", Toast.LENGTH_SHORT).show()
        }

        about_launcher.setOnClickListener {
            // Handle button 2 click
            Toast.makeText(activity, "about_launcher clicked", Toast.LENGTH_SHORT).show()
        }

        youversion_bible_settings_explanation.setOnClickListener {
            // Handle button 1 click
            Toast.makeText(activity, "youversion_bi0ble_settings_explanation clicked", Toast.LENGTH_SHORT).show()
            //findNavController().navigate(R.id.action_homeFragment_to_settingsActivity)
        }

        mysword_bible_setup.setOnClickListener {
            // Handle button 2 click
            Toast.makeText(activity, "mysword_bible_setup clicked", Toast.LENGTH_SHORT).show()
        }
    }

}