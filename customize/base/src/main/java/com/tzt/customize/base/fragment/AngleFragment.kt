package com.tzt.customize.base.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.base.databinding.FragmentAngleBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/21
 */
class AngleFragment: BaseFragment<FragmentAngleBinding>() {
    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAngleBinding.inflate(inflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun initData() {
        binding.apply {
            tvDescrip.text = "一.角度与弧度"
            tvSubDescrip.text = "简单来说就是为了方便，为了精确描述一个角的大小引入了角度与弧度的概念。\n" +
                    "\n" +
                    "由于两者进制是不同的(角度是60进制，弧度是10进制),在合适的地方使用合适的单位来描述会更加方便。\n" +
                    "\n" +
                    "例如： 角度是60进位制，遇到30°6′这样的角，应该转化为10进制的30.1°。但弧度就不需要，因为弧度本身就是十进制的实数。"

            tvDefinition.text = "二.角度与弧度的定义"
            tvSubDefinition.text = "角度和弧度一样都是描述角的一种度量单位，下面是它们的定义：\n" +
                    "角度\t两条射线从圆心向圆周射出，形成一个夹角和夹角正对的一段弧。当这段弧长正好等于圆周长的360分之一时，两条射线的夹角的大小为1度.\n" +
                    "弧度\t两条射线从圆心向圆周射出，形成一个夹角和夹角正对的一段弧。当这段弧长正好等于圆的半径时，两条射线的夹角大小为1弧度."

            tvConversion.text = "三.角度和弧度的换算关系"
            tvSubConversion.text = "圆一周对应的角度为360度(角度)，对应的弧度为2π弧度。\n" +
                    "\n" +
                    "故得等价关系:360(角度) = 2π(弧度) ==> 180(角度) = π(弧度)\n" +
                    "\n" +
                    "由等价关系可得如下换算公式:\n" +
                    "\n" +
                    "rad 是弧度， deg 是角度\n" +
                    "公式" +
                    "rad = deg x π / 180\t2π ＝ 360 x π / 180\n" +
                    "deg = rad x 180 / π\t360 ＝ 2π x 180 / π"

            tvDetail.text = "四.一些细节问题"
            tvSubDetail.text = "由于默认屏幕坐标系和常见数学坐标系的小差别(坐标系问题点这里)，所以在角上必然也会存在一些区别，例如：\n" +
                    "\n" +
                    "在常见的数学坐标系中角度增大方向为逆时针，\n" +
                    "\n" +
                    "在默认的屏幕坐标系中角度增大方向为顺时针。"
        }
    }
}