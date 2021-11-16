package com.example.testtaskandroid.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.testtaskandroid.R
import com.example.testtaskandroid.data.ResultLocation
import com.example.testtaskandroid.databinding.FragmentDetailsBinding
import com.example.testtaskandroid.recycler_view.PlaceholderCharacters


class FragmentDetails(position: Int) : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    lateinit var viewModel: CharactersViewModel
    private val currentPosition = position



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[CharactersViewModel::class.java]
        if (PlaceholderCharacters.CHARACTERS[currentPosition].character.location.url != ""){
            val idOfLocation = PlaceholderCharacters.CHARACTERS[currentPosition].character.location.url.split("location/")[1].toInt()
            viewModel.getLocationById(idOfLocation)
        } else{
            viewModel.getLocationById(-1)
        }

        binding.imgB.setOnClickListener { requireActivity().onBackPressed() }


        viewModel.detailsFormState.observe(viewLifecycleOwner, Observer {
                detailsFormState ->
            when {
                detailsFormState == null -> return@Observer
                detailsFormState.isInfoAboutLocationLoaded -> {
                    fillFragmentWithData(viewModel.location)

                }
            }
        })
        return binding.root
    }

    private fun fillFragmentWithData(location: ResultLocation) {
        val character = PlaceholderCharacters.CHARACTERS[currentPosition].character
        with(binding){
            NameInDetails.text = character.name
            StatusInDetails.text = (" " + character.status + " - " + character.species)
            textViewGenderInDetails.text = character.gender
            textViewOriginInDetails.text = character.origin.name
            textViewLocationInDetails.text = character.location.name
            textViewTypeInDetails.text = location.type
            textViewDimensionInDetails.text = location.dimension
            textViewEpisodeInDetails.text = character.episode.size.toString()
            when (character.status) {
                "unknown" -> {StatusInDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.white_circle,0,0,0)}
                "Alive" -> {StatusInDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_circle,0,0,0)}
                "Dead" -> {StatusInDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.red_circle,0,0,0)}
            }
        }

        Glide.with(requireContext())
            .load(PlaceholderCharacters.CHARACTERS[currentPosition].character.image)
            .optionalCenterCrop()
            .into(binding.imageViewInDetails)
    }
}