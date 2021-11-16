package com.example.testtaskandroid.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtaskandroid.R
import com.example.testtaskandroid.databinding.ListOfCharactersFragmentBinding
import com.example.testtaskandroid.recycler_view.CharactersRecyclerViewAdapter
import com.example.testtaskandroid.recycler_view.PlaceholderCharacters

class ListOfCharactersFragment : Fragment() {

    private lateinit var binding: ListOfCharactersFragmentBinding
    private lateinit var viewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListOfCharactersFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[CharactersViewModel::class.java]

        if (PlaceholderCharacters.CHARACTERS.isEmpty()){
            viewModel.loadFirstPage()
        }

        val adapterCharactersInRecyclerView = CharactersRecyclerViewAdapter(viewModel)

        adapterCharactersInRecyclerView.setOnItemClickListener{
            position ->
            // Launch a details fragment

            val detailsFragment = FragmentDetails(position)
            val transactionFragments =
                requireActivity().supportFragmentManager.beginTransaction()

            transactionFragments.replace(R.id.fragmentContainerView, detailsFragment)
            transactionFragments.addToBackStack("list in previous state")
            transactionFragments.commit()
        }


        val linearLayoutManager = LinearLayoutManager(requireContext())
        with(binding.recyclerView) {
            layoutManager = linearLayoutManager
            adapter = adapterCharactersInRecyclerView
            setOnClickListener {

            }
        }
        viewModel.listFormState.observe(viewLifecycleOwner, Observer { listFormState ->
            when {
                listFormState == null -> return@Observer
                listFormState.allIsGood -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                listFormState.isEmptyCharactersList -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                listFormState.isCharactersLoading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                listFormState.isCharactersLoaded -> {
                    binding.progressBar.visibility = View.GONE
                    adapterCharactersInRecyclerView.notifyAboutData(listFormState.countLoadedCharacters)
                }
            }
        })
        return binding.root
    }


}



