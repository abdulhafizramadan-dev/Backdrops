package com.backdrops.app.presentation.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.backdrops.app.databinding.FragmentGalleryBinding
import com.backdrops.app.ui.gallery.GalleryPagingDataAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding: FragmentGalleryBinding get() = _binding!!

    private val viewModel: GalleryViewModel by viewModel()

    private lateinit var galleryPagingDataAdapter: GalleryPagingDataAdapter

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
        initRecyclerView()
        initObserver()
    }

    private fun initRecyclerView() {
        galleryPagingDataAdapter = GalleryPagingDataAdapter()
        binding.rvGallery.adapter = galleryPagingDataAdapter
    }

    private fun initObserver() {
        viewModel.gallery.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                galleryPagingDataAdapter.submitData(pagingData)
            }
        }
    }

}