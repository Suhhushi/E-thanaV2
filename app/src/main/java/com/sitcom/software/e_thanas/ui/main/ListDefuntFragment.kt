package com.sitcom.software.e_thanas.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.ui.main.listDefuntViewModel

class ListDefuntFragment : Fragment() {

    private lateinit var viewModel: listDefuntViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_listdefunt, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialisez votre ViewModel ici
        viewModel = ViewModelProvider(this).get(listDefuntViewModel::class.java)

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
