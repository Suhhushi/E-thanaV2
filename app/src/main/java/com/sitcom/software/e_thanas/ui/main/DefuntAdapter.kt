package com.sitcom.software.e_thanas.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Defunt

class DefuntAdapter(private val defunts: List<Defunt>) : RecyclerView.Adapter<DefuntAdapter.DefuntViewHolder>() {

    inner class DefuntViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomTextView: TextView = itemView.findViewById(R.id.nomTextView)
        val prenomTextView: TextView = itemView.findViewById(R.id.prenomTextView)



        // Ajoutez d'autres vues si nécessaire pour afficher d'autres informations sur le défunt
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefuntViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_defunt, parent, false)
        return DefuntViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DefuntViewHolder, position: Int) {
        val currentDefunt = defunts[position]
        holder.nomTextView.text = currentDefunt.nom
        holder.prenomTextView.text = currentDefunt.prenom

        // Liez d'autres données de défunts aux vues de votre élément de liste
    }

    override fun getItemCount() = defunts.size
}
