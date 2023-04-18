package com.nokopi.marketregistersystem

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.data.UserDatabase
import com.nokopi.marketregistersystem.databinding.FragmentSignupBinding

class SignUpFragment: Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val inputId = requireArguments().getString("inputId") ?: "noId"
        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        val viewModelFactory = SignUpViewModelFactory(dataSource, inputId)
        viewModel = ViewModelProvider(this, viewModelFactory)[SignUpViewModel::class.java]
        binding.signUpViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.finishSignUp.observe(viewLifecycleOwner) {
            if (it) {
                val nfcIntent = Intent(context, NFCActivity::class.java)
                startActivity(nfcIntent)
                viewModel.finishSignUpComplete()
            }
        }

    }

}