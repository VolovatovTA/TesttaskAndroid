package com.example.testtaskandroid.recycler_view

import com.example.testtaskandroid.data.Character
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceholderCharacters @Inject constructor() {

    val CHARACTERS: MutableList<PlaceholderItem> = ArrayList()

    fun addCharacter(item: PlaceholderItem) {
        CHARACTERS.add(item)
    }

    data class PlaceholderItem(val character: Character) {

        override fun toString(): String =
            "PlaceHolderItem{character = $character }"
    }
}