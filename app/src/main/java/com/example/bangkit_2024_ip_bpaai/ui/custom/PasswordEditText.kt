package com.example.bangkit_2024_ip_bpaai.ui.custom

import android.content.Context
import android.text.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.example.bangkit_2024_ip_bpaai.R

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
): AppCompatEditText(context, attrs) {
    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 8) {
                    setError(resources.getString(R.string.invalid_password), null)
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }
}