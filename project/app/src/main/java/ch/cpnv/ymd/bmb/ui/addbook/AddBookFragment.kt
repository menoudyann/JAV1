package ch.cpnv.ymd.bmb.ui.books

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import ch.cpnv.ymd.bmb.R
import ch.cpnv.ymd.bmb.database.DatabaseHandler
import ch.cpnv.ymd.bmb.databinding.FragmentAddBookBinding
import ch.cpnv.ymd.bmb.models.Book
import ch.cpnv.ymd.bmb.ui.addbook.AddBookViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AddBookFragment : Fragment() {

    private var _binding: FragmentAddBookBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var btnAddBook: FloatingActionButton
    private lateinit var imageView: ImageView
    private lateinit var url: String
    private lateinit var edt_url: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(AddBookViewModel::class.java)

        _binding = FragmentAddBookBinding.inflate(inflater, container, false)

        val root: View = binding.root

        edt_url = binding.editTextImageURL
        imageView = binding.imageView

        edt_url.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                url = edt_url.text.toString()
                Log.e("URL", url)
                Glide.with(requireContext())
                    .load(url)
                    .into(imageView);
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // nothing
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                // nothing
            }
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        binding.btnInsertBook.setOnClickListener {
            if (binding.editTextTitle.text.toString()
                    .isNotEmpty() && binding.editTextAuthor.text.toString()
                    .isNotEmpty() && binding.editTextISBN.text.toString()
                    .isNotEmpty() && binding.editTextImageURL.text.toString().isNotEmpty()
            ) {
                var book = Book(
                    binding.editTextTitle.text.toString(),
                    binding.editTextAuthor.text.toString(),
                    binding.editTextISBN.text.toString(),
                    binding.editTextImageURL.text.toString()
                )
                var db = DatabaseHandler(context);
                db.insertBook(book);

                binding.editTextTitle.text!!.clear()
                binding.editTextAuthor.text!!.clear()
                binding.editTextISBN.text!!.clear()
                binding.editTextImageURL.text!!.clear()

                view.findNavController().navigate(R.id.navigation_books);
            } else {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}