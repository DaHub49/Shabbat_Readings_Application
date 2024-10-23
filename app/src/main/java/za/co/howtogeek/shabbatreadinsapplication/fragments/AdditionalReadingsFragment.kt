package za.co.howtogeek.shabbatreadingsapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import za.co.howtogeek.shabbatreadinsapplication.R

class AdditionalReadingsFragment : Fragment() {

    private val TAG ="AdditionalReadingsFragment -> "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "AdditionalReadingsFragment -> onCreate: called")
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i(TAG, "AdditionalReadingsFragment -> onCreateView: called")
        return inflater.inflate(R.layout.fragment_additional_readings, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdditionalReadingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdditionalReadingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}