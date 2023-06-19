package ch.cpnv.ymd.bmb.ui.books

import BookAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.cpnv.ymd.bmb.R
import ch.cpnv.ymd.bmb.database.DatabaseHandler
import ch.cpnv.ymd.bmb.databinding.FragmentBooksBinding
import ch.cpnv.ymd.bmb.models.Book
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Locale


class BooksFragment : Fragment() {

    private var _binding: FragmentBooksBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var btnAddBook: FloatingActionButton;

    private lateinit var bookRV: RecyclerView

    // class and array list
    private lateinit var adapter: BookAdapter
    private lateinit var bookList: ArrayList<Book>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(BooksViewModel::class.java)

        _binding = FragmentBooksBinding.inflate(inflater, container, false)

        bookRV = binding.idRVBooks
        buildRecyclerView()

        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddBook = binding.btnAddBook
        val searchView = binding.searchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })


        binding.btnAddBook.setOnClickListener {
            view.findNavController().navigate(R.id.navigation_add_book)
        }
    }

    private fun filter(text: String) {
        val filteredlist = ArrayList<Book>()

        for (item in bookList) {
            if (item.title.contains(text.lowercase(Locale.getDefault()))) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(requireContext(), "No Book Found..", Toast.LENGTH_SHORT).show()
        } else {
            binding.bookNumber.setText(filteredlist.size.toString());
            adapter.filterList(filteredlist)
        }
    }

    private fun buildRecyclerView() {

        // below line we are creating a new array list
        bookList = ArrayList<Book>()

        var db = DatabaseHandler(requireContext());
        bookList = db.getAllBooks()as ArrayList<Book>;

        // initializing our adapter class.
        adapter = BookAdapter(bookList)

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(requireContext())
        bookRV.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        bookRV.layoutManager = manager

        // setting adapter to
        // our recycler view.
        binding.bookNumber.setText(bookList.size.toString());
        bookRV.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}