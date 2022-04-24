package com.borshevskiy.googlebooksapp.di

import com.borshevskiy.googlebooksapp.data.repository.BookRepositoryImpl
import com.borshevskiy.googlebooksapp.domain.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BookRepositoryModule {

    @Binds
    abstract fun bindRepository(impl: BookRepositoryImpl): BookRepository
}