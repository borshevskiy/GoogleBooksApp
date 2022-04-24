package com.borshevskiy.googlebooksapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.borshevskiy.googlebooksapp.databinding.ItemQueryBinding
import com.borshevskiy.googlebooksapp.domain.Request
import com.borshevskiy.googlebooksapp.presentation.SettingsFragmentArgs
import com.borshevskiy.googlebooksapp.presentation.SettingsFragmentDirections

class QueryAdapter(private val args: SettingsFragmentArgs): RecyclerView.Adapter<QueryAdapter.QueryViewHolder>() {

    var queryList: List<Request> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    class QueryViewHolder(val binding: ItemQueryBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        QueryViewHolder(ItemQueryBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: QueryViewHolder, position: Int) {
        val currentQuery = queryList[position]
        with(holder.binding) {
            tvQuery.text = currentQuery.queryName
            if (currentQuery.queryPosition == args.position)  {
                root.isEnabled = false
                checkIndicator.visibility = View.VISIBLE
            }
            root.setOnClickListener {
                root.findNavController().navigate(
                    SettingsFragmentDirections.actionSettingsFragmentToSearchFragment(
                        currentQuery,
                        args.queryFromSearch))
            }
        }
    }

    override fun getItemCount() = queryList.size
}


