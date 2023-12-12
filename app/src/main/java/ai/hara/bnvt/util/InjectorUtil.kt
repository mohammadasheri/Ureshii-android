package ai.hara.bnvt.util

import ai.hara.bnvt.data.model.Song
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.bumptech.glide.load.model.GlideUrl
import java.io.*
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


inline fun <T> LiveData<T>.observeNotNull(
    owner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    observe(owner) { if (it != null) observer(it) }
}


fun createLinearGradientDrawable2(color: String, cornerRadius: Float): GradientDrawable {
    val colors = IntArray(2)
    colors[0] = Color.parseColor("#$color")
    colors[1] = Color.parseColor("#000000")
    val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors)
    gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
    gradientDrawable.cornerRadius = cornerRadius
    return gradientDrawable
}

object InjectorUtils {
    private var token: String? = null
    fun getGlideUrl(image: String?): Any? {
        if (image.isNullOrEmpty()) {
            return null
        }
        if (token == null)
            token = getToken()

        return GlideUrl("${getRandomHost()}$image") { mapOf(Pair("TK", token)) }
    }

    fun getGlideUrl(id: Int?): Any? {
        if (id == null) {
            return null
        }
        return GlideUrl("${getRandomHost()}song/picture/download/$id") {
            mapOf(
                Pair(
                    "TK",
                    getToken()
                )
            )
        }
    }

    private fun getAbsoluteUrl(song: Song): String = "${getRandomHost()}song/download/${song.id}"

    private const val MiB = 1024 * 1024.toLong()

    fun convertBitmapToJPEGFile(
        selectedImage: Uri,
        context: Context
    ): File {
        val imageBitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, selectedImage)
        val imgInputStream: InputStream = context.contentResolver.openInputStream(selectedImage)!!
        val size: Int = imgInputStream.available()

        val file = File(context.cacheDir, "photo.jpeg")
        file.createNewFile()
        val bos = ByteArrayOutputStream()
        var quality = 100
        if (size > MiB) {
            quality = ((MiB * 100) / size).toInt()
        }
//        if (convertToSquare)
//            imageBitmap = createSquaredBitmap(imageBitmap)
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos)
        val fos = FileOutputStream(file)
        fos.write(bos.toByteArray())
        fos.flush()
        fos.close()
        return file


    }

    fun convertNumberToPersianTime(millis: Long): String {
        val formatter: DateFormat = SimpleDateFormat("mm:ss", Locale("fa", "IR"))
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.format(Date(millis))
    }

    fun convertNumberToPersianNumber(milis: Long?): String {
        val nf = NumberFormat.getInstance(Locale("fa", "IR"))
        return nf.format(milis ?: 0)
    }

    fun readFile(file: File): ByteArray? {
        try {
            val inputs: InputStream = FileInputStream(file)
            val buf = ByteArray(inputs.available())
            while (inputs.read(buf) !== -1);
            return buf
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    fun setHomeFm(fm: FragmentManager) {
        homeFM = fm
    }

    fun setSearchFm(fm: FragmentManager) {
        searchFM = fm
    }

    fun setLibraryFm(fm: FragmentManager) {
        libraryFM = fm
    }

    private lateinit var libraryFM: FragmentManager
    private lateinit var searchFM: FragmentManager
    private lateinit var homeFM: FragmentManager

    fun getToken(): String = ""
    fun getId(): Int = 0
}

const val PHONEREG = "^(\\+98|0|98|0098)?9\\d{9}\$"
var PHONEPATTERN: Pattern = Pattern.compile(PHONEREG)
fun CharSequence.isPhoneNumber(): Boolean = PHONEPATTERN.matcher(this).find()

@Throws(Exception::class)
fun CharSequence.getPhoneNumber(): CharSequence {
    if (!isPhoneNumber())
        throw Exception("not valid phone number")
    return "+98${subSequence(length - 10, length)}"

}

const val EMAILREG = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
var PATTERN: Pattern = Pattern.compile(EMAILREG)
fun CharSequence.isEmailADDRESS(): Boolean = PATTERN.matcher(this).find()
var steper = 1
fun getRandomHost(): String = "http://192.168.1.175:8080/"