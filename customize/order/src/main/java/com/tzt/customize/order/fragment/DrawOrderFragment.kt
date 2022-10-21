package com.tzt.customize.order.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tzt.common.basedepency.BaseFragment
import com.tzt.common.basedepency.PaintItemModel
import com.tzt.common.basedepency.dpToPx
import com.tzt.customize.order.R
import com.tzt.customize.order.databinding.FragmentDrawOrderBinding
import com.tzt.customize.order.widget.*


/**
 * Description: 绘制顺序
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class DrawOrderFragment: BaseFragment<FragmentDrawOrderBinding>() {
    companion object {
        const val SUPER_ONDRAW = 1
        const val DISPATCH_DRAW = 2
        const val ONDRAW_FOREGROUND = 3
    }

    private val mList = ArrayList<PaintItemModel>()

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDrawOrderBinding.inflate(inflater, container, false)

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun initData() {
        val effect = arguments?.getInt("draw_order_type", -1)
        binding.titleTv.visibility = View.GONE
        mList.clear()
        effect?.let {
            when (it) {
                SUPER_ONDRAW -> {
                    // super.onDraw()
                    mList.apply {
                        val viewImage = ImageView(mContext)
                        viewImage.setImageResource(R.mipmap.view)
                        add(PaintItemModel("自定义view: 继承View类。\n" +
                                "绘制代码写在 super.onDraw() 的上面还是下面都无所谓，甚至，你把 super.onDraw() " +
                                "这行代码删掉都没关系，效果都是一样的——因为在 View 这个类里，onDraw() 本来就是空实现", viewImage))
                        val viewExitImage = ImageView(mContext)
                        viewExitImage.setImageResource(R.mipmap.view_exit)
                        add(PaintItemModel("自定义绘制更为常见的情况是，继承一个具有某种功能的控件，去重写它的 onDraw() ，" +
                                "在里面添加一些绘制代码，做出一个「进化版」的控件：\n" +
                                "这种基于已有控件的自定义绘制，就不能不考虑 super.onDraw() 了：你需要根据自己的需求，" +
                                "判断出你绘制的内容需要盖住控件原有的内容还是需要被控件原有的内容盖住，" +
                                "从而确定你的绘制代码是应该写在 super.onDraw() 的上面还是下面", viewExitImage))
                        val below = SuperOndrawBelowView(mContext)
                        below.setImageResource(R.mipmap.batman)
                        add(PaintItemModel("super.onDraw() 的下面\n绘制内容就会盖住控件原来的内容", below))
                        val above = SuperOndrawAboveView(mContext)
                        above.text = "自定义view继承TextView\nAndroid 里面的绘制都是按顺序的，\n先绘制的内容会被后绘制的盖住。" +
                                "\n比如你在重叠的位置先画圆再画方，\n和先画方再画圆所呈现出来的结果肯定是不同的："
                        add(PaintItemModel("super.onDraw() 的上面\n绘制的内容会被控件的原内容盖住", above))
                    }
                }
                DISPATCH_DRAW -> {
                    // 绘制子View的方法
                    mList.apply {
                        add(PaintItemModel("具体来讲，这里说的「绘制子 View」是通过另一个绘制方法的调用来发生的，这个绘制方法叫做：\n" +
                                "dispatchDraw()。也就是说，在绘制过程中，每个 View 和 ViewGroup 都会先调用 onDraw() 方法来绘制主体，" +
                                "再调用 dispatchDraw() 方法来绘制子 View\n" +
                                "注：\n虽然 View 和 ViewGroup 都有 dispatchDraw() 方法，" +
                                "不过由于 View 是没有子 View 的，所以一般来说 dispatchDraw() " +
                                "这个方法只对 ViewGroup（以及它的子类）有意义。", null))
                        add(PaintItemModel("\n绘制代码写在 super.dispatchDraw() 的下面\n", DispatchDrawBelowView(mContext)))
                        add(PaintItemModel("\n绘制代码写在 super.dispatchDraw() 的上面\n" +
                                "与重写 onDraw() 并把绘制代码写在 super.onDraw()下面 ，显示效果是一致的\n", null))
                    }
                }
                ONDRAW_FOREGROUND -> {
                    // onDrawForeground()
                    mList.apply {
                        add(PaintItemModel("这个方法是 API 23 才引入的，所以在重写这个方法的时候要确认你的 minSdk 达到了 23，不然低版本的手机装上你的软件会没有效果。\n" +
                                "在 onDrawForeground() 中，会依次绘制滑动边缘渐变、滑动条和前景。\n" +
                                "这三部分是依次绘制的，但它们被一起写进了 onDrawForeground() 方法里，\n" +
                                "所以绘制内容只能放在这三部分上面或者下面，在他们之间插入绘制，是做不到的", null))
                        val below = OnDrawForegeoundBelowView(mContext)
                        below.setImageResource(R.mipmap.batman)
                        below.foreground = ColorDrawable(Color.parseColor("#88000000"))
                        add(PaintItemModel("绘制内容代码在 super.onDrawForeground() 的下面\n" +
                                "绘制内容会盖住滑动边缘渐变、滑动条以及前景", below))
                        val above = OnDrawForegroundAboveView(mContext)
                        above.setImageResource(R.mipmap.batman)
                        above.foreground = ColorDrawable(Color.parseColor("#88000000"))
                        add(PaintItemModel("绘制内容代码在 super.onDrawForeground() 的上面\n" +
                                "绘制内容会盖住子 View，但被滑动边缘渐变、滑动条以及前景盖住", above))
                    }
                }
                else -> {}
            }
        }

        binding.recyclerShader.layoutManager = LinearLayoutManager(mContext).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.recyclerShader.adapter = EffectAdapter()
    }

    inner class EffectAdapter: RecyclerView.Adapter<EffectAdapter.ShaderViewHolder>() {
        inner class ShaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val title: TextView = itemView.findViewById(R.id.shaderTypeTv)
            val contextView: FrameLayout = itemView.findViewById(R.id.shaderContentView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShaderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_shader, parent, false)
        )

        override fun getItemCount() = mList.size

        override fun onBindViewHolder(holder: ShaderViewHolder, position: Int) {
            val model = mList[position]

            holder.title.setTextColor(Color.parseColor("#006400"))
            holder.title.text = model.title
            if (model.view == null) {
                holder.contextView.visibility = View.GONE
            } else {
                holder.contextView.visibility = View.VISIBLE
                val viewParent = model.view!!.parent
                if (viewParent != null) {
                    val vp = viewParent as ViewGroup
                    vp.removeView(model.view)
                }
                holder.contextView.removeAllViews()
                val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dpToPx(200).toInt())
                holder.contextView.addView(model.view, params)
            }
        }
    }
}