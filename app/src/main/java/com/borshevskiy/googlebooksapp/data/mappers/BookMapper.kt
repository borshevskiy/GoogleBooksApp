package com.borshevskiy.googlebooksapp.data.mappers

import com.borshevskiy.googlebooksapp.data.network.model.Item
import com.borshevskiy.googlebooksapp.domain.Book
import javax.inject.Inject

class BookMapper @Inject constructor() {

    private fun mapItemToBook(item: Item): Book = Book(
        item.volumeInfo?.title?: "",
        item.volumeInfo?.authors?.joinToString(", ")?: "",
        item.volumeInfo?.imageLinks?.smallThumbnail?: ""
    )

    fun mapListItemsToListBook(listItems: List<Item>) = listItems.map { mapItemToBook(it) }

}