package com.borshevskiy.googlebooksapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.borshevskiy.googlebooksapp.domain.Book

object BookDiffCallback: DiffUtil.ItemCallback<Book>() {

    override fun areItemsTheSame(oldItem: Book, newItem: Book) = oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: Book, newItem: Book) = oldItem == newItem
}