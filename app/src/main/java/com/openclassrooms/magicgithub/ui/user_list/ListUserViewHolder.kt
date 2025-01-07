package com.openclassrooms.magicgithub.ui.user_list

import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.magicgithub.R
import com.openclassrooms.magicgithub.model.User
import kotlinx.android.synthetic.main.item_list_user.view.item_list_user_avatar
import kotlinx.android.synthetic.main.item_list_user.view.item_list_user_delete_button
import kotlinx.android.synthetic.main.item_list_user.view.item_list_user_username

class ListUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // FOR DESIGN ---
    private var avatar: ImageView = itemView.item_list_user_avatar
    private val username: TextView = itemView.item_list_user_username
    private val deleteButton: ImageButton = itemView.item_list_user_delete_button

    fun bind(user: User, callback: UserListAdapter.Listener) {
        itemView.apply{
            setBackgroundColor(
                if (user.active) Color.WHITE
                else Color.RED
            )
        }

        Glide.with(itemView.context)
            .load(user.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(avatar)
        username.text = user.login
        deleteButton.setOnClickListener { callback.onClickDelete(user) }
    }

}