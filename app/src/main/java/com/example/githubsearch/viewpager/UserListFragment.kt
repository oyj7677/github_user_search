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

class UserListFragment : Fragment() {

    companion object {
        fun newInstance() = UserListFragment()
    }

    private val binding by lazy { SearchUserListFragmentBinding.inflate(layoutInflater) }
    private val viewModel: GithubUserSearchViewModel by activityViewModels()
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDataBinding()
        initRecyclerView()
        initObserve()
    }

    private fun initObserve() {
        viewModel.githubUserList.observe(viewLifecycleOwner) { githubUserList ->
            userListAdapter.setData(githubUserList)
        }
    }

    private fun initRecyclerView() {
        activity?.let {
            binding.rvUserList.layoutManager = LinearLayoutManager(it.baseContext)
            val itemDecoration = CustomDividerItemDecoration(it.baseContext)
            binding.rvUserList.addItemDecoration(itemDecoration)
            userListAdapter = UserListAdapter(UserListAdapter.ItemClickListener { userData ->
                viewModel.clickItem(userData)
            })
            binding.rvUserList.adapter = userListAdapter
        }
    }

    private fun initDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }


}
