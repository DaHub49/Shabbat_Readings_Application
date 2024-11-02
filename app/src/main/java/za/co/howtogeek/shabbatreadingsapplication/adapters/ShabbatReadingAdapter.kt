package za.co.howtogeek.shabbatreadingsapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.co.howtogeek.shabbatreadingsapplication.R

//(private val itemList: ArrayList<Item>, private val onClickListener: OnClickListener) :
private val TAG = "za.co.howtogeek.shabbatreadingsapplication.adapters -> ShabbatReadingAdapter -> "

class ShabbatReadingAdapter(
    //private val parentFragment: Int, //0 for FFOZ, 1 for Additional Readings
    private val context: Context,
    private var parashaNames: ArrayList<String>,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<ShabbatReadingAdapter.ViewHolder>() {

    //Step 1: Define OnItemClickListener interface (in adapter) for onclicks:
    interface OnItemClickListener{
        fun onItemClick(position: Int)

    }

    //ViewHolder for ParashaName, takes in the inflated view and the onClick behavior.
    class ViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.parasha_name_text_view)

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

        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val shabbatReading = mList[position]
        //Log.i("info","ShabbatRadingAdapter -> onBindViewHolder() -> mList.get($position)")

        //val ShabbatReadingHolder = parashaNames[position]
        holder.textView.text = parashaNames[position]
    }

    override fun getItemCount(): Int {
        return parashaNames.size
    }

    fun updateList(newParashaNames: ArrayList<String>) {
        parashaNames = newParashaNames
        notifyDataSetChanged()
    }

}