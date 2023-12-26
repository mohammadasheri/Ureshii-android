package ai.hara.ureshii.util

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar


fun Activity.showSnackBar(view: View = this.findViewById(android.R.id.content), message: String, duration: Int = Snackbar.LENGTH_LONG) {
    KeyboardUtils.hideSoftInput(this)
    Snackbar.make(view, message, duration).show()
}