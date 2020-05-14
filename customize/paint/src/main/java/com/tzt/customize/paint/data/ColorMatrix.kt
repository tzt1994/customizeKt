package com.tzt.customize.paint.data

/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
object ColorMatrix {
    private val COMMON =
        floatArrayOf(1f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 1f, 0f)

    fun common(): FloatArray {
        return COMMON.clone()
    }

    private val GREY_SCALE = floatArrayOf(
        0.33f, 0.59f, 0.11f, 0f, 0f,
        0.33f, 0.59f, 0.11f, 0f, 0f,
        0.33f, 0.59f, 0.11f, 0f, 0f, 0f, 0f, 0f, 1f, 0f
    )

    fun greyScale(): FloatArray {
        return GREY_SCALE.clone()
    }

    private val INVERT = floatArrayOf(
        -1f,
        0f,
        0f,
        0f,
        255f,
        0f,
        -1f,
        0f,
        0f,
        255f,
        0f,
        0f,
        -1f,
        0f,
        255f,
        0f,
        0f,
        0f,
        1f,
        0f
    )

    fun invert(): FloatArray {
        return INVERT.clone()
    }

    private val RGB_TO_BGR =
        floatArrayOf(0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 1f, 0f)

    fun rgbToBgr(): FloatArray {
        return RGB_TO_BGR.clone()
    }

    private val SEPIA = floatArrayOf(
        0.393f, 0.769f, 0.189f, 0f, 0f,
        0.349f, 0.686f, 0.168f, 0f, 0f,
        0.272f, 0.534f, 0.131f, 0f, 0f, 0f, 0f, 0f, 1f, 0f
    )

    fun sepia(): FloatArray {
        return SEPIA.clone()
    }

    private val BRIGHT = floatArrayOf(
        1.438f, -0.122f, -0.016f, 0f, 0f,
        -0.062f, 1.378f, -0.016f, 0f, 0f,
        -0.062f, -0.122f, 1.483f, 0f, 0f, 0f, 0f, 0f, 1f, 0f
    )

    fun bright(): FloatArray {
        return BRIGHT.clone()
    }

    private val BLACK_AND_WHITE = floatArrayOf(
        1.5f, 1.5f, 1.5f, 0f, -255f,
        1.5f, 1.5f, 1.5f, 0f, -255f,
        1.5f, 1.5f, 1.5f, 0f, -255f, 0f, 0f, 0f, 1f, 0f
    )

    fun blackAndWhite(): FloatArray {
        return BLACK_AND_WHITE.clone()
    }

    private val VINTAGE_PINHOLE = floatArrayOf(
        0.6279345635605994f,
        0.3202183420819367f,
        -0.03965408211312453f,
        0f,
        9.651285835294123f,
        0.02578397704808868f,
        0.6441188644374771f,
        0.03259127616149294f,
        0f,
        7.462829176470591f,
        0.0466055556782719f,
        -0.0851232987247891f,
        0.5241648018700465f,
        0f,
        5.159190588235296f,
        0f,
        0f,
        0f,
        1f,
        0f
    )

    fun vintagePinhole(): FloatArray {
        return VINTAGE_PINHOLE.clone()
    }

    private val KODACHROME = floatArrayOf(
        1.1285582396593525f,
        -0.3967382283601348f,
        -0.03992559172921793f,
        0f,
        63.72958762196502f,
        -0.16404339962244616f,
        1.0835251566291304f,
        -0.05498805115633132f,
        0f,
        24.732407896706203f,
        -0.16786010706155763f,
        -0.5603416277695248f,
        1.6014850761964943f,
        0f,
        35.62982807460946f,
        0f,
        0f,
        0f,
        1f,
        0f
    )

    fun kodachrome(): FloatArray {
        return KODACHROME.clone()
    }

    private val TECHNICOLOR = floatArrayOf(
        1.9125277891456083f,
        -0.8545344976951645f,
        -0.09155508482755585f,
        0f,
        11.793603434377337f,
        -0.3087833385928097f,
        1.7658908555458428f,
        -0.10601743074722245f,
        0f,
        -70.35205161461398f,
        -0.231103377548616f,
        -0.7501899197440212f,
        1.847597816108189f,
        0f,
        30.950940869491138f,
        0f,
        0f,
        0f,
        1f,
        0f
    )

    fun technicolor(): FloatArray {
        return TECHNICOLOR.clone()
    }
}