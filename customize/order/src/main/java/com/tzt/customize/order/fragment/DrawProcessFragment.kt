package com.tzt.customize.order.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.order.databinding.FragmentDrawProcessBinding


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/12
 */
class DrawProcessFragment: BaseFragment<FragmentDrawProcessBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDrawProcessBinding.inflate(inflater, container, false)

    override fun initData() {
        val dp1 = "\n一个完整的绘制过程会依次绘制以下几个内容：\n\n" +
                "1.背景\n" +
                "2.主体（onDraw()）\n" +
                "3.子 View（dispatchDraw()）\n" +
                "4.滑动边缘渐变和滑动条\n" +
                "5.前景\n\n" +
                "一般来说，一个 View（或 ViewGroup）的绘制不会这几项全都包含，但必然逃不出这几项，" +
                "并且一定会严格遵守这个顺序。例如通常一个 LinearLayout 只有背景和子 View，" +
                "那么它会先绘制背景再绘制子 View；一个 ImageView 有主体，" +
                "有可能会再加上一层半透明的前景作为遮罩，那么它的前景也会在主体之后进行绘制。" +
                "需要注意，前景的支持是在 Android 6.0（也就是 API 23）才加入的；之前其实也有，" +
                "不过只支持 FrameLayout，而直到 6.0 才把这个支持放进了 View 类里。\n\n" +
                "这其中的第 2、3 两步，请滑动tab；第 1 步——背景，它的绘制发生在一个叫 drawBackground()" +
                " 的方法里，但这个方法是 private 的，不能重写，你如果要设置背景，只能用自带的 API 去设置（xml " +
                "布局文件的 android:background 属性以及 Java 代码的 View.setBackgroundXxx() 方法 ）" +
                "，而不能自定义绘制；而第 4、5 两步——滑动边缘渐变和滑动条以及前景，" +
                "这两部分被合在一起放在了 onDrawForeground() 方法里，这个方法是可以重写的。\n"
        binding.tvDp1.text = dp1
        val dp2 = "\n滑动边缘渐变和滑动条可以通过 xml 的 android:scrollbarXXX 系列属性或 Java 代码的 " +
                "View.setXXXScrollbarXXX() 系列方法来设置；前景可以通过 xml 的 android:foreground " +
                "属性或 Java 代码的 View.setForeground() 方法来设置。而重写 onDrawForeground() 方法，" +
                "并在它的 super.onDrawForeground() 方法的上面或下面插入绘制代码，则可以控制绘制内容和滑动边缘渐变、滑动条以及前景的遮盖关系\n"
        binding.tvDp2.text = dp2
    }
}