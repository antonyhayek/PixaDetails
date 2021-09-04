package com.antonyhayek.pixadetails.ui.login

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
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.antonyhayek.pixadetails.R
import com.antonyhayek.pixadetails.data.remote.config.Resource
import com.antonyhayek.pixadetails.data.remote.requests.LoginRequest
import com.antonyhayek.pixadetails.databinding.FragmentLoginBinding
import com.antonyhayek.pixadetails.utils.isValidEmail
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class LoginFragment : Fragment(), KodeinAware {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private val factory: LoginViewModelFactory by instance()

    override val kodein by kodein()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        setLayoutListeners()
        setRegisterSpannableString()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }


    private fun setLayoutListeners() {


        binding.btnLogin.setOnClickListener {

            if (credentialsValid()) {

                viewModel.loginResponseLiveData(LoginRequest(binding.etEmail.text.toString(), binding.etPassword.text.toString())).observe(viewLifecycleOwner, Observer {


                    when (it) {
                        is Resource.Success -> {

                            if (it.value.body() != null) {
                                Toast.makeText(requireContext(), it.value.body()!!.data.email, Toast.LENGTH_SHORT).show()
                            }

                            if (findNavController().currentDestination?.id == R.id.loginFragment) {
                                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                            }

                        }
                        is Resource.Failure -> {


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

        return true
    }

    private fun setRegisterSpannableString() {


        val clickableRegister = object : ClickableSpan() {
            override fun onClick(view: View) {
                if (findNavController().currentDestination?.id == R.id.loginFragment) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                }
            }
        }

        val completeTermsString = getString(R.string.text_register_prompt)
        val registerNowString = getString(R.string.text_register_now)
        val startIndex = completeTermsString.indexOf(registerNowString)
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

        binding.tvRegister.setText(spannable, TextView.BufferType.SPANNABLE)
        binding.tvRegister.movementMethod = LinkMovementMethod.getInstance();
    }


}