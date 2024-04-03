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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnLocaliser.setOnClickListener {

            binding.btnLocaliser.isEnabled = false
            // Naviguer vers le fragment de recherche
            findNavController().navigate(R.id.action_navigation_home_to_navigation_search)
            //Réinitialiser le bouton une fois cliqué pour pouvoir revenir à la page
            binding.btnLocaliser.isEnabled = false

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
