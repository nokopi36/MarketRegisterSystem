package com.nokopi.marketregistersystem.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.ProductDatabaseDao
import com.nokopi.marketregistersystem.databinding.AddProductDialogBinding

class AddProductDialogFragment(private val dataSource: ProductDatabaseDao): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DataBindingUtil.inflate<AddProductDialogBinding>(requireActivity().layoutInflater, R.layout.add_product_dialog, null, false)
        val viewModelFactory = AddProductViewModelFactory(dataSource)
        val viewModel = ViewModelProvider(this, viewModelFactory)[AddProductViewModel::class.java]
        binding.addProductViewModel = viewModel
        val builder = AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setTitle("商品追加")
            .setPositiveButton("追加") { _, _ ->
                if (viewModel.isProductNameNull() && viewModel.isProductPriceNull()) {
                    viewModel.addProduct()
                    Toast.makeText(activity, "登録完了", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "商品名もしくは値段が入力されていません", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("キャンセル") { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
    }
}