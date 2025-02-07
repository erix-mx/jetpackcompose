package io.github.afalabarce.jetpackcompose.utilities

import io.github.afalabarce.jetpackcompose.svg.AndroidResourceParser
import io.github.afalabarce.jetpackcompose.svg.ResourceCollector
import io.github.afalabarce.jetpackcompose.svg.Vector2SvgConverter
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(strFormat: String = "dd/MM/yyyy"):String = SimpleDateFormat(strFormat).format(this)
fun Int.format(strFormat: String = "#,##0"):String = DecimalFormat(strFormat).format(this)
fun Long.format(strFormat: String = "#,##0"):String = DecimalFormat(strFormat).format(this)
fun Float.format(strFormat: String = "#,##0.00"):String = DecimalFormat(strFormat).format(this)
fun Double.format(strFormat: String = "#,##0.00"):String = DecimalFormat(strFormat).format(this)
fun <T>Boolean.iif(ifTrue: T, ifFalse: T): T = if (this) ifTrue else ifFalse
fun Calendar.today(): Date? = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).let { f ->
    val dateStr = f.format(this.time)
    f.parse(dateStr)
}

fun String.toDate(format: String = "yyyy-MM-dd"): Date? = try{
    SimpleDateFormat(format).parse(this)
}catch (ex: Exception){
    null
}

fun CharSequence.toDate(format: String = "yyyy-MM-dd"): Date? = try{
    SimpleDateFormat(format, Locale.getDefault()).parse(this.toString())
}catch (ex: Exception){
    null
}

fun String.toSvg(charset: Charset = Charsets.UTF_8): String{
    try {
        val resourceParser = AndroidResourceParser(this)
        val colorCollector =
            ResourceCollector().apply { addResources(resourceParser.values("color")) }
        val svgConverter = Vector2SvgConverter(colorCollector)
        val outputStream = ByteArrayOutputStream(0)

        svgConverter.convert(this.byteInputStream(Charsets.UTF_8), outputStream)

        return String(outputStream.toByteArray(), charset)
    }catch (_: Exception){
        return this
    }
}