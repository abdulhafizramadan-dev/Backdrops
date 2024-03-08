package com.backdrops.app.presentation.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.backdrops.app.databinding.FragmentGalleryBinding
import com.backdrops.app.domain.model.Resource
import com.backdrops.app.ui.SectionGallery
import com.xwray.groupie.GroupieAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding get() = _binding!!

    private val viewModel: GalleryViewModel by viewModel()

    private val groupieAdapter: GroupieAdapter by lazy {
        GroupieAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.gallery.value !is Resource.Success) {
            viewModel.getGallery()
        }

        initObserver()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.rvGallery.adapter = groupieAdapter
    }

    private fun initObserver() {
        viewModel.gallery.observe(viewLifecycleOwner) { resource ->
            groupieAdapter.clear()
            when (resource) {
                Resource.Loading -> Log.d("TAG", "initObserver: Loading")
                is Resource.Error -> {
                    val groupingData = resource.data?.groupBy { it.createdAt } ?: emptyMap()
                    groupingData.forEach { (date, photos) ->
                        groupieAdapter.add(
                            SectionGallery(title = date, photos = photos)
                        )
                    }
                }
                is Resource.Success -> {
                    val groupingData = resource.data.groupBy { it.createdAt }
                    groupingData.forEach { (date, photos) ->
                        groupieAdapter.add(
                            SectionGallery(title = date, photos = photos)
                        )
                    }
                }
            }
        }
    }

}