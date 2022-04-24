package com.borshevskiy.googlebooksapp.domain

enum class Request(val queryPosition: Int, val queryKeywords: String, val queryName: String) {
    REQUEST_ALL(0, "", "Поиск по всему"),
    REQUEST_IN_TITLE(2, "+intitle:", "Поиск по названию"),
    REQUEST_IN_AUTHOR(1, "+inauthor:", "Поиск по автору"),
    REQUEST_IN_PUBLISHER(4, "+inpublisher:", "Поиск по издателю"),
    REQUEST_SUBJECT(3, "+subject:", "Поиск по жанру")
}