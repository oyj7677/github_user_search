package com.example.githubsearch.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearch.GithubUserSearchViewModel
import com.example.githubsearch.databinding.SearchUserListFragmentBinding

class UserListFragment: Fragment() {
    private val binding by lazy { SearchUserListFragmentBinding.inflate(layoutInflater)}
    private val viewModel: GithubUserSearchViewModel by activityViewModels()
    private lateinit var userListAdapter: UserListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initDataBinding()
        initRecyclerView()
        initObserve()
        return binding.root
    }

    private fun initObserve() {
        viewModel.githubUserList.observe(viewLifecycleOwner) { githubUserList ->
            userListAdapter.setData(githubUserList)
        }
    }

    private fun initRecyclerView() {
        binding.rvUserList.layoutManager = LinearLayoutManager(requireActivity())
        userListAdapter = UserListAdapter()
        binding.rvUserList.adapter = userListAdapter
    }


    private fun initDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    companion object {
        fun newInstance() = UserListFragment()
        private const val TAG = "SearchUserListFragment"
    }
}
