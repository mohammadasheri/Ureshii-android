package ai.hara.bnvt.util

import java.text.DecimalFormat
import kotlin.math.abs

object CurrencyUtils {

    private val farsiChars = arrayOf(
        "\u06F0",
        "\u06F1",
        "\u06F2",
        "\u06F3",
        "\u06F4",
        "\u06F5",
        "\u06F6",
        "\u06F7",
        "\u06F8",
        "\u06F9",
        "\u066C",
        "\u066B"
    )


    fun toEnglish(number: String): String {
        var number = number
        number = number.replace(farsiChars[0].toRegex(), "0")
        number = number.replace(farsiChars[1].toRegex(), "1")
        number = number.replace(farsiChars[2].toRegex(), "2")
        number = number.replace(farsiChars[3].toRegex(), "3")
        number = number.replace(farsiChars[4].toRegex(), "4")
        number = number.replace(farsiChars[5].toRegex(), "5")
        number = number.replace(farsiChars[6].toRegex(), "6")
        number = number.replace(farsiChars[7].toRegex(), "7")
        number = number.replace(farsiChars[8].toRegex(), "8")
        number = number.replace(farsiChars[9].toRegex(), "9")

        return number

    }

    private fun toFarsi(number: String?): String {//1232452
        var number = number

        number = number?.replace("0".toRegex(), farsiChars[0])
        number = number?.replace("1".toRegex(), farsiChars[1])
        number = number?.replace("2".toRegex(), farsiChars[2])
        number = number?.replace("3".toRegex(), farsiChars[3])
        number = number?.replace("4".toRegex(), farsiChars[4])
        number = number?.replace("5".toRegex(), farsiChars[5])
        number = number?.replace("6".toRegex(), farsiChars[6])
        number = number?.replace("7".toRegex(), farsiChars[7])
        number = number?.replace("8".toRegex(), farsiChars[8])
        number = number?.replace("9".toRegex(), farsiChars[9])
        number = number?.replace(",".toRegex(), farsiChars[10])
        number = number?.replace(".", farsiChars[11])

        return number!!

    }

    fun percentDownloadFormat(value: Double?): String {
        val formatter = DecimalFormat("##")
        return toFarsi(formatter.format(value!! * 100)) + "\u066A"
    }


    private fun valueFormat(value: Float): String {
        val formatter = DecimalFormat("###.##")
        return toFarsi(formatter.format(value.toDouble()))
    }

    fun toShortValue(value: Long): String {
        var value = value
        var type = ""
        var mid = " "

        if (value < 0) {
            mid = "- "
        }

        value = abs(value)
        if (value < 1000) {
            return mid + valueFormat(value.toFloat()) + type
        }
        if (value < 1000000) {
            type = " K"
            return mid + valueFormat(value.toFloat() / 1000) + type
        }
        if (value < 1000000000) {
            type = " M"
            return mid + valueFormat(value.toFloat() / 1000000) + type
        }

        type = " B"
        return mid + valueFormat(value.toFloat() / 1000000000) + type

    }


}
