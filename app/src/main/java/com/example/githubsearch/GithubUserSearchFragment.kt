package com.example.githubsearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.github_domain.Api
import com.example.github_domain.Local
import com.example.githubsearch.databinding.GithubUserSearchFragmentBinding
import com.example.githubsearch.viewpager.ViewPagerFragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.dataSourceType.observe(viewLifecycleOwner) { dataSourceType ->
            when (dataSourceType) {
                Api -> TODO()
                Local -> TODO()
            }
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


    companion object {
        fun newInstance() = GithubUserSearchFragment()
    }

}
