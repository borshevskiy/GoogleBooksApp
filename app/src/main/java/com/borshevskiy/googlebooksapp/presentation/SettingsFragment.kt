package com.borshevskiy.googlebooksapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.borshevskiy.googlebooksapp.databinding.FragmentSettingsBinding
import com.borshevskiy.googlebooksapp.domain.Request
import com.borshevskiy.googlebooksapp.presentation.adapters.QueryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val mAdapter by lazy { QueryAdapter(args) }
    private val args by navArgs<SettingsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.listQueries.adapter = mAdapter
        mAdapter.queryList = listOf(Request.REQUEST_ALL, Request.REQUEST_IN_AUTHOR,
            Request.REQUEST_IN_TITLE, Request.REQUEST_SUBJECT, Request.REQUEST_IN_PUBLISHER)
    }
}