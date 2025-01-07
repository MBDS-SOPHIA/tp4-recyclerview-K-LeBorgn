package com.openclassrooms.magicgithub.ui.user_list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.magicgithub.R
import com.openclassrooms.magicgithub.databinding.ActivityListUserBinding
import com.openclassrooms.magicgithub.di.Injection.getRepository
import com.openclassrooms.magicgithub.model.User

class ListUserActivity : AppCompatActivity(), UserListAdapter.Listener {
    // FOR DESIGN ---
    lateinit var recyclerView: RecyclerView
    lateinit var fab: FloatingActionButton

    // FOR DATA ---
    private lateinit var adapter: UserListAdapter

    // OVERRIDE ---
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_list_user)
        val binding = ActivityListUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerView = binding.activityListUserRv
        fab = binding.activityListUserFab
        configureFab()
        configureRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    // CONFIGURATION ---
    private fun configureRecyclerView() {
        adapter = UserListAdapter(this)
        recyclerView.adapter = adapter
        configureMoveAndSwipeGesture()
    }

    private fun configureMoveAndSwipeGesture() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // Change the order of the users
                adapter.moveUserToIndex(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val user = adapter.getUsers()[viewHolder.adapterPosition]
                user.active = !user.active
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun configureFab() {
        fab.setOnClickListener(View.OnClickListener { view: View? ->
            getRepository().addRandomUser()
            loadData()
        })
    }

    private fun loadData() {
        adapter.updateList(getRepository().getUsers())
        recyclerView.adapter = adapter
    }

    // ACTIONS ---
    override fun onClickDelete(user: User) {
        Log.d(ListUserActivity::class.java.name, "User tries to delete a item.")
        getRepository().deleteUser(user)
        loadData()
    }
}