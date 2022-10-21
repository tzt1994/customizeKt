package com.tzt.customize.paint.fragment.effect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.paint.databinding.FragmentShaowLayerBinding

/**
 * Description:阴影
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class ShadowLayerFragment : BaseFragment<FragmentShaowLayerBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShaowLayerBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val des = "setShadowLayer(float radius, float dx, float dy, int shadowColor)\n" +
                "radius: 阴影的模糊范围\ndx dy: 阴影的偏移量\nshadowColor: 阴影的颜色\n" +
                "\n如果要清除阴影层，使用 clearShadowLayer()\n" +
                "注意:\n\t\t· 在硬件加速开启的情况下， setShadowLayer() 只支持文字的绘制，文字之外的绘制必须关闭硬件加速才能正常绘制阴影。\n" +
                "\t\t· 如果 shadowColor 是半透明的，阴影的透明度就使用 shadowColor 自己的透明度；而如果 shadowColor 是不透明的，阴影的透明度就使用 paint 的透明度。"
        binding.tvShadowLayer.text = des
    }
}