import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.cpnv.ymd.bmb.R
import ch.cpnv.ymd.bmb.models.Loan
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LoanAdapter(loanModelArrayList: ArrayList<Loan>) :
    RecyclerView.Adapter<LoanAdapter.ViewHolder>() {
    private var loanModelArrayList: ArrayList<Loan>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.loan_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Loan = loanModelArrayList[position]
        holder.book.setText(model.book);
        holder.contact.setText("Loaned to " + model.contact);
        val format = SimpleDateFormat("dd-MM-yyyy")
        val date = Date(model.return_at)
        holder.return_at.setText("Return at " + format.format(date));

    }

    override fun getItemCount(): Int {
        return loanModelArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var book: TextView
        lateinit var contact: TextView
        lateinit var return_at: TextView

        init {
            book = itemView.findViewById(R.id.title)
            contact = itemView.findViewById(R.id.contact)
            return_at = itemView.findViewById(R.id.return_at)
        }
    }

    init {
        this.loanModelArrayList = loanModelArrayList;
    }
}