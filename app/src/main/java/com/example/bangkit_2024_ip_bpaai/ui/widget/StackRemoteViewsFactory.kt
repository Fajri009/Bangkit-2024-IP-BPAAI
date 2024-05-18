package com.example.bangkit_2024_ip_bpaai.ui.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.*
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import com.example.bangkit_2024_ip_bpaai.R
import com.example.bangkit_2024_ip_bpaai.data.remote.response.*
import com.example.bangkit_2024_ip_bpaai.data.remote.retrofit.ApiConfig
import com.example.bangkit_2024_ip_bpaai.utils.convertListStoryToBitmap
import retrofit2.*

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {
    private val listStory = MutableLiveData<List<ListStoryItem>>()
    private val mWidgetItems = ArrayList<Bitmap>()

    override fun onCreate() {
        TODO("Not yet implemented")
    }

    override fun onDataSetChanged() {
        val client = ApiConfig.getApiService().getStories(TEMP_TOKEN)
        client.enqueue(object : Callback<Story> {
            override fun onResponse(call: Call<Story>, response: Response<Story>) {
                if (response.isSuccessful) {
                    val listResponse = response.body()?.listStory
                    if (listResponse != null) {
                        mWidgetItems.clear()
                        mWidgetItems.addAll(convertListStoryToBitmap(mContext, listResponse))
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Story>, e: Throwable) {
                Log.e(TAG, "onFailure: ${e.message}")
            }
        })
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int = listStory.value!!.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setImageViewBitmap(R.id.iv_story, mWidgetItems[position])

        val extras = bundleOf(
            ImageBannerWidget.EXTRA_ITEM to position
        )

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.iv_story, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun hasStableIds(): Boolean {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TEMP_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLWZWNkFCelJ6QzFlOE9RckkiLCJpYXQiOjE2NTEwMjE4MzB9.fNi8G9VXnv8Sg1EHJq2KHOeMg_tbhLuo2Hqd6YMacK4"
        private const val TAG = "StackRemoteViewsFactory"
    }
}