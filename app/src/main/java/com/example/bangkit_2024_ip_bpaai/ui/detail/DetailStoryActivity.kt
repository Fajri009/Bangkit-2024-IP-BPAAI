package com.example.bangkit_2024_ip_bpaai.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_2024_ip_bpaai.R

class DetailStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_story)
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}