package com.zocky.githubapp.ui.favorite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zocky.githubapp.data.room.db.FavoriteUser
import com.zocky.githubapp.databinding.ItemUserBinding
import com.zocky.githubapp.ui.detail.DetailActivity

class FavoriteAdapter : ListAdapter<FavoriteUser, FavoriteAdapter.FavoriteViewHolder>(
    FavoriteUserDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteUser = getItem(position)
        holder.bind(favoriteUser)
        holder.itemView.setOnClickListener {
            navigateToDetailActivity(
                favoriteUser,
                holder.itemView.context
            )
        }
    }

    private fun navigateToDetailActivity(favoriteUser: FavoriteUser, context: Context) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USERNAME, favoriteUser.username)
        context.startActivity(intent)
    }

    class FavoriteViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                tvItemName.text = favoriteUser.username
                Glide.with(itemView)
                    .load(favoriteUser.avatarUrl)
                    .into(imgItemPhoto)
            }
        }
    }

    private class FavoriteUserDiffCallback : DiffUtil.ItemCallback<FavoriteUser>() {
        override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
            return oldItem == newItem
        }
    }
}