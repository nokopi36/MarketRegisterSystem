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
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.UserDatabase
import com.nokopi.marketregistersystem.databinding.FragmentChargeBalanceBinding

class ChargeBalanceFragment: Fragment() {

    private var _binding: FragmentChargeBalanceBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ChargeBalanceViewModel
    private val args: ChargeBalanceFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charge_balance, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val inputId = args.inputId
        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        val viewModelFactory = ChargeBalanceViewModelFactory(dataSource, inputId)
        viewModel = ViewModelProvider(this, viewModelFactory)[ChargeBalanceViewModel::class.java]
        binding.chargeBalanceViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputId = args.inputId

        viewModel.finishCharge.observe(viewLifecycleOwner) {
            if (it) {
                val action = ChargeBalanceFragmentDirections.actionChargeBalanceFragmentToUserFragment(inputId)
                findNavController().navigate(action)
                viewModel.finishChargeComplete()
            }
        }

        viewModel.canCharge.observe(viewLifecycleOwner) {
            if (it) {
                AlertDialog.Builder(requireContext())
                    .setTitle("※最終確認")
                    .setMessage("チャージしますか？")
                    .setPositiveButton("OK") { _, _ ->
                        viewModel.charge()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("※チャージエラー")
                    .setMessage("チャージ金額を選択してください")
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