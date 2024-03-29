package com.tzt.customize.paint.fragment.color

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.paint.databinding.FragmentColorMatrixBinding
import com.tzt.customize.paint.ui.ColorMatrixColorFilterActivity

/**
 * Description:颜色矩阵
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class ColorMatrixFragment: BaseFragment<FragmentColorMatrixBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentColorMatrixBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.tvColorMatrix.text = "ColorMatrixColorFilter 使用一个 ColorMatrix 来对颜色进行处理。 ColorMatrix 这个类，内部是一个 4x5 的矩阵:\n" +
                "\t\t[a, b, c, d, e\n\t\tf, g, h, i, j\n\t\tk, l, m, n, o\n\t\tp, q, r, s, t]\n通过计算， ColorMatrix 可以把要绘制的像素进行转换。对于颜色 [R, G, B, A] ，转换算法是这样的:\n" +
                "\t\tR’ = a*R + b*G + c*B + d*A + e\n\t\tG’ = f*R + g*G + h*B + i*A + j\n\t\tB’ = k*R + l*G + m*B + n*A + o\n\t\tA’ = p*R + q*G + r*B + s*A + t"

        binding.btnColorMatrix.setOnClickListener {
            startActivity(Intent(mContext, ColorMatrixColorFilterActivity::class.java))
        }
    }
}