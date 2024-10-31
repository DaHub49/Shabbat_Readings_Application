package za.co.howtogeek.shabbatreadinsapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.co.howtogeek.shabbatreadinsapplication.R

class LinksAdapter(data: ArrayList<String>, context: Context) :
    RecyclerView.Adapter<LinksAdapter.LinksViewHolder?>() {
    /**
     * MyAdapter.java:
     * Author: Dylan Martin, George, South Africa
     *
     * LinksAdapter binds the ArrayList<String> of links to an item of a RecyclerView.
     *
     * The arguments are:
     * 1. the List<String> of various links.
    </String></String> */
    private val TAG = "LinksAdapter ->"
    var mContext: Context

    init {
        mData = data
        mContext = context
        var index = 0
        //Log.i(TAG, "LinksAdapter -> constructor()");
        for (line in data) {
            //Log.i(TAG, "LinksAdapter -> data: "+ data.get(index));
            index++
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinksViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_bookmark, parent, false)
        val linksViewHolder = LinksViewHolder(view)
        return linksViewHolder
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(
        holder: LinksViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        if (!mData.isEmpty()) {
            //String item = mData.get(position);
            //holder.bind(item, position);
            holder.parasha_name_text_view.text = mData[position]
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            when (position) {
                0 -> andrewHodkinsonYoutube()
                1 -> bethTikkunAudioLinks()
                2 -> bethTikkunYoutube()
                3 -> theCreationGospel()
                4 -> drHollisaAlewineYoutube()
                5 -> ffoz()
                6 -> ffozYoutube()
                7 -> firm()
                8 -> firmYoutube()
                9 -> grantLutonYoutube()
                10 -> torahTodayMinistries()
            }
        })
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    inner class LinksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // each data item is just a string in this case
        var parasha_name_text_view: TextView =
            itemView.findViewById<TextView>(R.id.parasha_name_text_view)
    }

    //Methods to launch link intents:
    fun andrewHodkinsonYoutube() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@andrewhodkinson1"))
        mContext.startActivity(intent)
    }

    fun bethTikkunAudioLinks() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://bethtikkun.com/category/media/")
        ) //https://bethtikkun.com/category/audio/"));
        mContext.startActivity(intent)
    }

    fun bethTikkunYoutube() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@bethtikkunmessianic"))
        mContext.startActivity(intent)
    }

    fun theCreationGospel() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.thecreationgospel.com/"))
        mContext.startActivity(intent)
    }

    fun drHollisaAlewineYoutube() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@thecreationgospel"))
        mContext.startActivity(intent)
    }

    fun ffoz() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ffoz.org/"))
        mContext.startActivity(intent)
    }

    fun ffozYoutube() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@firstfruitsofzion"))
        mContext.startActivity(intent)
    }

    fun firm() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://firmisrael.org/"))
        mContext.startActivity(intent)
    }

    fun firmYoutube() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@FIRMisrael"))
        mContext.startActivity(intent)
    }

    fun grantLutonYoutube() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@grantluton"))
        mContext.startActivity(intent)
    }

    private fun torahTodayMinistries() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://torahtodayministries.org/"))
        mContext.startActivity(intent)
    }

    companion object {
        private var mData = ArrayList<String>()
    }
}
