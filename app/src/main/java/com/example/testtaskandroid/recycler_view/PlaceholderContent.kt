package com.example.testtaskandroid.recycler_view

import com.example.testtaskandroid.data.Character
import java.util.*

object PlaceholderContent {

    val ITEMS: MutableList<PlaceholderItem> = ArrayList()

    fun addItem(item: PlaceholderItem) {
        ITEMS.add(item)
    }

    data class PlaceholderItem(val character: Character, val nameOfFirstEpisode: String) {

        override fun toString(): String =
            "PlaceHolderItem{character = $character; nameOfFirstEpisode = $nameOfFirstEpisode; }"
    }
}