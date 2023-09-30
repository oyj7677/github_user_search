package com.example.githubsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_domain.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val gitHubRepository: GithubRepository) : ViewModel() {

}
