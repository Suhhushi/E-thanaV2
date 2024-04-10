package com.sitcom.software.e_thanas.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sitcom.software.e_thanas.databinding.FragmentSearchBinding
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Cimetiere


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
                val adapter = ArrayAdapter(requireContext(), R.layout.color_spinner_layout, options)
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
                binding.spinnerSexe.adapter = adapter
            } else {
                // Gérer le cas où les données sont null ou vides
                Log.e("SearchFragment", "Options null or empty")
            }
        })

        // Observer pour surveiller les changements dans les noms des cimetières
        searchViewModel.getCimetiereName(requireContext()).observe(viewLifecycleOwner, Observer { cimetiereNames ->
            if (cimetiereNames != null && cimetiereNames.isNotEmpty()) {
                val adapter = ArrayAdapter(requireContext(), R.layout.color_spinner_layout, cimetiereNames)
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
                binding.spinnerCimetiere.adapter = adapter
            } else {
                // Gérer le cas où les données sont null ou vides
                Log.e("SearchFragment", "Cimetiere names null or empty")
            }
        })

        // Observer pour surveiller les changements dans les noms des cimetières
        searchViewModel.getCimetiereVille(requireContext()).observe(viewLifecycleOwner, Observer { cimetiereVille ->
            if (cimetiereVille != null && cimetiereVille.isNotEmpty()) {
                val adapter = ArrayAdapter(requireContext(), R.layout.color_spinner_layout, cimetiereVille)
                adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
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
                if(selectedItem == "Madame"){
                    binding.conditionalLayout.visibility = View.VISIBLE
                }else{
                    binding.conditionalLayout.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //ne rien faire
            }
        }

        binding.btnRechercher.setOnClickListener {
            val nom = searchViewModel.normalizeInput(binding.editTextNom.text.toString(), capitalizeFirstName = true)
            val prenom = searchViewModel.normalizeInput(binding.editTextPrenom.text.toString(), capitalizeFirstName = true)
            val genre = searchViewModel.normalizeInput(binding.spinnerSexe.selectedItem.toString(), capitalizeFirstName = true)
            val nomJF = searchViewModel.normalizeInput(binding.editTextNomJeuneFille.text.toString(), capitalizeFirstName = true)
            val cimetiere = searchViewModel.normalizeInput(binding.spinnerCimetiere.selectedItem.toString())

            // Compteur de champs saisis
            var fieldsFilledCount = 0

            // Vérifier si chaque champ est saisi
            if (nom.isNotEmpty()) fieldsFilledCount++
            if (prenom.isNotEmpty()) fieldsFilledCount++
            if (binding.spinnerSexe.selectedItemPosition != 0) fieldsFilledCount++
            if (nomJF.isNotEmpty()) fieldsFilledCount++
            if (binding.spinnerCimetiere.selectedItemPosition != 0) fieldsFilledCount++

            // Vérifier si au moins deux champs sont saisis
            if (fieldsFilledCount < 2) {
                // Afficher le pop-up indiquant à l'utilisateur de saisir au moins deux champs
                // Remplacez "requireContext()" par le context approprié pour afficher la popup
                // Vous pouvez utiliser AlertDialog ou Toast pour afficher le message
                // Exemple d'utilisation de Toast :
                Toast.makeText(requireContext(), "Veuillez saisir au moins deux champs", Toast.LENGTH_SHORT).show()
            } else {
                // Au moins deux champs sont saisis, naviguer vers la prochaine destination
                val bundle = Bundle()
                bundle.putString("nom", nom)
                bundle.putString("prenom", prenom)
                bundle.putString("genre", genre)
                bundle.putString("nomJF", nomJF)
                bundle.putString("Cimetiere", cimetiere)

                findNavController().navigate(R.id.action_navigation_search_to_listDefuntFragment, bundle)
            }
        }



        return root
    }

    override fun onResume() {
        super.onResume()
        // Effacer les champs de texte
        binding.editTextNom.text.clear()
        binding.editTextPrenom.text.clear()
        binding.editTextNomJeuneFille.text.clear()

        // Réinitialiser les sélections des spinners
        binding.spinnerSexe.setSelection(0)
        binding.spinnerCimetiere.setSelection(0)
        binding.spinnerVille.setSelection(0)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

