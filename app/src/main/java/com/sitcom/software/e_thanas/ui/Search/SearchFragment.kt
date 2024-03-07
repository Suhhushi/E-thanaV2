package com.sitcom.software.e_thanas.ui.Search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sitcom.software.e_thanas.databinding.FragmentSearchBinding
//lol
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize ViewModel
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        // Find views
        val conditionalLayout: LinearLayout = binding.conditionalLayout
        val spinnerSexe: Spinner = binding.spinnerSexe

        spinnerSexe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                searchViewModel.handleSpinnerSelection(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle nothing selected
            }
        }

        // Observe field visibility LiveData
        searchViewModel.isFieldVisible.observe(viewLifecycleOwner, { isVisible ->
            conditionalLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}