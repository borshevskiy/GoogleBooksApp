package com.borshevskiy.googlebooksapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.borshevskiy.googlebooksapp.domain.Book
import com.borshevskiy.googlebooksapp.domain.Request
import com.borshevskiy.googlebooksapp.domain.SearchBooksByQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchBookByQueryUseCase: SearchBooksByQueryUseCase): ViewModel() {

    private val _searchBookList = MutableLiveData<List<Book>>()
    val searchBookList: LiveData<List<Book>> get() = _searchBookList

    private val query: MutableLiveData<String> = MutableLiveData()

    fun searchBooks(text: String) = viewModelScope.launch {
        if (query.value == "" || text == "") {
            _searchBookList.value = searchBookByQueryUseCase(text)!!
        } else {
            _searchBookList.value = searchBookByQueryUseCase("$text${query.value}$text")!!
        }
    }

    fun applySearchQuery(request: Request) {
        query.value = request.queryKeywords
    }
}