package za.co.howtogeek.shabbatreadinsapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import za.co.howtogeek.shabbatreadinsapplication.R

class YouVersionTranslationsAdapter(
    private val context: Context,
    private val listener: OnItemClickListener
): RecyclerView.Adapter<YouVersionTranslationsAdapter.ViewHolder>() {

    private var translations: Array<String> = context.resources.getStringArray(R.array.youversion_translations)
    //private var translationsArrayList = ArrayList(translations.asList())

    //Step 1: Define OnItemClickListener interface (in adapter) for onclicks:
    interface OnItemClickListener{
        //fun onItemClick(position: Int)

        @SuppressLint("NotConstructor")
        fun OnItemClickListener(position: Int)
    }

    //ViewHolder for translation, takes in the inflated view and the onClick behavior.
    class ViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.parasha_name_text_view)

        init {
            itemView.setOnClickListener {
                listener.OnItemClickListener(adapterPosition)
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
        //Log.i("info", "YouVersionTranslationsAdapter -> onBindViewHolder() -> mList.get($position)")

        //val ShabbatReadingHolder = parashaNames[position]
        holder.textView.text = translations[position]
    }

    override fun getItemCount(): Int {
        return translations.size
    }

    fun updateList(newParashaNames: Array<String>) {
        translations = newParashaNames
        notifyDataSetChanged()
    }

    /*

    fun setOnTranslationClickListener(listener: YouVersionTranslationFragment.OnTranslationClickListener) {
        this.listener = listener
    }
     */

    /*inner class TranslationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onTranslationClick(translations[position])
                }
            }
        }

        // ... (Rest of your ViewHolder code) ...
    }
     */

}