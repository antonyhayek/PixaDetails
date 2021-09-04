package com.antonyhayek.pixadetails.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.antonyhayek.pixadetails.R
import com.antonyhayek.pixadetails.data.remote.config.Resource
import com.antonyhayek.pixadetails.data.remote.responses.ImageResponse
import com.antonyhayek.pixadetails.databinding.FragmentHomeBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware {
    private var imagesList: List<ImageResponse> = listOf()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val factory: HomeViewModelFactory by instance()
    private lateinit var imagesRecyclerAdapter: ImagesRecyclerAdapter

    override val kodein by kodein()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
     //   initImagesRecyclerView()
        getPixabayImages()
    }

    private fun initImagesRecyclerView() {


        imagesRecyclerAdapter = ImagesRecyclerAdapter(
            imagesList.toMutableList(),
            requireActivity()
        ) { image: ImageResponse -> onImageItemClick(image) }

        binding.let {
            it.rvImages.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            it.rvImages.adapter = imagesRecyclerAdapter
        }
    }

    private fun onImageItemClick(image: ImageResponse) {


        if (findNavController().currentDestination?.id == R.id.homeFragment) {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailsFragment(image))
        }
    }

    private fun getPixabayImages() {

        viewModel.pixabayImagesResponseLiveData().observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Success -> {

                    if (it.value.body() != null) {
                        imagesList = it.value.body()!!.hits
                        initImagesRecyclerView()
                        //imagesRecyclerAdapter.notifyDataSetChanged()
                    }

                }
                is Resource.Failure -> {

                }

                is Resource.Loading -> {

                }

            }
        })

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

}