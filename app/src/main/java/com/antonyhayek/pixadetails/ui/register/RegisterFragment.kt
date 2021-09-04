package com.antonyhayek.pixadetails.ui.register

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.antonyhayek.pixadetails.R
import com.antonyhayek.pixadetails.data.remote.config.Resource
import com.antonyhayek.pixadetails.data.remote.requests.LoginRequest
import com.antonyhayek.pixadetails.data.remote.requests.RegisterRequest
import com.antonyhayek.pixadetails.databinding.FragmentRegisterBinding
import com.antonyhayek.pixadetails.ui.login.LoginFragmentDirections
import com.antonyhayek.pixadetails.ui.login.LoginViewModel
import com.antonyhayek.pixadetails.utils.isValidEmail
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class RegisterFragment : Fragment(), KodeinAware {
    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding
    private val factory: RegisterViewModelFactory by instance()

    override val kodein by kodein()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        setLayoutListeners()
        setLoginSpannableString()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
    }


    private fun setLayoutListeners() {

        binding.btnRegister.setOnClickListener {

            if (credentialsValid()) {
                binding.pbLoading.visibility = View.VISIBLE
                
                viewModel.registerResponseLiveData(
                    RegisterRequest(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString(),
                        binding.etAge.text.toString().toInt()
                    )
                ).observe(viewLifecycleOwner, Observer {


                    when (it) {
                        is Resource.Success -> {
                            binding.pbLoading.visibility = View.GONE

                            if (it.value.body() != null) {
                                Toast.makeText(
                                    requireContext(),
                                    it.value.body()!!.data.email,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            if (findNavController().currentDestination?.id == R.id.registerFragment) {
                                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
                            }

                        }
                        is Resource.Failure -> {
                            binding.pbLoading.visibility = View.GONE

                        }

                        is Resource.Loading -> {

                        }

                    }
                })


            }

        }

    }


    private fun credentialsValid(): Boolean {

        if (TextUtils.isEmpty(binding.etEmail.text.toString())) {
            binding.lytEmail.error = getString(R.string.text_email_required)
            return false
        } else {
            binding.lytEmail.error = null
        }

        if (!binding.etEmail.text.toString().isValidEmail()) {
            binding.lytEmail.error = getString(R.string.text_invalid_email)
            return false
        } else {
            binding.lytEmail.error = null
        }

        if (TextUtils.isEmpty(binding.etAge.text.toString())) {
            binding.lytAge.error = getString(R.string.text_age_required)
            return false
        } else {
            binding.lytAge.error = null
        }


        if (binding.etAge.text.toString().toInt() < 18 || binding.etAge.text.toString()
                .toInt() > 99
        ) {
            binding.lytAge.error = getString(R.string.text_age_restriction)
            return false
        } else {
            binding.lytAge.error = null
        }


        if (TextUtils.isEmpty(binding.etPassword.text.toString())) {
            binding.lytPassword.error = getString(R.string.text_password_required)
            return false
        } else {
            binding.lytPassword.error = null
        }

        if (binding.etPassword.text.toString().length < 6 || binding.etPassword.text.toString().length > 12) {
            binding.lytPassword.error = getString(R.string.text_password_length_not_valid)
            return false
        } else {
            binding.lytPassword.error = null
        }

        if (TextUtils.isEmpty(binding.etConfirmPassword.text.toString())) {
            binding.lytConfirmPassword.error =
                getString(R.string.text_password_confirmation_required)
            return false
        } else {
            binding.lytConfirmPassword.error = null
        }


        if (binding.etConfirmPassword.text.toString() != binding.etPassword.text.toString()) {
            binding.lytConfirmPassword.error = getString(R.string.text_passwords_should_match)
            return false
        } else {
            binding.lytConfirmPassword.error = null
        }

        return true
    }

    private fun setLoginSpannableString() {


        val clickableRegister = object : ClickableSpan() {
            override fun onClick(view: View) {
                if (findNavController().currentDestination?.id == R.id.registerFragment) {
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                }
            }
        }

        val completeTermsString = getString(R.string.text_login_prompt)
        val loginNowString = getString(R.string.text_login_now)
        val startIndex = completeTermsString.indexOf(loginNowString)
        val lastIndex = completeTermsString.lastIndex + 1

        val spannable = SpannableStringBuilder(completeTermsString)
        spannable.setSpan(
            clickableRegister,
            startIndex, // start
            lastIndex, // end
            0
        )
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.purple_200)),
            startIndex, // start
            lastIndex, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex, // start
            lastIndex, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        spannable.setSpan(
            UnderlineSpan(),
            startIndex, // start
            lastIndex, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        binding.tvLogin.setText(spannable, TextView.BufferType.SPANNABLE)
        binding.tvLogin.movementMethod = LinkMovementMethod.getInstance();
    }

}