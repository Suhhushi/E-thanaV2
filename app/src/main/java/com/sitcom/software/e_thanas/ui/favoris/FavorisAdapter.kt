package com.sitcom.software.e_thanas.ui.favoris

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sitcom.software.e_thanas.R
import com.sitcom.software.e_thanas.classes.Defunt


class FavorisAdapter(private val defuntList: List<Defunt>) : RecyclerView.Adapter<FavorisAdapter.DefuntViewHolder>() {

    inner class DefuntViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefuntViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_enregistrer, parent, false)
        return DefuntViewHolder(view)
    }

    override fun onBindViewHolder(holder: DefuntViewHolder, position: Int) {
        val defunt = defuntList[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.nameText)?.text = defunt.nom
            findViewById<TextView>(R.id.firstNameTextView1)?.text = defunt.prenom
            findViewById<TextView>(R.id.locationTextView1)?.text = defunt.dateDeces

            Log.d("testAdapter", "defunt = $defunt")
        }
    }

    override fun getItemCount() = defuntList.size
}


