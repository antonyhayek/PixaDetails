package com.antonyhayek.pixadetails.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.antonyhayek.pixadetails.R
import com.antonyhayek.pixadetails.data.remote.config.Resource
import com.antonyhayek.pixadetails.data.remote.responses.ImageResponse
import com.antonyhayek.pixadetails.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val factory: HomeViewModelFactory by instance()
    override val kodein by kodein()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.root
    }

    @ExperimentalPagingApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        val adapter = ImagesRecyclerAdapter { image: ImageResponse -> onImageItemClick(image) }
        binding.rvImages.layoutManager = /*LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)*/StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)


        binding.rvImages.adapter = adapter

       /* lifecycleScope.launch {
            viewModel.getPixabayImages().collectLatest { pagedData ->
                adapter.submitData(pagedData)
            }

        }*/

        lifecycleScope.launch {
            viewModel.getPixabayImagesLiveData().observe(viewLifecycleOwner, Observer {

                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            })
        }
    }


    private fun onImageItemClick(image: ImageResponse) {

        if (findNavController().currentDestination?.id == R.id.homeFragment) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(image))
        }
    }


}