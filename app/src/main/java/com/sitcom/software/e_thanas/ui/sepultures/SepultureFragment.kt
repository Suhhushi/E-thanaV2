package com.sitcom.software.e_thanas.ui.sepultures

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Defunt
import com.sitcom.software.e_thanas.ui.main.ListDefuntViewModel


class SepultureFragment : Fragment() {

    private lateinit var viewModel: SepultureViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sepulture, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisez votre ViewModel ici
        viewModel = ViewModelProvider(this).get(SepultureViewModel::class.java)

        viewModel.getDefunts(requireContext())

        // Observer pour écouter les changements dans la liste des défunts
        viewModel.defunts.observe(viewLifecycleOwner, Observer { defunts ->
            // Récupérer l'ID du défunt depuis les arguments
            val defuntId = arguments?.getInt("id_defunt")

            Log.d("SepultureFragment", "ID du défunt: $defuntId")

            // Récupérer le défunt correspondant à l'ID
            val defunt = defunts.find { it.id == defuntId }

            if (defunt != null) {
                // Afficher les détails du défunt dans le logcat
                Log.d("SepultureFragment", "Défunt trouvé : $defunt")

                view.findViewById<TextView>(R.id.textViewNom).text = defunt.nom
                view.findViewById<TextView>(R.id.textViewPrenom).text = defunt.prenom
            } else {
                Log.e("SepultureFragment", "Défunt non trouvé avec l'ID $defuntId")
            }

        })
    }
}



