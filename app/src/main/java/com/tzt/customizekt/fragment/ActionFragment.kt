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
import com.tzt.customize.action.ui.*
import com.tzt.customizekt.R
import kotlinx.android.synthetic.main.fragment_customize.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/14
 */
class ActionFragment: BaseFragment() {
    private val uiList = ArrayList<CustomModel>()

    override fun layoutResID(): Int {
        return R.layout.fragment_customize
    }

    override fun initData() {
        uiList.clear()
        uiList.apply {
            add(CustomModel("刮刮卡", "xfermode", ScrapingCardXfermodeActivity::class.java))
            add(CustomModel("心跳", "xfermode", HeartXfermodeActivity::class.java))
            add(CustomModel("LeafLoading", "风扇叶子loading效果", LeafLoadingActivity::class.java))
            add(CustomModel("ShapeShaderImageView", "BitmapShader实现\n(矩形或圆形，自由设置圆角或椭圆角,边框)", ShapeShaderActivity::class.java))
            add(CustomModel("ShapeXfermodeImageView", "Xfermode实现\n(矩形或圆形，自由设置圆角或椭圆角,边框)", ShapeXfermodeActivity::class.java))
            add(CustomModel("CircleImageView", "圆形图片, 可设置边框宽度及颜色)", CircleImageViewActivity::class.java))
        }

        recyclerCustomize.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recyclerCustomize.adapter = UIAdapter()
    }

    inner class UIAdapter: RecyclerView.Adapter<UIViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= UIViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_custom_view, parent, false)
        )

        override fun getItemCount()= uiList.size

        override fun onBindViewHolder(holder: UIViewHolder, position: Int) {
            val model = uiList[position]
            holder.title.text = model.title
            holder.subTitle.text = model.subTitle

            holder.itemView.setOnClickListener {
                startActivity(Intent(mContext, model.clazz))
            }
        }

    }

    inner class UIViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val subTitle: TextView = itemView.findViewById(R.id.tvSubTitle)
    }
}