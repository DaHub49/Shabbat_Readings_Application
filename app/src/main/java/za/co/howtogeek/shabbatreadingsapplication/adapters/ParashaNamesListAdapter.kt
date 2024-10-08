package za.co.howtogeek.shabbatreadingsapplication.adapters

/*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import za.co.howtogeek.shabbatreadingsapplication.R
import za.co.howtogeek.shabbatreadingsapplication.objects.ShabbatReading

class ParashaNamesListAdapter (private val textList: List<String>, private val onClick: (ShabbatReading) -> Unit) :
    ListAdapter<ShabbatReading, ParashaNamesListAdapter.ParashaNamesViewHolder>(ShabbatReadingDiffCallback) {

    /* ViewHolder for ShabbatReading, takes in the inflated view and the onClick behavior */
    class ParashaNamesViewHolder(itemView: View, val onClick: (ShabbatReading) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val shabbatReadingTextView: TextView = itemView.findViewById(R.id.card_view_text_view)
        private var currentShabbatReading: ShabbatReading? = null

        init {
            itemView.setOnClickListener{
                currentShabbatReading?.let {
                    onClick(it)
                }
            }
        }

        // Bind ShabbatReading title
        fun bind(shabbatReading: ShabbatReading){
            currentShabbatReading = shabbatReading

            shabbatReadingTextView.text = shabbatReading.parashaName
            //Checking image or ! and setting image...
        }
    }

    /* Creates and inflates view and returns ShabbatReadingViewHolder */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShabbatReadingAdapter.ShabbatReadingViewHolder { //CHECK HERE IF PROBLEMS!!
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_bookmark, parent, false) //.item_text, parent, false)
        return ShabbatReadingAdapter.ShabbatReadingViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ShabbatReadingAdapter.ShabbatReadingViewHolder, position: Int) {
        val shabbatReading = getItem(position)
        holder.bind(shabbatReading)

        /* [Old]:
        val text = textList[position]
        holder.shabbatReadingTextView.text = text
         */
    }

    override fun getItemCount(): Int {
        return textList.size
    }
}
object ShabbatReadingDiffCallback : DiffUtil.ItemCallback<ShabbatReading>() {
    override fun areItemsTheSame(oldItem: ShabbatReading, newItem: ShabbatReading): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ShabbatReading, newItem: ShabbatReading): Boolean {
        return oldItem.id == newItem.id
    }
}
 */