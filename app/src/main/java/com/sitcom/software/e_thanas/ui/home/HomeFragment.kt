package com.sitcom.software.e_thanas.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.databinding.FragmentHomeBinding
import com.sitcom.software.e_thanas.ui.home.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialiser le ViewModel
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Observer pour les données des cimetières
        homeViewModel.cimetieresLiveData.observe(viewLifecycleOwner, Observer { cimetieres ->
            // Afficher les données des cimetières dans le Logcat
            for (cimetiere in cimetieres) {
                Log.d("Cimetiere", "ID: ${cimetiere.id}, Nom: ${cimetiere.nom}, Rue: ${cimetiere.rue}, Ville: ${cimetiere.ville}, Code Postal: ${cimetiere.codePostal}")
            }
        })

        // Charger les données XML
        homeViewModel.loadXmlData(requireContext())

        binding.btnLocaliser.setOnClickListener {
            // Obtenir le NavController à partir de l'activity
            val navController = findNavController()

            // Naviguer vers le fragment de recherche
            findNavController().navigate(R.id.action_navigation_home_to_navigation_search)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
