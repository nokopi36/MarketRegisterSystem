package com.nokopi.marketregistersystem.admin

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nokopi.marketregistersystem.AllUserCustomAdapter
import com.nokopi.marketregistersystem.R
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabase
import com.nokopi.marketregistersystem.databinding.FragmentAllUserBinding
import java.io.File
import java.io.IOException

class AllUserFragment : Fragment() {

    private var _binding: FragmentAllUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AllUserViewModel

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
            result: ActivityResult ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val resultData = result.data
            if (resultData != null) {
                val uri: Uri? = resultData.data
                val csvData = makeCSVData(viewModel.userList)
                Log.i("userList", viewModel.userList.value.toString())
                Log.i("csvdata", csvData)
                try {
                    if (uri != null) {
                        context?.contentResolver?.openOutputStream(uri).use {outputStream ->
                            outputStream?.writer().use { writer ->
                                writer?.write(csvData)
                            }
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

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
        binding.allUserViewModel = viewModel

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

        viewModel.getAllUser()

        viewModel.userList.observe(viewLifecycleOwner) { users ->
            users.let {
                adapter.submitList(users)
            }
        }

        viewModel.pushBackup.observe(viewLifecycleOwner) {
            Log.i("go pushbackup","yes")
            if (it) {
                AlertDialog.Builder(requireContext())
                    .setTitle("バックアップの作成")
                    .setMessage("全ユーザーの情報をallUsers.csvに出力します")
                    .setPositiveButton("OK") { _, _ ->
                        val backupFile = File(requireContext().getExternalFilesDir(null), "allUsers.csv")
                        backupFile.bufferedWriter().use { writer ->
                            viewModel.userList.value?.forEach { users ->
                                val line = "${users.id},${users.userId},${users.userName},${users.userBalance}\n"
                                writer.write(line)
                            }
                        }

                        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                            addCategory(Intent.CATEGORY_OPENABLE)
                            type = "text/csv"
                            putExtra(Intent.EXTRA_TITLE, "allUsers.csv")
                        }
                        resultLauncher.launch(intent)
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
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

    private fun makeCSVData(userList: LiveData<List<User>>): String {
        val stringBuilder = StringBuilder()
        userList.value?.forEach { users ->
            stringBuilder.append("${users.id},${users.userId},${users.userName},${users.userBalance}\n")
        }
        return stringBuilder.toString()
    }

}