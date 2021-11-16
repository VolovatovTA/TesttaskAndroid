package com.example.testtaskandroid.recycler_view

import com.example.testtaskandroid.data.Character
import java.util.*

object PlaceholderCharacters {

    val CHARACTERS: MutableList<PlaceholderItem> = ArrayList()

    fun addItem(item: PlaceholderItem) {
        CHARACTERS.add(item)
    }

    data class PlaceholderItem(val character: Character, val nameOfFirstEpisode: String) {

        override fun toString(): String =
            "PlaceHolderItem{character = $character; nameOfFirstEpisode = $nameOfFirstEpisode; }"
    }
}