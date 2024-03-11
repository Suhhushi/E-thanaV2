package com.sitcom.software.e_thanas.ui.favoris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sitcom.software.e_thanas.databinding.FragmentFavorisBinding


class FavorisFragment : Fragment() {

    private var _binding: FragmentFavorisBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favorisViewModel =
            ViewModelProvider(this).get(FavorisViewModel::class.java)

        _binding = FragmentFavorisBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}