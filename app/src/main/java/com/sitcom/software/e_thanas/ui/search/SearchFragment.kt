package com.sitcom.software.e_thanas.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sitcom.software.e_thanas.databinding.FragmentSearchBinding
import com.sitcom.software.e_thanas.R



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


        // Initialiser la ViewModel
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        // Observer pour surveiller les changements dans les données
        searchViewModel.getData().observe(viewLifecycleOwner, Observer { options ->
            if (options != null && options.isNotEmpty()) {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerSexe.adapter = adapter
            } else {
                // Gérer le cas où les données sont null ou vides
                Log.e("SearchFragment", "Options null or empty")
            }
        })

        // Observer pour surveiller les changements dans les noms des cimetières
        searchViewModel.getCimetiereName(requireContext()).observe(viewLifecycleOwner, Observer { cimetiereNames ->
            if (cimetiereNames != null && cimetiereNames.isNotEmpty()) {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cimetiereNames)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCimetiere.adapter = adapter
            } else {
                // Gérer le cas où les données sont null ou vides
                Log.e("SearchFragment", "Cimetiere names null or empty")
            }
        })

        // Observer pour surveiller les changements dans les noms des cimetières
        searchViewModel.getCimetiereVille(requireContext()).observe(viewLifecycleOwner, Observer { cimetiereVille ->
            if (cimetiereVille != null && cimetiereVille.isNotEmpty()) {
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, cimetiereVille)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerVille.adapter = adapter

            } else {
                // Gérer le cas où les données sont null ou vides
                Log.e("SearchFragment", "Cimetiere ville null or empty")
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

        // Dans SearchFragment, dans la méthode onCreateView ou ailleurs approprié
        // Supposons que vous avez un bouton nommé btnSearch dans votre layout XML

        binding.btnRechercher.setOnClickListener {
            // Lorsque l'utilisateur appuie sur le bouton de recherche
            val cimetiereSelected = binding.spinnerCimetiere.selectedItem.toString()
            val villeSelected = binding.spinnerVille.selectedItem.toString()
            val nom = binding.editTextNom.text.toString()
            val prenom = binding.editTextPrenom.text.toString()

            val bundle = Bundle()
            bundle.putString("cimetiere", cimetiereSelected)
            bundle.putString("ville", villeSelected)
            bundle.putString("nom", nom)
            bundle.putString("prenom", prenom)

            findNavController().navigate(R.id.action_navigation_search_to_listDefuntFragment, bundle)
        }



        return root
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

