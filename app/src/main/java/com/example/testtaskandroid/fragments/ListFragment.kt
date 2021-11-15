package com.example.testtaskandroid.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtaskandroid.R
import com.example.testtaskandroid.databinding.ListFragmentBinding
import com.example.testtaskandroid.recycler_view.MyItemRecyclerViewAdapter
import com.example.testtaskandroid.recycler_view.PlaceholderContent

class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding

    private lateinit var viewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this)[CharactersViewModel::class.java]

        if (PlaceholderContent.ITEMS.isEmpty()){
            viewModel.loadFirstPage()
        }

        val myAdapter = MyItemRecyclerViewAdapter(requireContext(), viewModel)
        myAdapter.setOnItemClickListener(object : MyItemRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                // Launch a details fragment

                val detailsFragment = BlankFragment(position)
                val transactionFragments =
                    requireActivity().supportFragmentManager.beginTransaction()

                transactionFragments.replace(R.id.fragmentContainerView, detailsFragment)
                transactionFragments.addToBackStack("list in previous state")
                transactionFragments.commit()
            }

        })
        val myLayoutManager = LinearLayoutManager(requireContext())
        with(binding.recyclerView) {
            layoutManager = myLayoutManager
            adapter = myAdapter
        }
        viewModel.listFormState.observe(viewLifecycleOwner, Observer { listFormState ->
            when {
                listFormState == null -> return@Observer
                listFormState.allIsGood -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                listFormState.isEmptyListCharacters -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                listFormState.isAddingCharacters -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                listFormState.isCharactersAdd -> {
                    binding.progressBar.visibility = View.GONE
                    myAdapter.notifyAboutData()
                }
            }
        })
        return binding.root
    }


}



