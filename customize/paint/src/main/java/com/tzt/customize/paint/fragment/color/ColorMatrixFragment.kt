package com.tzt.customize.paint.fragment.color

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.paint.R
import com.tzt.customize.paint.ui.ColorMatrixColorFilterActivity
import kotlinx.android.synthetic.main.fragment_color_matrix.*


/**
 * Description:颜色矩阵
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class ColorMatrixFragment: BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_color_matrix, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvColorMatrix.text = "ColorMatrixColorFilter 使用一个 ColorMatrix 来对颜色进行处理。 ColorMatrix 这个类，内部是一个 4x5 的矩阵:\n" +
                "\t\t[a, b, c, d, e\n\t\tf, g, h, i, j\n\t\tk, l, m, n, o\n\t\tp, q, r, s, t]\n通过计算， ColorMatrix 可以把要绘制的像素进行转换。对于颜色 [R, G, B, A] ，转换算法是这样的:\n" +
                "\t\tR’ = a*R + b*G + c*B + d*A + e\n\t\tG’ = f*R + g*G + h*B + i*A + j\n\t\tB’ = k*R + l*G + m*B + n*A + o\n\t\tA’ = p*R + q*G + r*B + s*A + t"

        btnColorMatrix.setOnClickListener {
            startActivity(Intent(mContext, ColorMatrixColorFilterActivity::class.java))
        }
    }
}