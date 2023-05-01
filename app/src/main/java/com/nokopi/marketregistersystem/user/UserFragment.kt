package com.nokopi.marketregistersystem.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.NFCActivity
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.UserDatabase
import com.nokopi.marketregistersystem.databinding.FragmentUserBinding

class UserFragment: Fragment() {

    private lateinit var binding: FragmentUserBinding
    private lateinit var viewModel: UserViewModel
    private var inputId = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        inputId = requireArguments().getString("inputId") ?: "noId"
        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        val viewModelFactory = UserViewModelFactory(dataSource, inputId)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        binding.userViewModel= viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser(inputId)

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

}