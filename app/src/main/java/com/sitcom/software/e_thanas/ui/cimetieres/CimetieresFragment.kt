package com.sitcom.software.e_thanas.ui.cimetieres

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.sitcom.software.e_thanas.R

/**
 * A simple [Fragment] subclass.
 * Use the [CimetieresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CimetieresFragment : Fragment() {



    private lateinit var googleMap: GoogleMap




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

}




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cimetieres, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CimetieresFragment.
         */
}