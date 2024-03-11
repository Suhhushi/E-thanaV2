package com.sitcom.software.e_thanas.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sitcom.software.e_thanas.R

class ListDefuntFragment : Fragment() {

    companion object {
        fun newInstance() = ListDefuntFragment()
    }

    private lateinit var viewModel: listDefuntViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(listDefuntViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_listdefunt, container, false)
    }

}