package com.sitcom.software.e_thanas.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sitcom.software.e_thanas.R

class ListDefuntFragment : Fragment() {

    private lateinit var viewModel: ListDefuntViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listdefunt, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialisez votre ViewModel ici
        viewModel = ViewModelProvider(this).get(ListDefuntViewModel::class.java)

        // Récupérer les données passées par le Bundle
        val cimetiere = arguments?.getString("cimetiere")
        val ville = arguments?.getString("ville")
        val nom = arguments?.getString("nom")
        val prenom = arguments?.getString("prenom")

        // Utilisez les données récupérées comme vous le souhaitez
        Log.d("ListDefuntFragment", "Cimetiere: $cimetiere, Ville: $ville, Nom: $nom, Prenom: $prenom")

        // Mettez en place votre RecyclerView ou d'autres éléments de votre fragment ici
        // Trouvez le bouton de retour par son ID
        val backButton: ImageButton = view.findViewById(R.id.btnBack)

        // Définissez le OnClickListener pour le bouton de retour
        backButton.setOnClickListener {
            // Appel de la fonction onBackButtonClicked lorsque le bouton est cliqué
            onBackButtonClicked(it)
        }
    }

    // La fonction pour gérer le clic sur le bouton de retour
    fun onBackButtonClicked(view: View) {
        findNavController().navigateUp()
    }
}
