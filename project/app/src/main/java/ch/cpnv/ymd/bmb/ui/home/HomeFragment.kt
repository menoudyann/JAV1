package ch.cpnv.ymd.bmb.ui.home

import LoanAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.cpnv.ymd.bmb.R
import ch.cpnv.ymd.bmb.database.DatabaseHandler
import ch.cpnv.ymd.bmb.databinding.FragmentHomeBinding
import ch.cpnv.ymd.bmb.models.Book
import ch.cpnv.ymd.bmb.models.Loan
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var loanRV: RecyclerView
    private lateinit var adapter: LoanAdapter
    private lateinit var loanList: ArrayList<Loan>
    private lateinit var btnNewLoan: FloatingActionButton;


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loanRV = binding.idRVLoans
        buildRecyclerView()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNewLoan= binding.btnNewLoan

        binding.btnNewLoan.setOnClickListener {
            view.findNavController().navigate(R.id.navigation_new_loan)
        }

    }

    private fun buildRecyclerView() {
        // below line we are creating a new array list
        loanList = ArrayList<Loan>()

        var db = DatabaseHandler(requireContext());
        loanList = db.getAllLoans() as ArrayList<Loan>;

        // initializing our adapter class.
        adapter = LoanAdapter(loanList)

        // adding layout manager to our recycler view.
        val manager = LinearLayoutManager(requireContext())
        loanRV.setHasFixedSize(true)

        // setting layout manager
        // to our recycler view.
        loanRV.layoutManager = manager

        // setting adapter to
        // our recycler view.

        binding.loanNumber.setText(loanList.size.toString());
        loanRV.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}