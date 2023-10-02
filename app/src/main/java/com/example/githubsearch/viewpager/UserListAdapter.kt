package com.example.githubsearch.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.github_domain.repository.GithubUserData
import com.example.githubsearch.databinding.HeaderListBinding
import com.example.githubsearch.databinding.UserListBinding

class UserListAdapter(private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var userDataList = emptyList<UserDataListItem>()

    class UserViewHolder(private var binding: UserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserDataListItem.UserData, clickListener: ItemClickListener) {
            binding.userData = userData
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    class HeaderViewHolder(private var binding: HeaderListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(headerData: UserDataListItem.HeaderData) {
            binding.headerData = headerData
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(HeaderListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            VIEW_TYPE_USER -> UserViewHolder(UserListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (userDataList[position]) {
            is UserDataListItem.HeaderData -> VIEW_TYPE_HEADER
            is UserDataListItem.UserData -> VIEW_TYPE_USER
        }
    }

    override fun getItemCount() = userDataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = userDataList[position]) {
            is UserDataListItem.HeaderData -> (holder as HeaderViewHolder).bind(item)
            is UserDataListItem.UserData -> (holder as UserViewHolder).bind(item, itemClickListener)
        }
    }

    fun setData(newMemoryList: List<UserDataListItem>) {
        val diffUtil = RecodeDiffUtil(userDataList, newMemoryList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        userDataList = newMemoryList
        diffResult.dispatchUpdatesTo(this)
    }

    class RecodeDiffUtil(
        private val oldList: List<UserDataListItem>,
        private val newList: List<UserDataListItem>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return when(oldList[oldItemPosition]) {
                is UserDataListItem.HeaderData -> return oldList[oldItemPosition] == newList[newItemPosition]
                is UserDataListItem.UserData -> return oldList[oldItemPosition] == newList[newItemPosition]
            }

        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

    class ItemClickListener(val clickListener: (userData: UserDataListItem.UserData) -> Unit) {
        fun onClickItem(userData: UserDataListItem.UserData) = clickListener(userData)
    }

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_USER = 1
    }
}
