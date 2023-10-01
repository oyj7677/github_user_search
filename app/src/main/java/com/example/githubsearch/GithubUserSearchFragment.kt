package com.example.githubsearch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.githubsearch.databinding.GithubUserSearchFragmentBinding
import com.example.githubsearch.viewpager.ViewPagerFragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GithubUserSearchFragment : Fragment() {
    private val binding by lazy { GithubUserSearchFragmentBinding.inflate(layoutInflater) }
    private val viewModel: GithubUserSearchViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initDataBinding()
        initViewModelObserve()
        initViewPager()

        return binding.root
    }

    private fun initDataBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initViewModelObserve() {
        viewModel.clickSearch.observe(viewLifecycleOwner) {
            hideKeyboard()
            binding.etSearchText.clearFocus()
        }
    }

    private fun initViewPager() {
        binding.vpUserList.adapter = ViewPagerFragmentStateAdapter(requireActivity())
        binding.vpUserList.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tabDataSourceType, binding.vpUserList) { tab, position ->
            when (position) {
                0 -> tab.text = "API"
                1 -> tab.text = "Local"
            }
        }.attach()
    }

    private fun hideKeyboard() {
        if(activity != null && requireActivity().currentFocus != null) {
            val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
        }
    }

    companion object {
        fun newInstance() = GithubUserSearchFragment()
    }

}
