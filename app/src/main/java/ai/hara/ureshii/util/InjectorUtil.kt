package ai.hara.ureshii.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


inline fun <T> LiveData<T>.observeNotNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(owner) { if (it != null) observer(it) }
}

fun getHostURL(): String = "http://192.168.0.109:8080/ureshii/"