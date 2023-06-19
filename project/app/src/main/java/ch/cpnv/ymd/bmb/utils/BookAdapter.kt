import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.cpnv.ymd.bmb.R
import ch.cpnv.ymd.bmb.models.Book
import com.bumptech.glide.Glide

class BookAdapter(bookModelArrayList: ArrayList<Book>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {
    private var bookModelArrayList: ArrayList<Book>

    fun filterList(filterList: ArrayList<Book>) {
        bookModelArrayList = filterList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.book_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Book = bookModelArrayList[position]
        holder.title.setText(model.title);
        holder.author.setText(model.author);
        holder.isbn.setText(model.isbn);
        Glide.with(holder.itemView.context)
            .load(model.imageUrl)
            .into(holder.itemView.findViewById(R.id.card_image_loan))
    }

    override fun getItemCount(): Int {
        return bookModelArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var title: TextView
        lateinit var author: TextView
        lateinit var isbn: TextView

        init {
            title = itemView.findViewById(R.id.title)
            author = itemView.findViewById(R.id.contact)
            isbn = itemView.findViewById(R.id.return_at)
        }
    }

    init {
        this.bookModelArrayList = bookModelArrayList;
    }
}