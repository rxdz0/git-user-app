package com.example.dicoding.submisi2Bfaa.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicoding.submisi2Bfaa.view.adapter.UserAdapter
import com.example.dicoding.submisi2Bfaa.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var vBinding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var userQuery: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        vBinding.svUser.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userQuery = query.toString()
                    clearFocus()
                    val getData = mainViewModel.getUser(userQuery)
                    if (userQuery.isEmpty() || getData.equals(null)) {
                        vBinding.rvUsers.adapter = UserAdapter(emptyList())
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    userQuery = newText.toString()
                    if (userQuery.isEmpty()) {
                        vBinding.rvUsers.adapter = UserAdapter(emptyList())
                    }
                    return true
                }
            })
        }

        mainViewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }

        showRecyclerList()
        mainViewModel.listGithubUser.observe(this) { listGithubUser ->
            vBinding.rvUsers.adapter = UserAdapter(listGithubUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showRecyclerList() {
        vBinding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        vBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}