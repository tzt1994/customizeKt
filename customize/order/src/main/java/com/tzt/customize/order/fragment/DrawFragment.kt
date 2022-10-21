package com.tzt.customize.order.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tzt.common.basedepency.BaseFragment
import com.tzt.customize.order.databinding.FragmentDrawBinding


/**
 * Description: draw() 总调度方法
 *
 * @author tangzhentao
 * @since 2020/5/12
 */
class DrawFragment: BaseFragment<FragmentDrawBinding>() {

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDrawBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.apply {
            val draw1 = "draw() 是绘制过程的总调度方法。一个 View 的整个绘制过程都发生在 draw() 方法里。前面讲到的背景、主体、子 View " +
                    "、滑动相关以及前景的绘制，它们其实都是在 draw() 方法里的。"
            tvDraw1.text = draw1

            val draw2 = "从上面的代码可以看出，onDraw() dispatchDraw() onDrawForeground() 这三个方法在 draw() 中被依次调用，" +
                    "因此它们的遮盖关系也就像前面所说的——dispatchDraw() 绘制的内容盖住 onDraw() 绘制的内容；onDrawForeground() " +
                    "绘制的内容盖住 dispatchDraw() 绘制的内容。而在它们的外部，则是由 draw() 这个方法作为总的调度。所以，你也可以重写 draw() 方法来做自定义的绘制。"
            tvDraw2.text = draw2

            val draw4 = "由于 draw() 是总调度方法，所以如果把绘制代码写在 super.draw() 的下面，" +
                    "那么这段代码会在其他所有绘制完成之后再执行，也就是说，它的绘制内容会盖住其他的所有绘制内容。\n" +
                    "\n" +
                    "它的效果和重写 onDrawForeground()，并把绘制代码写在 super.onDrawForeground() 下面时的效果是一样的：都会盖住其他的所有内容。"
            tvDraw4.text = draw4

            val draw6 = "同理，由于 draw() 是总调度方法，所以如果把绘制代码写在 super.draw() 的上面，那么这段代码会在其他所有绘制之前被执行" +
                    "，所以这部分绘制内容会被其他所有的内容盖住，包括背景。是的，背景也会盖住它。\n\n" +
                    "例如我有一个 EditText："
            tvDraw6.text = draw6

            val draw7 = "它下面的那条横线，是 EditText 的背景。所以如果我想给这个 EditText 加一个绿色的底，" +
                    "我不能使用给它设置绿色背景色的方式，因为这就相当于是把它的背景替换掉，从而会导致下面的那条横线消失：\n" +
                    "<EditText\n" +
                    "            android:id=\"@+id/etDraw2\"\n" +
                    "            android:layout_width=\"match_parent\"\n" +
                    "            android:layout_height=\"48dp\"\n" +
                    "            android:text=\"Hello TangZhenTao\"\n" +
                    "            android:background=\"#66bb6a\"/>\n"
            tvDraw7.text = draw7

            val draw8 = "\nEditText：我到底是个 EditText 还是个 TextView？傻傻分不清楚。\n" +
                    "在这种时候，你就可以重写它的 draw() 方法，然后在 super.draw() 的上方插入代码，以此来在所有内容的底部涂上一片绿色：\n"
            tvDraw8.text = draw8

            val draw9 = "关于绘制方法，有两点需要注意一下：\n" +
                    "\n" +
                    "1.出于效率的考虑，ViewGroup 默认会绕过 draw() 方法，换而直接执行 dispatchDraw()，以此来简化绘制流程。所以如果你自定义了某个" +
                    " ViewGroup 的子类（比如 LinearLayout）并且需要在它的除 dispatchDraw() 以外的任何一个绘制方法内绘制内容，你可能会需要调用 " +
                    "View.setWillNotDraw(false) 这行代码来切换到完整的绘制流程（是「可能」而不是「必须」的原因是，有些 ViewGroup " +
                    "是已经调用过 setWillNotDraw(false) 了的，例如 ScrollView）。\n\n" +
                    "2.有的时候，一段绘制代码写在不同的绘制方法中效果是一样的，这时你可以选一个自己喜欢或者习惯的绘制方法来重写。但有一个例外：" +
                    "如果绘制代码既可以写在 onDraw() 里，也可以写在其他绘制方法里，那么优先写在 onDraw() ，因为 Android 有相关的优化，" +
                    "可以在不需要重绘的时候自动跳过 onDraw() 的重复执行，以提升开发效率。享受这种优化的只有 onDraw() 一个方法。"
            tvDraw9.text = draw9
        }
    }
}