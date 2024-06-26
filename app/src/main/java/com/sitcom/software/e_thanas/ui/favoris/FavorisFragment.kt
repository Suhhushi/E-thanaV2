package com.sitcom.software.e_thanas.ui.favoris

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.sitcom.software.e_thanas.databinding.FragmentFavorisBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Defunt

class FavorisFragment : Fragment() {

    private var _binding: FragmentFavorisBinding? = null
    private val binding get() = _binding!!

    private lateinit var favorisViewModel: FavorisViewModel

    private var defuntId : Int = 0

    private var defuntsEnregistrer : MutableList<Defunt> = mutableListOf() // Initialize here

    private lateinit var favorisAdapter: FavorisAdapter


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

        favorisViewModel.getEnregistrement(requireContext())

        // Observer to listen for changes in the list of Enregistrements
        favorisViewModel.enregistrement.observe(viewLifecycleOwner, Observer { enregistrementList ->

            Log.d("FavorisFragment", "Defunt : $enregistrementList")

            favorisViewModel.getDefunts(requireContext())

            favorisViewModel.defunts.observe(viewLifecycleOwner, Observer { defuntList ->

                defuntsEnregistrer.clear() // Nettoyer la liste avant d'ajouter de nouveaux éléments

                for (enregistrement in enregistrementList) {
                    defuntId = enregistrement.idDefunt
                    if (defuntId != 0) {
                        for (defunt in defuntList) {
                            if (defunt.id == defuntId) {
                                defuntsEnregistrer.add(defunt)
                            }
                        }
                    }
                }


                favorisAdapter = FavorisAdapter(defuntsEnregistrer)

                val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = favorisAdapter

            })

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
