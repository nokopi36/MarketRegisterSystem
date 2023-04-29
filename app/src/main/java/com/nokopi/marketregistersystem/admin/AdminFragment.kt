package com.nokopi.marketregistersystem.admin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminBinding
    private lateinit var viewModel: AdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        binding.adminViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.goAllUser.observe(viewLifecycleOwner) {
            Log.i("observe goAllUser", it.toString())
            if (it) {
                val action = AdminFragmentDirections.actionAdminFragmentToAllUserFragment()
                findNavController().navigate(action)
                viewModel.goAllUserCompleted()
            }
        }

        viewModel.goAddProduct.observe(viewLifecycleOwner) {
            if (it) {
                val action = AdminFragmentDirections.actionAdminFragmentToProductFragment()
                findNavController().navigate(action)
                viewModel.goAddProductCompleted()
            }
        }

    }

}