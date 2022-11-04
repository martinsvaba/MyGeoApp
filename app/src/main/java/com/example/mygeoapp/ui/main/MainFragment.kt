package com.example.mygeoapp.ui.main

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import com.example.mygeoapp.MainActivity
import com.example.mygeoapp.R
import com.example.mygeoapp.databinding.FragmentMainBinding

open class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
             parent.getItemAtPosition(pos)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.let {
            viewModel = ViewModelProvider(it!!).get(MainViewModel::class.java)
        }

        viewModel.locations.observe(viewLifecycleOwner, Observer { locations ->
            binding.spnLocations.adapter =
                ArrayAdapter(requireContext(), R.layout.dropdown_item, locations)
        })



        binding.btnMap.setOnClickListener {

            (activity as MainActivity).onOpenMap()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

