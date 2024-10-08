package za.co.howtogeek.shabbatreadingsapplication.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import za.co.howtogeek.shabbatreadingsapplication.MainActivity
import za.co.howtogeek.shabbatreadingsapplication.R
import za.co.howtogeek.shabbatreadingsapplication.fragments.ShabbatDetailFragment
import za.co.howtogeek.shabbatreadingsapplication.objects.ShabbatReading
import za.co.howtogeek.shabbatreadingsapplication.readings5784.ReadingList_Fragment

//(private val itemList: ArrayList<Item>, private val onClickListener: OnClickListener) :
private val TAG = "za.co.howtogeek.shabbatreadingsapplication.adapters -> ShabbatReadingAdapter -> "

class ShabbatReadingAdapter(
    private val items: ArrayList<String>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<ShabbatReadingAdapter.ViewHolder>() {

    //Step 1: Define OnItemClickListener interface (in adapter) for onclicks:
    interface OnItemClickListener{
        fun onItemClick(position: Int)

    }

    //ViewHolder for ParashaName, takes in the inflated view and the onClick behavior.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView: TextView = itemView.findViewById(R.id.text_view)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    /* Creates and inflates view and return ViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_bookmark, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val shabbatReading = mList[position]
        Log.i("info","ShabbatRadingAdapter -> onBindViewHolder() -> mList.get($position)")

        //val ShabbatReadingHolder = items[position]
        holder.textView.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

}