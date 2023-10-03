package com.example.githubsearch

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.github_domain.DataSourceType
import com.example.githubsearch.databinding.GithubUserSearchFragmentBinding
import com.example.githubsearch.viewpager.ViewPagerFragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GithubUserSearchFragment : Fragment() {

    companion object {
        fun newInstance() = GithubUserSearchFragment()
    }

    private val binding by lazy { GithubUserSearchFragmentBinding.inflate(layoutInflater) }
    private val viewModel: GithubUserSearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataBinding()
        initViewModelObserve()
        initViewPager()
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
                0 -> tab.text = getString(R.string.TEXT_API)
                1 -> tab.text = getString(R.string.TEXT_LOCAL)
            }
        }.attach()

        binding.vpUserList.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                viewModel.setDataSourceType(DataSourceType.values()[position])
            }
        })
    }

    private fun hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            val inputMethodManager =
                requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken, 0
            )
        }
    }
}
