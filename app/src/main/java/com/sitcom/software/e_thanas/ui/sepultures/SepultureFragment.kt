package com.sitcom.software.e_thanas.ui.sepultures

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sitcom.software.e_thanas.R


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
                if (defunt.nomJeuneFille != null){
                    view.findViewById<TextView>(R.id.textViewNomJF).text = defunt.nomJeuneFille
                    view.findViewById<TextView>(R.id.textViewNomJF).isVisible = true
                }

                // Récupérer et afficher la liste des sépultures dans le logcat
                viewModel.getSepulture(requireContext())
                viewModel.sepulture.observe(viewLifecycleOwner, Observer { sepultures ->

                    val sepulture = sepultures.find { it.id == defunt.idSepulture}
                    Log.d("SepultureFragment", "La sépultures : $sepulture")

                    val coordX = sepulture?.coordX //pour la map
                    val coordY = sepulture?.coordY

                    // Récupérer et afficher la liste des cimetières dans le logcat
                    viewModel.getCimetieres(requireContext())
                    viewModel.cimetieres.observe(viewLifecycleOwner, Observer { cimetieres ->

                        val cimetiere = cimetieres.find { it.id == sepulture?.idCimetiere }

                        view.findViewById<TextView>(R.id.textViewVille).text = cimetiere?.ville
                        view.findViewById<TextView>(R.id.textViewCimetiere).text = cimetiere?.nom

                    })
                })
            } else {
                Log.e("SepultureFragment", "Défunt non trouvé avec l'ID $defuntId")
            }

        })

    }
}



