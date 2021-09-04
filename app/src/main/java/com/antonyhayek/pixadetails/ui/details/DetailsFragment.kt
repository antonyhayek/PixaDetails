package com.antonyhayek.pixadetails.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.antonyhayek.pixadetails.R
import com.antonyhayek.pixadetails.databinding.FragmentDetailsBinding
import com.google.android.material.chip.Chip


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun setLayoutListeners() {

        binding.let {
            it.ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }


    }

}