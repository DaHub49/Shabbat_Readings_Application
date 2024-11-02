package za.co.howtogeek.shabbatreadingsapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import za.co.howtogeek.shabbatreadingsapplication.R

class AboutFragment : Fragment() {
    /**
     * AboutFragment.java:
     * Author: Dylan Martin, George, South Africa
     *
     * AboutFragment displays some background info about the app via a string hardcoded into the view from strings.xml:
     *
     */
    private var aboutOrBibleHelp = 'a'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            val bundle = requireArguments()
            aboutOrBibleHelp = bundle.getChar("aboutOrBibleHelp", 'a')
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        val fragment_about_textview = view.findViewById<TextView>(R.id.fragment_about_textview)

        when (aboutOrBibleHelp) {
            'a' -> fragment_about_textview.setText(R.string.youversion_links)
            'b' -> fragment_about_textview.setText(R.string.bible_settings_string)
        }
        return view
    }
}