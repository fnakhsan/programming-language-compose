package com.example.programminglanguagecompose.utils

import android.content.Context
import androidx.annotation.StringRes
import com.example.programminglanguagecompose.R

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(@StringRes val id: Int, vararg val args: Any) : UiText()

    companion object {
        fun unknownError(): UiText {
            return StringResource(R.string.text_error_unknown)
        }
        fun UiText.asString(context: Context) =
            when (this) {
                is DynamicString -> value
                is StringResource -> context.getString(id, *args)
            }
    }
}