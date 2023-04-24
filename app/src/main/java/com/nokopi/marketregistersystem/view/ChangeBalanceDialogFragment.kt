package com.nokopi.marketregistersystem.view

import android.app.Dialog
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao
import com.nokopi.marketregistersystem.databinding.ChangeBalanceDialogBinding

class ChangeBalanceDialogFragment(private val dataSource: UserDatabaseDao, private val user: User): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DataBindingUtil.inflate<ChangeBalanceDialogBinding>(requireActivity().layoutInflater, R.layout.change_balance_dialog, null, false)
        val viewModelFactory = ChangeBalanceViewModelFactory(dataSource, user)
        val viewModel = ViewModelProvider(this, viewModelFactory)[ChangeBalanceViewModel::class.java]
        binding.changeBalanceViewModel = viewModel
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setTitle("残高変更")
            .setPositiveButton("変更") { _, _ ->
                if (viewModel.isBalanceNull()) {
                    viewModel.changeBalance()
                }
            }
            .setNegativeButton("キャンセル") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }

}