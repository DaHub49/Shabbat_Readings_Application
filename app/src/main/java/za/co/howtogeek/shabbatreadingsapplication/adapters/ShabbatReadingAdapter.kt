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

    //val mParashaName: ArrayList<String> = parashaNames
    //private var listener: OnItemClickListener = listener
    //private lateinit var mParashaNames: MutableList<String>

    //Step 1: Define OnItemClickListener interface (in adapter) for onclicks:
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    //ViewHolder for ParashaName, takes in the inflated view and the onClick behavior.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val card_view: CardView = itemView.findViewById(R.id.card_view)
        val textView: TextView = itemView.findViewById(R.id.text_view)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    /**
     * override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     *         val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
     *         return ViewHolder(view)
     *     }
     */

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

        /*holder.itemView.findViewById<TextView>(R.id.text_view).text = items.get(position)
        holder.itemView.setOnClickListener {
            items.get() //.onItemClick(ShabbatReadingHolder)
         */

            /**
             * Using arguments in navcontroller:
             *
             * val action = YourSourceFragmentDirections.actionYourSourceFragmentToYourDestinationFragment(argValue)
             * findNavController().navigate(action)
             */

        /*
            Log.i(TAG, "onBindViewHolder -> position: $position")
            findNavController().navigate
            //val action = ReadingList_Fragment//.action_ReadingListFragment_to_ShabbatDetailFragment //.action_readingList_Fragment_to_shabbat_detail_fragment()
            //navController.navigate(R.id.action_readingList_Fragment_to_shabbat_detail_fragment)//R.id.action_home_to_readingList)

         */
            /**
             * binding.febAdd.setOnClickListener {
             *    findNavController().navigate(R.id.action_mobile_navigation_to_addNoteFragment)
             * }
             */
    }

    /* Gets current shabbatReading and uses it to bind view.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shabbatReading = mList[position]
        Log.i("info","ShabbatRadingAdapter -> onBindViewHolder() -> parashaNames.get($position)")

        holder.itemView.findViewById<TextView>(R.id.text_view).text = shabbatReading.parashaName

        /*
        Still to implement after general recyclerview is working:
        //handle item click:
        holder.itemView.setOnClickListener {
            listener.onItemClick(ItemsViewModel)
        }
         */
    }

     */

    /* To populate ArrayList?:
    fun populateArrayList(parashaNames: MutableList<String>) {
        mParashaNames = parashaNames

        for (line in parashaNames) {
            Log.i(TAG, "ShabbatReadingAdapter -> populateArrayList(copied a.l.) -> for: " + line)
        }
    }
     */

    override fun getItemCount(): Int {
        return items.size
    }
    /*

    object ShabbatDiffCallback : DiffUtil.ItemCallback<ShabbatReading>() {
        override fun areItemsTheSame(oldItem: ShabbatReading, newItem: ShabbatReading): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ShabbatReading, newItem: ShabbatReading): Boolean {
            return oldItem.parashaName == newItem.parashaName
        }
    }
     */
}