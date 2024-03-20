package com.sitcom.software.e_thanas.ui.sepultures

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.FormData


/**
 * A simple [Fragment] subclass.
 * Use the [SepultureFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SepultureFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_sepulture, container, false)

            return view
        }

        fun onFormDataSubmitted(formData: FormData) {
            // Afficher les données du formulaire dans le fragment de destination
            val nomTextView = view?.findViewById<TextView>(R.id.textViewNom)
            nomTextView?.text = formData.nom

            val nomJFTextView = view?.findViewById<TextView>(R.id.textViewNomJF)
            nomJFTextView?.text = formData.nomJF

            val prenomTextView = view?.findViewById<TextView>(R.id.textViewPrenom)
            prenomTextView?.text = formData.prenom

            val villeTextView = view?.findViewById<TextView>(R.id.textViewVille)
            villeTextView?.text = formData.ville

            val cimetiereTextView = view?.findViewById<TextView>(R.id.textViewCimetiere)
            cimetiereTextView?.text = formData.cimetiere
        }
}