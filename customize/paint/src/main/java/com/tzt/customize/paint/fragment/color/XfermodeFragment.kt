package com.tzt.customize.paint.fragment.color

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tzt.common.basedepency.BaseFragment
import com.tzt.common.basedepency.PaintItemModel
import com.tzt.common.basedepency.dpToPx
import com.tzt.common.basedepency.screenWidth
import com.tzt.customize.paint.R
import com.tzt.customize.paint.databinding.FragmentXfermodeBinding
import com.tzt.customize.paint.widget.color.PorterDuffXfermodeView

/**
 * Description: Xfermode
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class XfermodeFragment : BaseFragment<FragmentXfermodeBinding>() {
    private val mList = ArrayList<PaintItemModel>()

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentXfermodeBinding.inflate(inflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun initData() {
        mList.clear()
        binding.tvXfermode.text = "Xfermode\n指的是你要绘制的内容和 Canvas 的目标位置的内容应该怎样结合计算出最终的颜色\n" +
                "唯一子类 PorterDuffXfermode,构造函数\n" +
                "PorterDuffXfermode(model: PorterDuff.Mode)"

        mList.apply {
            add(PaintItemModel("源图",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.SRC,
                    noneMode = PorterDuffXfermodeView.NONE_MODE_SRC
                )
            ))
            add(PaintItemModel("目标图",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.SRC,
                    noneMode = PorterDuffXfermodeView.NONE_MODE_DST
                )
            ))
            add(PaintItemModel("SRC",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.SRC
                )
            ))
            add(PaintItemModel("DST",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.DST
                )
            ))
            add(PaintItemModel("SRC_OVER",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.SRC_OVER
                )
            ))
            add(PaintItemModel("DST_OVER",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.DST_OVER
                )
            ))
            add(PaintItemModel("SRC_IN",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.SRC_IN
                )
            ))
            add(PaintItemModel("DST_IN",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.DST_IN
                )
            ))
            add(PaintItemModel("SRC_ATOP",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.SRC_ATOP
                )
            ))
            add(PaintItemModel("DST_ATOP",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.DST_ATOP
                )
            ))
            add(PaintItemModel("SRC_OUT",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.SRC_OUT
                )
            ))
            add(PaintItemModel("DST_OUT",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.DST_OUT
                )
            ))
            add(PaintItemModel("XOR",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.XOR
                )
            ))
            add(PaintItemModel("DARKEN",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.DARKEN
                )
            ))
            add(PaintItemModel("LIGHTEN",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.LIGHTEN
                )
            ))
            add(PaintItemModel("MULTIPLY",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.MULTIPLY
                )
            ))
            add(PaintItemModel("SCREEN",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.SCREEN
                )
            ))
            add(PaintItemModel("ADD",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.ADD
                )
            ))
            add(PaintItemModel("OVERLAY",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.OVERLAY
                )
            ))
            add(PaintItemModel("CLEAR",
                PorterDuffXfermodeView(
                    mContext,
                    PorterDuff.Mode.CLEAR
                )
            ))
        }

        binding.recyclerXfermode.layoutManager = GridLayoutManager(mContext, 4).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.recyclerXfermode.adapter = XfermodeAdapter()
    }

    inner class XfermodeAdapter: RecyclerView.Adapter<XfermodeAdapter.XfermodeViewHolder>() {
        inner class XfermodeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val title: TextView = itemView.findViewById(R.id.shaderTypeTv)
            val contextView: FrameLayout = itemView.findViewById(R.id.shaderContentView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XfermodeViewHolder {
            val holder = XfermodeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_xfermode, parent, false))
            holder.contextView.layoutParams.height = (screenWidth() - dpToPx(40).toInt()) / 4
            return holder
        }

        override fun getItemCount() = mList.size

        override fun onBindViewHolder(holder: XfermodeViewHolder, position: Int) {
            val model = mList[position]

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
                val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                holder.contextView.addView(model.view, params)
            }
        }
    }
}