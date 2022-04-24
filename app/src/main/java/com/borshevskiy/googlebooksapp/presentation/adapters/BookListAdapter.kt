package com.borshevskiy.googlebooksapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.borshevskiy.googlebooksapp.R
import com.borshevskiy.googlebooksapp.databinding.BookItemBinding
import com.borshevskiy.googlebooksapp.domain.Book

class BookListAdapter: ListAdapter<Book, BookListAdapter.BookViewHolder>(BookDiffCallback) {

    class BookViewHolder(val binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        with(holder.binding) {
            with(book) {
                tvBookTitle.text = title
                tvBookAuthor.text = authors
                imageBook.load(smallThumbnail) {
                    placeholder(R.drawable.ic_no_image)
                    crossfade(600)
                    error(R.drawable.ic_no_image)}
            }
        }
    }
}