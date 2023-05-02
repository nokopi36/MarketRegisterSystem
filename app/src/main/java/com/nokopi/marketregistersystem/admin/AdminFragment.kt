package com.nokopi.marketregistersystem.admin

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.nokopi.marketregistersystem.NFCActivity
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {

    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin, container, false)
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

        viewModel.goAddAdmin.observe(viewLifecycleOwner) {
            if (it) {
                val addAdminIntent = Intent(context, AdminRegisterActivity::class.java)
                startActivity(addAdminIntent)
                viewModel.goAddAdminCompleted()
            }
        }

        viewModel.logout.observe(viewLifecycleOwner) {
            if (it) {
                AlertDialog.Builder(requireContext())
                    .setTitle("ログアウト")
                    .setMessage("ログアウトしますか？")
                    .setPositiveButton("OK") { _, _ ->
                        val nfcIntent = Intent(context, NFCActivity::class.java)
                        startActivity(nfcIntent)
                        viewModel.logoutCompleted()
                    }
                    .setNegativeButton("キャンセル") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}