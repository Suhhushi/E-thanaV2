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

    inner class DefuntViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomTextView: TextView = itemView.findViewById(R.id.nomTextView)
        val prenomTextView: TextView = itemView.findViewById(R.id.prenomTextView)
        val btnDefunt: Button = itemView.findViewById(R.id.btnDefunt)
        var dateDeces: TextView = itemView.findViewById(R.id.locationTextView1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefuntViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_defunt, parent, false)
        return DefuntViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DefuntViewHolder, position: Int) {
        val currentDefunt = defunts[position]

        // Date de décès du défunt au format AAAA-MM-JJ
        val dateDeces = currentDefunt.dateDeces

        // Extraire les parties de la date
        val annee = dateDeces.substring(0, 4)
        val mois = dateDeces.substring(5, 7)
        val jour = dateDeces.substring(8, 10)

        // Formater la date au format JJ-MM-AAAA
        val dateDecesFormatOk = "$jour-$mois-$annee"

        holder.nomTextView.text = currentDefunt.nom
        holder.prenomTextView.text = currentDefunt.prenom

        // Définir le texte formaté dans le TextView correspondant
        holder.dateDeces.text = dateDecesFormatOk





        val bundle = Bundle()
        bundle.putInt("id_defunt", currentDefunt.id)


        holder.btnDefunt.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_listDefuntFragment_to_sepultureFragment, bundle)
        }
    }

    override fun getItemCount() = defunts.size
}

