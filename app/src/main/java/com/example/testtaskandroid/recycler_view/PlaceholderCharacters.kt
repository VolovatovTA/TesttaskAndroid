package com.example.testtaskandroid.recycler_view

import com.example.testtaskandroid.data.Character
import java.util.*

object PlaceholderCharacters {

    val CHARACTERS: MutableList<PlaceholderItem> = ArrayList()

    fun addCharacter(item: PlaceholderItem) {
        CHARACTERS.add(item)
    }

    data class PlaceholderItem(val character: Character) {

        override fun toString(): String =
            "PlaceHolderItem{character = $character }"
    }
}