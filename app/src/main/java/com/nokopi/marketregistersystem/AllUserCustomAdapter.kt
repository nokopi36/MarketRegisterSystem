package com.nokopi.marketregistersystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nokopi.marketregistersystem.data.User

class AllUserCustomAdapter(
    private val itemClickListener: OnItemClickListener
): ListAdapter<User, ViewHolder>(diff_util) {

    interface OnItemClickListener {
        fun itemClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.userName.text = user.userName
        holder.userName.setOnClickListener {
            itemClickListener.itemClick(user)
        }
    }
}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val userName: TextView = view.findViewById(R.id.userNameText)
}

val diff_util = object : DiffUtil.ItemCallback<User>() {
    // Itemのnameが同じならtrue
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.userName == newItem.userName
    }

    // Itemが同じならtrue
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}