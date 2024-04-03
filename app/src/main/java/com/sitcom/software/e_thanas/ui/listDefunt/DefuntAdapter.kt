package com.sitcom.software.e_thanas.ui.listDefunt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Defunt

class DefuntAdapter(private val defunts: List<Defunt>) : RecyclerView.Adapter<DefuntAdapter.DefuntViewHolder>() {

    inner class DefuntViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val nomTextView: TextView = itemView.findViewById(R.id.nomTextView)
        val prenomTextView: TextView = itemView.findViewById(R.id.prenomTextView)
        val dateDeces: TextView = itemView.findViewById(R.id.locationTextView1)

        init {
            // Attachez le OnClickListener à l'élément racine de l'élément de liste
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // Récupérez la position de l'élément cliqué
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                // Récupérez le défunt correspondant à cette position
                val currentDefunt = defunts[position]

                // Créez un Bundle pour passer des données à la destination suivante, s'il y en a
                val bundle = Bundle()
                bundle.putInt("id_defunt", currentDefunt.id)

                // Naviguer vers la destination souhaitée en passant le Bundle
                itemView.findNavController().navigate(R.id.action_listDefuntFragment_to_sepultureFragment, bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefuntViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_defunt, parent, false)
        return DefuntViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DefuntViewHolder, position: Int) {
        val currentDefunt = defunts[position]
        holder.nomTextView.text = currentDefunt.nom
        holder.prenomTextView.text = currentDefunt.prenom
        holder.dateDeces.text = currentDefunt.dateDeces
    }

    override fun getItemCount() = defunts.size
}

