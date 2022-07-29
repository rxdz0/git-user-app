package com.example.dicoding.submisi2Bfaa.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dicoding.submisi2Bfaa.R
import com.example.dicoding.submisi2Bfaa.databinding.UserItemBinding
import com.example.dicoding.submisi2Bfaa.model.Users
import com.example.dicoding.submisi2Bfaa.view.DetaiUserActivity
import com.example.dicoding.submisi2Bfaa.view.DetaiUserActivity.Companion.EXTRA_USER

class UserAdapter(private val listUser: List<Users>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    inner class ListViewHolder(private val userBinding: UserItemBinding) :
        RecyclerView.ViewHolder(userBinding.root) {

        fun bind(user: Users) {
            userBinding.apply {
                tvItemUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions
                        .circleCropTransform()
                        .placeholder(R.drawable.ic_account)
                        .error(R.drawable.ic_account)
                    ).into(ivItemAvatar)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetaiUserActivity::class.java)
                intent.putExtra(EXTRA_USER, user.username)
                itemView.context.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val userBinding =
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size
}