package ai.hara.bnvt.util

import android.content.Context


fun saveCredentials(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("access_token", token).apply()
}

fun getToken(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("token", "") ?: "";
}