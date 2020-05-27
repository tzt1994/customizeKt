package com.tzt.customizekt.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tzt.common.basedepency.BaseFragment
import com.tzt.common.basedepency.CustomModel
import com.tzt.customize.order.DrawOrderActivity
import com.tzt.customize.paint.ui.EffectActivity
import com.tzt.customize.paint.ui.PaintColorActivity
import com.tzt.customize.paint.ui.TextActivity
import com.tzt.customize.path.bezier.BezierActivity
import com.tzt.customize.propertyanimation.AnimatorActivity
import com.tzt.customize.transform.ClipTransFormActivity
import com.tzt.customizekt.R
import com.tzt.custom.canvas.ui.CanvasDrawActivity
import com.tzt.custom.canvas.ui.ScrollerAcitvity
import com.tzt.customize.base.ClassicProcessActivity
import com.tzt.customize.base.CustomBaseActivity
import kotlinx.android.synthetic.main.fragment_customize.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/14
 */
class CustomizeFragment: BaseFragment() {
    private val pageList = ArrayList<CustomModel>()

    override fun layoutResID(): Int {
        return R.layout.fragment_customize
    }

    override fun initData() {
        pageList.apply {
            add(CustomModel("基础", "坐标系，角度弧度，颜色", CustomBaseActivity::class.java))
            add(CustomModel("分类与流程", "自定义view的分类，以及函数调用链", ClassicProcessActivity::class.java))
            add(CustomModel("自定义View", "绘制基础canvas.DrawXXX()", CanvasDrawActivity::class.java))
            add(CustomModel("Paint", "Paint中对颜色的处理\n(基本颜色，ColorFilter，Xfermode)", PaintColorActivity::class.java))
            add(CustomModel("Paint", "Paint中效果详解", EffectActivity::class.java))
            add(CustomModel("Paint", "Paint中绘制文字的详解", TextActivity::class.java))
            add(CustomModel("自定义View", "Clip(裁剪), 几何变换详解", ClipTransFormActivity::class.java))
            add(CustomModel("自定义View", "绘制顺序", DrawOrderActivity::class.java))
            add(CustomModel("属性动画", "ObjectAnimator", AnimatorActivity::class.java))
            add(CustomModel("让view滑动起来", "Scroller , VelocityTracker", ScrollerAcitvity::class.java))
            add(CustomModel("贝塞尔曲线", "贝塞尔曲线的详解", BezierActivity::class.java))
        }

        recyclerCustomize.layoutManager = LinearLayoutManager(mContext)
        recyclerCustomize.adapter = CustomAdapter()
    }

    inner class CustomAdapter: RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

        inner class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.tvTitle)
            val subTitle: TextView = itemView.findViewById(R.id.tvSubTitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CustomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_custom_view, parent, false))

        override fun getItemCount() = pageList.size

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            val model = pageList[position]

            holder.title.text = model.title
            holder.subTitle.text = model.subTitle
            holder.itemView.setOnClickListener {
                startActivity(Intent(mContext, model.clazz))
            }
        }
    }
}