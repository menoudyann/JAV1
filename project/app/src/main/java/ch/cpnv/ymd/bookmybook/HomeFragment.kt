package ch.cpnv.ymd.bookmybook

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : AppCompatActivity() {

    private lateinit var bookList: List<BookModel>;
    private lateinit var searchView: SearchView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText);
                return false;
            }

        });

        val bookRV = findViewById<RecyclerView>(R.id.idRVBook);
        bookList = ArrayList<BookModel>();

        (bookList as ArrayList<BookModel>).add(BookModel(R.drawable.petitpays, "Livre 1", "978-345-134", "Gael Faye"));
        (bookList as ArrayList<BookModel>).add(BookModel(R.drawable.petitpays, "Livre 2", "978-345-134", "Gael Faye"));
        (bookList as ArrayList<BookModel>).add(BookModel(R.drawable.petitpays, "Livre 3", "978-345-134", "Gael Faye"));
        (bookList as ArrayList<BookModel>).add(BookModel(R.drawable.petitpays, "Livre 4", "978-345-134", "Gael Faye"));

        val numberOfBooks = findViewById<TextView>(R.id.txt_number_loans);
        numberOfBooks.text = bookList.size.toString();

        val bookAdapter = BookAdapter(this, bookList);

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        bookRV.layoutManager = linearLayoutManager;
        bookRV.adapter = bookAdapter;

    }

    private fun filterList(text: String?) {
        val filteredList = ArrayList<Item>();
        for (item in filteredList) {
            if (item.toString().toLowerCase().contains(text.toString().toLowerCase())) {
                filteredList.add(item);
            }
        }


    }
}