package ch.cpnv.ymd.bmb.ui.newloan

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import ch.cpnv.ymd.bmb.R
import ch.cpnv.ymd.bmb.database.DatabaseHandler
import ch.cpnv.ymd.bmb.databinding.FragmentNewLoanBinding
import ch.cpnv.ymd.bmb.models.Book
import ch.cpnv.ymd.bmb.models.Loan
import ch.cpnv.ymd.bmb.utils.DropdownBookAdapter
import java.util.Calendar
import java.util.Date
import java.util.Locale


class NewLoanFragment : Fragment() {

    private var _binding: FragmentNewLoanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: DropdownBookAdapter;
    private lateinit var bookList: ArrayList<Book>
    private val CONTACT_PERMISSION_CODE = 1;
    private val CONTACT_PICK_CODE = 2;
    private var bookSelected: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(NewLoanViewModel::class.java)

        _binding = FragmentNewLoanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)

        val appBar = (requireActivity() as AppCompatActivity).supportActionBar
        appBar?.setDisplayHomeAsUpEnabled(true)

        buildDropDownList();


        val launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val contactUri = data?.data
                    Toast.makeText(requireContext(), contactUri.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }

        binding.edtContact.setOnClickListener {
            if (checkContactPermission()) {
                pickContact()
            } else {
                requestContactPermission()
            }
        }

        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController(requireParentFragment()).popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkContactPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactPermission() {

        val permission = arrayOf(android.Manifest.permission.READ_CONTACTS)
        ActivityCompat.requestPermissions(requireActivity(), permission, CONTACT_PERMISSION_CODE)
    }

    private fun pickContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, CONTACT_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickContact()
            } else {
                Toast.makeText(requireContext(), "Permission refusée", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()

        binding.btnCreateLoan.setOnClickListener {
            if (bookSelected!!.isNotEmpty() && binding.edtContact.text.toString()
                    .isNotEmpty() && binding.edtContact.text.toString() != "Choose a contact"
            ) {
                val calendar = Calendar.getInstance();
                calendar.set(binding.returnDatePicker.year, binding.returnDatePicker.month, binding.returnDatePicker.dayOfMonth)

                var loan = Loan(
                    binding.bookSpinner.selectedItem.toString(),
                    binding.edtContact.text.toString(),
                    calendar.timeInMillis
                )
                var db = DatabaseHandler(context);
                db.createLoan(loan);

                binding.bookSpinner.setSelection(0)
                binding.edtContact.text!!.clear()
                binding.returnDatePicker.isSelected = false

                view.findNavController().navigate(R.id.navigation_home);

            } else {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == CONTACT_PICK_CODE) {
            val cursor: Cursor?
            val contactUri = data?.data
            cursor =
                requireActivity().contentResolver.query(contactUri!!, null, null, null, null)!!
            if (cursor.moveToFirst()) {
                val contactName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                binding.edtContact.setText(contactName)
            }
        } else {
            Toast.makeText(requireContext(), "Error during picking contact", Toast.LENGTH_LONG)
                .show()
        }

    }

    private fun buildDropDownList() {
        bookList = ArrayList<Book>()

        var db = DatabaseHandler(requireContext());
        bookList = db.getAllBooks() as ArrayList<Book>;

        val spinner: Spinner = binding.bookSpinner
        adapter = DropdownBookAdapter(requireContext(), bookList)


        spinner.adapter = adapter;

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                bookSelected = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}