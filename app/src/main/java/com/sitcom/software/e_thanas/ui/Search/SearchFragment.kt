package com.sitcom.software.e_thanas.ui.Search

import android.R
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sitcom.software.e_thanas.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var searchViewModel: SearchViewModel

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialisation de la ViewModel
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        // Observer pour surveiller les changements dans les données
        searchViewModel.getData().observe(viewLifecycleOwner, Observer { options ->
            if (options != null && options.isNotEmpty()) {
                val adapter = object : ArrayAdapter<String>(
                    requireContext(),
                    R.layout.my_spinner_style,
                    options
                ) {
                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val v = super.getView(position, convertView, parent)
                        (v as TextView).textSize = 16.toFloat()
                        return v
                    }

                    override fun getDropDownView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val v = super.getDropDownView(position, convertView, parent)
                        (v as TextView).gravity = Gravity.CENTER
                        return v
                    }
                }
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.spinnerSexe.adapter = adapter
            } else {
                // Gérer le cas où les données sont null ou vides
                Log.e("SearchFragment", "Options null or empty")
            }
        })


        binding.spinnerSexe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if(selectedItem == "Femme"){
                    binding.conditionalLayout.visibility = View.VISIBLE
                }else{
                    binding.conditionalLayout.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //ne rien faire
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
