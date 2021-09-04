package com.antonyhayek.pixadetails.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import com.antonyhayek.pixadetails.R
import com.antonyhayek.pixadetails.databinding.FragmentDetailsBinding
import com.antonyhayek.pixadetails.ui.login.LoginViewModel
import com.antonyhayek.pixadetails.ui.login.LoginViewModelFactory
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class DetailsFragment : Fragment(), KodeinAware {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
    private val factory: DetailsViewModelFactory by instance()

    override val kodein by kodein()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        arguments?.let { it ->
            DetailsFragmentArgs.fromBundle(it).let {
                binding.image = it.image

                val tags: List<String> = it.image.tags.split(",").toList()

                for (tag in tags) {
                    val chip = Chip(binding.cgImageTags.context)
                    chip.text= tag

                    chip.isClickable = false
                    chip.isCheckable = false
                    binding.cgImageTags.addView(chip)
                }

            }
        }


        setLayoutListeners()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)
    }

    private fun setLayoutListeners() {

        binding.let {
            it.ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            it.btnLogout.setOnClickListener {

                lifecycleScope.launch {
                    viewModel.updateIsLoggedIn(false)
                }

                NavDeepLinkBuilder(requireContext())
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.loginFragment)
                    .createPendingIntent().send()
            }
        }
    }
}