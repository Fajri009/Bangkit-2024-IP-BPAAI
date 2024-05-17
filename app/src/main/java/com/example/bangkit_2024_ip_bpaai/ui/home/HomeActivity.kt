package com.example.bangkit_2024_ip_bpaai.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bangkit_2024_ip_bpaai.R
import com.example.bangkit_2024_ip_bpaai.data.local.User
import com.example.bangkit_2024_ip_bpaai.data.local.UserPreferences
import com.example.bangkit_2024_ip_bpaai.data.remote.response.ListStoryItem
import com.example.bangkit_2024_ip_bpaai.databinding.ActivityHomeBinding
import com.example.bangkit_2024_ip_bpaai.ui.account.AccountActivity
import com.example.bangkit_2024_ip_bpaai.ui.add.AddStoryActivity
import com.example.bangkit_2024_ip_bpaai.ui.detail.DetailStoryActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var userModel: User
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        viewModel.getStories(userModel.token!!)

        viewModel.listStory.observe(this) {
            setStoryData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        addStory()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.translate -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }
            R.id.account -> {
                val intent = Intent(this@HomeActivity, AccountActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun setStoryData(story: List<ListStoryItem>) {
        val layoutInflater = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutInflater

        val adapter = StoryAdapter(story)
        adapter.submitList(story)
        binding.rvStory.adapter = adapter

        adapter.setOnItemClickCallBack(object: StoryAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: ListStoryItem) {
                showSelectedStory(data)
            }
        })
    }

    private fun showSelectedStory(data: ListStoryItem) {
        val moveWithParcelableIntent = Intent(this@HomeActivity, DetailStoryActivity::class.java)
        moveWithParcelableIntent.putExtra(DetailStoryActivity.EXTRA_STORY, data.id)
        startActivity(moveWithParcelableIntent)
    }

    private fun addStory() {
        binding.fabAddStory.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }
}