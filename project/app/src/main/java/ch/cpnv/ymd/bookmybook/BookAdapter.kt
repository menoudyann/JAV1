package ch.cpnv.ymd.bookmybook

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView


class BookAdapter(private val context: Context, courseModelArrayList: ArrayList<BookModel>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    private val courseModelArrayList: ArrayList<BookModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookAdapter.ViewHolder, position: Int) {
        // to set data to textview and imageview of each card layout
        val model: BookModel = courseModelArrayList[position]
        holder.bookNameTV.setText(model.getTitle())
        holder.bookIV.setImageResource(model.getCover())
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return courseModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookIV: ImageView
        val bookNameTV: TextView

        init {
            bookIV = itemView.findViewById(R.id.idIVBookImage)
            bookNameTV = itemView.findViewById(R.id.idTVBookName)
        }
    }

    // Constructor
    init {
        this.courseModelArrayList = courseModelArrayList
    }
}
