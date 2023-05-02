package com.nokopi.marketregistersystem.user

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nokopi.marketregistersystem.PurchaseProductCustomAdapter
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.Product
import com.nokopi.marketregistersystem.data.ProductDatabase
import com.nokopi.marketregistersystem.data.UserDatabase
import com.nokopi.marketregistersystem.databinding.FragmentPurchaseBinding

class PurchaseFragment : Fragment() {

    private var _binding: FragmentPurchaseBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PurchaseViewModel
    private val args: PurchaseFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_purchase, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val inputId = args.inputId
        val application = requireNotNull(this.activity).application
        val userDataSource = UserDatabase.getInstance(application).userDatabaseDao
        val productDataSource = ProductDatabase.getInstance(application).productDatabaseDao
        val viewModelFactory = PurchaseViewModelFactory(userDataSource, productDataSource, inputId)
        viewModel = ViewModelProvider(this, viewModelFactory)[PurchaseViewModel::class.java]
        binding.purchaseViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputId = args.inputId

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter =
            PurchaseProductCustomAdapter(object : PurchaseProductCustomAdapter.OnItemClickListener {
                override fun itemClick(product: Product) {
                    viewModel.addPurchaseResult(product)
                }
            })

        viewModel.productList.observe(viewLifecycleOwner) { product ->
            adapter.submitList(product)
        }

        viewModel.finishPurchase.observe(viewLifecycleOwner) {
            if (it) {
                val action =
                    PurchaseFragmentDirections.actionPurchaseFragmentToUserFragment(inputId)
                findNavController().navigate(action)
                viewModel.finishPurchaseComplete()
            }
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        viewModel.canPurchase.observe(viewLifecycleOwner) {
            if (it) {
                AlertDialog.Builder(requireContext())
                    .setTitle("※最終確認")
                    .setMessage("購入しますか？")
                    .setPositiveButton("OK") { _, _ ->
                        viewModel.purchase()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("※購入エラー")
                    .setMessage("残高が不足しています")
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}