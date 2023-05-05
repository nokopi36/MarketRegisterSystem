package com.nokopi.marketregistersystem.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.UserDatabase
import com.nokopi.marketregistersystem.databinding.FragmentUserInfoBinding
import com.nokopi.marketregistersystem.dialog.ChangeBalanceDialogFragment

class UserInfoFragment: Fragment() {

    private var _binding: FragmentUserInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserInfoViewModel
    private val args: UserInfoFragmentArgs by navArgs()
    private lateinit var dialog: ChangeBalanceDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val user = args.user
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        val viewModelFactory = UserInfoViewModelFactory(dataSource, user)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserInfoViewModel::class.java]
        binding.userInfoViewModel = viewModel
        dialog = ChangeBalanceDialogFragment(dataSource, user)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isClickErase.observe(viewLifecycleOwner) {
            if (it) {
                AlertDialog.Builder(requireContext())
                    .setTitle("※最終確認")
                    .setMessage("本当に削除しますか？")
                    .setPositiveButton("yes") { _, _ ->
                        viewModel.deleteUser()
                    }
                    .setNegativeButton("no") {dialog, _ ->
                        dialog.cancel()
                        viewModel.noErase()
                    }
                    .show()
            }
        }

        viewModel.goAdmin.observe(viewLifecycleOwner) {
            if (it) {
                val action = UserInfoFragmentDirections.actionUserInfoFragmentToAdminFragment()
                findNavController().navigate(action)
                viewModel.goAdminCompleted()
            }
        }

        viewModel.isClickChangeBalance.observe(viewLifecycleOwner) {
            if (it) {
                dialog.show(childFragmentManager, "changeBalance")
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}