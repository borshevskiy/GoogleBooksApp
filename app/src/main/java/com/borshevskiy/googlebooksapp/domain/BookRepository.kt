package com.borshevskiy.googlebooksapp.domain

interface BookRepository {

    suspend fun searchBooks(searchQuery: String): List<Book>

}