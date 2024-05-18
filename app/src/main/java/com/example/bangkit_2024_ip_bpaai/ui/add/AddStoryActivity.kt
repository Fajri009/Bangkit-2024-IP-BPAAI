package com.example.bangkit_2024_ip_bpaai.ui.add

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bangkit_2024_ip_bpaai.data.local.User
import com.example.bangkit_2024_ip_bpaai.data.local.UserPreferences
import com.example.bangkit_2024_ip_bpaai.databinding.ActivityAddStoryBinding
import com.example.bangkit_2024_ip_bpaai.ui.home.HomeActivity
import com.example.bangkit_2024_ip_bpaai.utils.getImageUri
import com.example.bangkit_2024_ip_bpaai.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding
    private var currentImageUri: Uri? = null
    private val viewModel: AddStoryViewModel by viewModels()

    private lateinit var userModel: User
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        back()

        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnGallery.setOnClickListener { startGallery() }

        upload()

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) { // Apabila null, artinya belum ada media yang dipilih
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.ivStory.setImageURI(it)
        }
    }

    private fun upload() {
        binding.btnUpload.setOnClickListener {
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, this)
                val desc = binding.edDesc.text.toString()

                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val requestBody = desc.toRequestBody("text/plain".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "photo",
                    imageFile.name,
                    requestImageFile
                )

                viewModel.addStories(userModel.token!!, multipartBody, requestBody)

                finish()
            }
        }
    }

    private fun back() {
        binding.ivBack.setOnClickListener {
            finish()
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