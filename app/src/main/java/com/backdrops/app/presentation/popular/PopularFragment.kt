package com.backdrops.app.presentation.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.backdrops.app.databinding.FragmentPopularBinding
import com.backdrops.app.ui.photo.PhotoPagingDataAdapter
import com.backdrops.app.util.showContent
import com.backdrops.app.util.showLoading
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding: FragmentPopularBinding get() = _binding!!

    private val viewModel: PopularViewModel by activityViewModel()

    private val adapter: PhotoPagingDataAdapter by lazy {
        PhotoPagingDataAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.isLoad.value != true) {
            viewModel.getPhotos()
        }
        initLoadState()
        initObserver()
        initRecyclerView()
    }

    private fun initLoadState() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadState ->
                val isLoading = loadState.refresh is LoadState.Loading
                if (isLoading) {
                    binding.msvPopular.showLoading()
                    return@collectLatest
                }
                viewModel.updateIsLoad(true)
                binding.msvPopular.showContent()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvPopular.adapter = adapter
    }

    private fun initObserver() {
        viewModel.photos.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
        }
    }

}