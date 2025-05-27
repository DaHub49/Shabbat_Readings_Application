package za.co.howtogeek.shabbatreadingsapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import za.co.howtogeek.shabbatreadingsapplication.R



/**
 * MySwordSetupFragment.kt:
 * Author: Dylan Martin, South Africa
 *
 * MySwordSetupFragment displays basic instructions to setup the MySword Bible with the TS2009
 * and HiSB translations.
 *
 * 26/05: Consider adding steps to adding an actual Hebrew Bible in future releases.
 */

class MySwordSetupFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view: View = inflater.inflate(R.layout.fragment_my_sword_setup, container, false)
        return inflater.inflate(R.layout.fragment_my_sword_setup, container, false)
    }

}