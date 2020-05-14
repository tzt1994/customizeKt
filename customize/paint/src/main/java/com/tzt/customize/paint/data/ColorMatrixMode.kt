package com.tzt.customize.paint.data


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
object ColorMatrixMode {

    const val NONE = -1
    const val SATURATION = 0
    const val GREY_SCALE = 1
    const val INVERT = 2
    const val RGB_TO_BGR = 3
    const val SEPIA = 4
    const val BLACK_AND_WHITE = 5
    const val BRIGHT = 6
    const val VINTAGE_PINHOLE = 7
    const val KODACHROME = 8
    const val TECHNICOLOR = 9

    fun getColorMatrixModeDes(mode: Int): String {
        return when (mode) {
            NONE -> "标准模式"
            SATURATION -> "饱和度"
            GREY_SCALE -> "灰度，即常见的黑白照片"
            INVERT -> "色彩反转"
            RGB_TO_BGR -> "将Red与Blue两个颜色通道反转，可理解为另一种色彩翻转"
            SEPIA -> "老照片"
            BLACK_AND_WHITE -> "硬像，纯黑白，没有灰色"
            BRIGHT -> "明亮"
            VINTAGE_PINHOLE -> "针孔"
            KODACHROME -> "柯达"
            TECHNICOLOR -> "染印"
            else -> ""
        }
    }

    fun getColorMatrixModeDesEnglish(mode: Int): String {
        return when (mode) {
            NONE -> "None"
            SATURATION -> "Saturation"
            GREY_SCALE -> "Grey Scale"
            INVERT -> "Invert"
            RGB_TO_BGR -> "RGB to BGR"
            SEPIA -> "Sepia"
            BLACK_AND_WHITE -> "Black & White"
            BRIGHT -> "Bright"
            VINTAGE_PINHOLE -> "Vintage Pinhole"
            KODACHROME -> "Kodachrome"
            TECHNICOLOR -> "Technicolor"
            else -> ""
        }
    }
}