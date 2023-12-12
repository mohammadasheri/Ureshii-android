package ai.hara.bnvt.util

import android.content.Context
import android.util.AttributeSet
import android.view.WindowInsets
import androidx.constraintlayout.widget.ConstraintLayout

class CustomConstraintLayout : ConstraintLayout {
    private val mInsets = IntArray(4)

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr)

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        mInsets[0] = insets.systemWindowInsetLeft
        mInsets[1] = insets.systemWindowInsetTop
        mInsets[2] = insets.systemWindowInsetRight
        return super.onApplyWindowInsets(
            insets.replaceSystemWindowInsets(
                0, 0, 0,
                insets.systemWindowInsetBottom
            )
        )
    }
}