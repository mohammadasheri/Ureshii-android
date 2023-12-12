package ai.hara.bnvt.util

import android.text.Editable
import android.text.TextWatcher

abstract class AbstractTextWatcher : TextWatcher {

    override fun afterTextChanged(editable: Editable) {
        validate()
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    abstract fun validate()
}