package com.example.githubsearch

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
    private var isInitialLoad = false
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

        viewModel.toastEvent.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(requireContext(), getString(msg), Toast.LENGTH_SHORT).show()
        }
    }

    /*
    * API호출과 DB조회를 위한 ViewPager를 초기화 합니다.
    * 앱 실행 시 API호출을 방지하기 위해 isInitialLoad를 사용하여 초기화 시에는 호출하지 않습니다.
    * */
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
                if (isInitialLoad) {
                    viewModel.setDataSourceType(DataSourceType.values()[position])
                } else {
                    isInitialLoad = true
                }
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
