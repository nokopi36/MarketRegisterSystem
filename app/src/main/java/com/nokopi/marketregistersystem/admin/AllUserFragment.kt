package com.nokopi.marketregistersystem.admin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nokopi.marketregistersystem.AllUserCustomAdapter
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabase
import com.nokopi.marketregistersystem.databinding.FragmentAllUserBinding

class AllUserFragment : Fragment() {

    private var _binding: FragmentAllUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AllUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_user, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val application = requireNotNull(this.activity).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        val viewModelFactory = AllUserViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory)[AllUserViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        val adapter = AllUserCustomAdapter(object : AllUserCustomAdapter.OnItemClickListener {
            override fun itemClick(user: User) {
                val action = AllUserFragmentDirections.actionAllUserFragmentToUserInfoFragment(user)
                findNavController().navigate(action)
            }
        })

        viewModel.userList.observe(viewLifecycleOwner) { users ->
            users.let {
                adapter.submitList(users)
            }
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}