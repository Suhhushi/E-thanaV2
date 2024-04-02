package com.sitcom.software.e_thanas.ui.favoris

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sitcom.software.e_thanas.databinding.FragmentFavorisBinding
import androidx.lifecycle.Observer
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Defunt

class FavorisFragment : Fragment() {

    private var _binding: FragmentFavorisBinding? = null
    private val binding get() = _binding!!

    private lateinit var favorisViewModel: FavorisViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavorisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favorisViewModel = ViewModelProvider(this).get(FavorisViewModel::class.java)

        // Observer to listen for changes in the list of Enregistrements
        favorisViewModel.enregistrement.observe(viewLifecycleOwner, Observer { enregistrementList ->

            favorisViewModel.getEnregistrement(requireContext())

            favorisViewModel.defunts.observe(viewLifecycleOwner, Observer { defuntList ->

                val defuntsFiltres = mutableListOf<Defunt>()

                for (enregistrement in enregistrementList) {
                    val defuntFiltre = defuntList.find { defunt ->
                        enregistrement.idDefunt == defunt.id
                    }
                    defuntFiltre?.let {
                        defuntsFiltres.add(it)
                    }
                }

                Log.d("FavorisFragment", "Defunt : $defuntsFiltres")

            })

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
