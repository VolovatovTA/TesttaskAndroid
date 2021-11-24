package com.example.testtaskandroid.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtaskandroid.R
import com.example.testtaskandroid.dagger.MyApplication
import com.example.testtaskandroid.databinding.ListOfCharactersFragmentBinding
import com.example.testtaskandroid.recycler_view.CharactersRecyclerViewAdapter
import com.example.testtaskandroid.recycler_view.PlaceholderCharacters
import javax.inject.Inject

class ListOfCharactersFragment : Fragment() {

    private lateinit var binding: ListOfCharactersFragmentBinding
    @Inject lateinit var viewModel: CharactersViewModel
    @Inject lateinit var adapterCharactersInRecyclerView:CharactersRecyclerViewAdapter
    @Inject lateinit var placeholderCharacters: PlaceholderCharacters

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireContext().applicationContext as MyApplication).appComponent.inject(this)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListOfCharactersFragmentBinding.inflate(layoutInflater, container, false)

        if (placeholderCharacters.CHARACTERS.isEmpty()){
            viewModel.loadFirstPage()
        }

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
                    adapterCharactersInRecyclerView.notifyDataInserted(listFormState.countLoadedCharacters)
                }
            }
        })
        return binding.root
    }


}



