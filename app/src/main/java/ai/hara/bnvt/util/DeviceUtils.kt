package ai.hara.bnvt.util

import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings
import java.util.Locale


private fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    return if (model.lowercase(Locale.getDefault()).startsWith(manufacturer.lowercase(Locale.getDefault()))) {
        model
    } else {
        "$manufacturer $model"
    }
}

