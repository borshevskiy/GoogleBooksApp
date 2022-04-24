package com.borshevskiy.googlebooksapp.domain

import javax.inject.Inject

class SearchBooksByQueryUseCase @Inject constructor(private val repository: BookRepository) {

    suspend operator fun invoke(query: String) = repository.searchBooks(query)
}