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
        val dateDeces: TextView = itemView.findViewById(R.id.locationTextView1)
        val btnDefunt: Button = itemView.findViewById(R.id.btnDefunt)
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


        val bundle = Bundle()
        bundle.putInt("id_defunt", currentDefunt.id)


        holder.btnDefunt.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_listDefuntFragment_to_sepultureFragment, bundle)
        }
    }

    override fun getItemCount() = defunts.size
}

