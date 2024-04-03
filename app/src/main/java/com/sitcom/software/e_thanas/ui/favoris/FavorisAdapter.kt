package com.sitcom.software.e_thanas.ui.favoris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Defunt

class FavorisAdapter(private val defuntList: List<Defunt>) : RecyclerView.Adapter<FavorisAdapter.DefuntViewHolder>() {

    inner class DefuntViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val defunt = defuntList[adapterPosition]
                val bundle = Bundle().apply {
                    putInt("id_defunt", defunt.id) // ID du défunt sélectionné
                    // Ajoutez d'autres données du défunt à passer si nécessaire
                }
                itemView.findNavController().navigate(R.id.action_navigation_favoris_to_navigation_sepulture2, bundle)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefuntViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_enregistrer, parent, false)
        return DefuntViewHolder(view)
    }

    override fun onBindViewHolder(holder: DefuntViewHolder, position: Int) {
        val defunt = defuntList[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.nameText)?.text = defunt.nom
            findViewById<TextView>(R.id.firstNameTextView1)?.text = defunt.prenom
            findViewById<TextView>(R.id.locationTextView1)?.text = defunt.dateDeces
        }
    }

    override fun getItemCount() = defuntList.size
}
