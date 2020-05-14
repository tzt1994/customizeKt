package com.tzt.customizekt

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tzt.common.basedepency.BaseActivity
import com.tzt.common.basedepency.CustomModel
import com.tzt.customize.order.DrawOrderActivity
import com.tzt.customize.paint.ui.EffectActivity
import com.tzt.studykt.customView.canvasdraw.CanvasDrawActivity
import com.tzt.customize.paint.ui.PaintColorActivity
import com.tzt.customize.paint.ui.TextActivity
import com.tzt.customize.path.bezier.BezierActivity
import com.tzt.customize.propertyanimation.AnimatorActivity
import com.tzt.customize.transform.ClipTransFormActivity
import com.tzt.customize.paint.inaction.InActionActivity
import kotlinx.android.synthetic.main.activity_custom.*


/**
 * Description:自定义view
 *
 * @author tangzhentao
 * @since 2020/4/29
 */
class CustomActivity: BaseActivity() {
    private val pageList = ArrayList<CustomModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        pageList.apply {
            add(CustomModel("自定义View", "绘制基础canvas.DrawXXX()", CanvasDrawActivity::class.java))
            add(CustomModel("Paint", "Paint中对颜色的处理\n(基本颜色，ColorFilter，Xfermode)", PaintColorActivity::class.java))
            add(CustomModel("Paint", "Paint中效果详解", EffectActivity::class.java))
            add(CustomModel("Paint", "Paint中绘制文字的详解", TextActivity::class.java))
            add(CustomModel("自定义View", "Clip(裁剪), 几何变换详解", ClipTransFormActivity::class.java))
            add(CustomModel("自定义View", "绘制顺序", DrawOrderActivity::class.java))
            add(CustomModel("属性动画", "ObjectAnimator", AnimatorActivity::class.java))
            add(CustomModel("贝塞尔曲线", "贝塞尔曲线的详解", BezierActivity::class.java))
            add(CustomModel("高级UI效果", "自定义view实现的效果展示", InActionActivity::class.java))
        }

        recyclerCustomView.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recyclerCustomView.adapter = CustomAdapter()
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
                startActivity(Intent(this@CustomActivity, model.clazz))
            }
        }
    }
}