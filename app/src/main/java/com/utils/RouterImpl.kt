package com.utils

import android.content.Context
import android.content.Intent
import com.mainScreen.MainActivity
import com.core.utils.Router

class RouterImpl : Router {
    override fun navigateToMainActivity(context: Context) {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}