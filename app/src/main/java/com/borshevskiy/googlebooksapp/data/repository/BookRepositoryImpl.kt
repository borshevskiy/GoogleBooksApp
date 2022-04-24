package com.borshevskiy.googlebooksapp.data.repository

import com.borshevskiy.googlebooksapp.data.mappers.BookMapper
import com.borshevskiy.googlebooksapp.data.network.ApiService
import com.borshevskiy.googlebooksapp.domain.Book
import com.borshevskiy.googlebooksapp.domain.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(private val apiService: ApiService, private val mapper: BookMapper) : BookRepository {

    override suspend fun searchBooks(searchQuery: String): List<Book> {
        return if (apiService.getBooks(searchQuery).isSuccessful && apiService.getBooks(searchQuery).body()!!.totalItems > 0) {
            mapper.mapListItemsToListBook(apiService.getBooks(searchQuery).body()!!.items)
        } else  {
            emptyList()
        }
    }
}