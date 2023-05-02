package com.nokopi.marketregistersystem.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nokopi.marketregistersystem.ProductCustomAdapter
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.Product
import com.nokopi.marketregistersystem.data.ProductDatabase
import com.nokopi.marketregistersystem.databinding.FragmentProductBinding
import com.nokopi.marketregistersystem.dialog.AddProductDialogFragment

class ProductFragment : Fragment(){

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductViewModel
    private lateinit var dialog: AddProductDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val application = requireNotNull(this.activity).application
        val dataSource = ProductDatabase.getInstance(application).productDatabaseDao
        val viwModelFactory = ProductViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viwModelFactory)[ProductViewModel::class.java]
        binding.productViewModel = viewModel

        dialog = AddProductDialogFragment(dataSource)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = ProductCustomAdapter(object : ProductCustomAdapter.OnItemClickListener {
            override fun itemClick(product: Product) {
                viewModel.deleteProduct(product)
            }
        })

        viewModel.productList.observe(viewLifecycleOwner) { product ->
            adapter.submitList(product)
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        viewModel.isClickAddProduct.observe(viewLifecycleOwner) {
            dialog.show(childFragmentManager, "addProduct")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}