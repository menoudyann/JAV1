package ch.cpnv.ymd.bmb.utils

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import ch.cpnv.ymd.bmb.R
import ch.cpnv.ymd.bmb.models.Book
import com.bumptech.glide.Glide

class DropdownBookAdapter(val context: Context, var dataSource: List<Book>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val model: Book = dataSource[position]
        var vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.dropdown_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        vh.title.setText(model.title);
        vh.author.setText(model.author);
        vh.imageView
        Glide.with(this.context)
            .load(model.imageUrl)
            .into(vh.imageView)

        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val title: TextView
        val author: TextView
        val imageView: ImageView

        init {
            title = row?.findViewById(R.id.dropdown_title) as TextView
            author = row?.findViewById(R.id.dropdown_author) as TextView
            imageView = row?.findViewById(R.id.dropdown_image) as ImageView
        }
    }

    init {
        this.dataSource = dataSource;
    }

}