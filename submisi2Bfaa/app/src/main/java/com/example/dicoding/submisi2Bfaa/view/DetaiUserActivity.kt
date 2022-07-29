package com.example.dicoding.submisi2Bfaa.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dicoding.submisi2Bfaa.R
import com.example.dicoding.submisi2Bfaa.view.adapter.DetailUserAdapter
import com.example.dicoding.submisi2Bfaa.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetaiUserActivity : AppCompatActivity() {

    private lateinit var vBinding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        supportActionBar?.apply {
            title = getString(R.string.user_detail)
            setDisplayHomeAsUpEnabled(true)
        }

        val username = intent.getStringExtra(EXTRA_USER)
        detailViewModel.getDetailUser(username)

        detailViewModel.userData.observe(this) { userData ->
            vBinding.apply {
                tvUsername.text = userData.username
                tvName.text = userData?.name ?: "-"
                tvLocation.text = userData?.location ?: "-"
                tvCompany.text = userData?.company ?: "-"
                tvFollowers.text = userData.followers.toString()
                tvFollowing.text = userData.following.toString()



                Glide.with(this@DetaiUserActivity)
                    .load(userData.avatar)
                    .apply(RequestOptions
                        .circleCropTransform()
                        .placeholder(R.drawable.ic_account)
                        .error(R.drawable.ic_account)
                    ).into(ivAvatar)

                val fragment = mutableListOf<Fragment>(
                    FollowFragment.newInstance(FollowFragment.FOLLOWING),
                    FollowFragment.newInstance(FollowFragment.FOLLOWERS)
                )

                val fragmentTitle = mutableListOf(
                    getString(R.string.following),
                    getString(R.string.followers)
                )

                val detailUserAdapter = DetailUserAdapter(this@DetaiUserActivity, fragment)
                viewPager.adapter = detailUserAdapter

                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = fragmentTitle[position]
                }.attach()

                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        if (tab?.position == FollowFragment.FOLLOWERS) {
                            detailViewModel.getFollowers(userData.username)
                        } else {
                            detailViewModel.getFollowing(userData.username)
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {}
                    override fun onTabReselected(tab: TabLayout.Tab?) {}
                })
                detailViewModel.getFollowing(userData.username)
            }
        }

        detailViewModel.errorText.observe(this) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        vBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }

}