package com.example.githubsearch.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.github_domain.repository.GithubUserData
import com.example.githubsearch.databinding.UserListBinding

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.RecordViewHolder>() {

    private var memoryList = emptyList<GithubUserData>()

    class RecordViewHolder(private var userListBinding: UserListBinding) : RecyclerView.ViewHolder(userListBinding.root) {
        fun bind(userData: GithubUserData) {
            userListBinding.userData = userData
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return RecordViewHolder(binding)
    }

    override fun getItemCount() = memoryList.size

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(memoryList[position])
    }

    fun setData(newMemoryList: List<GithubUserData>) {
        val diffUtil = RecodeDiffUtil(memoryList, newMemoryList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        memoryList = newMemoryList
        diffResult.dispatchUpdatesTo(this)
    }

    class RecodeDiffUtil(
        private val oldList: List<GithubUserData>,
        private val newList: List<GithubUserData>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}
