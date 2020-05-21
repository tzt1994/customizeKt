package com.tzt.customize.transform.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tzt.common.basedepency.BaseFragment
import com.tzt.common.basedepency.PaintItemModel
import com.tzt.common.basedepency.dpToPx
import com.tzt.customize.transform.R
import com.tzt.customize.transform.widget.CameraTransFormView
import com.tzt.customize.transform.widget.CanvasTransFormView
import com.tzt.customize.transform.widget.ClipView
import com.tzt.customize.transform.widget.MatrixTransFormView
import kotlinx.android.synthetic.main.fragment_clip_transform.*


/**
 * Description: 裁剪和几何变换
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class ClipTransFormFragment: BaseFragment() {
    companion object {
        const val CLIP = 0
        const val CANVAS_TRANSFORM = 1
        const val MATRIX_TRANSFORM = 2
        const val CAMERA_TRANSFORM = 3
    }

    private val mList = ArrayList<PaintItemModel>()


    override fun layoutResID(): Int {
        return R.layout.fragment_clip_transform
    }

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val effect = arguments?.getInt("clip_transform_type", -1)
        titleTv.visibility = View.GONE
        mList.clear()
        effect?.let {
            when (it) {
                CLIP -> {
                    // 范围裁剪
                    mList.apply {
                        add(PaintItemModel("矩形裁剪\n canvas.clipRect()" +
                                "", ClipView(mContext, type = ClipView.CLIP_RECT)
                        ))
                        add(PaintItemModel("path裁剪(内)\ncanvas?.clipPath()" +
                                "", ClipView(mContext, type = ClipView.CLIP_PATH)))
                        add(PaintItemModel("矩形path裁剪(外)\ncanvas?.clipOutPath()\nAPI: >=26(android 8.0)" +
                                "", ClipView(mContext, type = ClipView.CLIP_OUT_PATH)))
                    }
                }
                CANVAS_TRANSFORM -> {
                    // 画布几何变换
                    mList.apply {
                        add(PaintItemModel("使用 Canvas 来做常见的二维变换, canvas的组合变换是逆序\n" +
                                "平移\nCanvas.translate(float dx, float dy)\n" +
                                "dx 和 dy: 表示横向和纵向的位移", CanvasTransFormView(mContext, type = CanvasTransFormView.TRANSLATE)
                        ))
                        add(PaintItemModel("旋转\nCanvas.rotate(float degrees, float px, float py)\n" +
                                "degrees: 旋转角度， 方向是顺时针为正向\n" +
                                "px 和 py: 轴心的位置", CanvasTransFormView(mContext, type = CanvasTransFormView.ROTATE)))
                        add(PaintItemModel("缩放\nCanvas.scale(float sx, float sy, float px, float py)\n" +
                                "sx sy: 横向和纵向的放缩倍数\n" +
                                "px py: 放缩的轴心", CanvasTransFormView(mContext, type = CanvasTransFormView.SCALE)))
                        add(PaintItemModel("错切\nCanvas.skew(float sx, float sy)\n" +
                                "sx 和 sy: x方向和y方向的错切系数", CanvasTransFormView(mContext, type = CanvasTransFormView.SKEW)))
                    }
                }
                MATRIX_TRANSFORM -> {
                    // matrix几何变换
                    mList.apply {
                        add(PaintItemModel("Matrix(二维变换)做常见变换的方式：\n" +
                                "\t1.创建 Matrix 对象；\n" +
                                "\t2.调用 Matrix 的 pre/postTranslate/Rotate/Scale/Skew() 方法来设置几何变换，pre(逆序)post(正序)；\n" +
                                "\t3.使用 Canvas.setMatrix(matrix) 或 Canvas.concat(matrix) 来把几何变换应用到 Canvas。\n" +
                                "\n把 Matrix 应用到 Canvas 有两个方法： Canvas.setMatrix(matrix) 和 Canvas.concat(matrix)。\n" +
                                "\n" +
                                "Canvas.setMatrix(matrix)：用 Matrix 直接替换 Canvas 当前的变换矩阵，即抛弃 Canvas 当前的变换，改用 Matrix 的变换（注：不同的系统中 setMatrix(matrix) 的行为可能不一致，所以还是尽量用 concat(matrix) 吧）；\n" +
                                "Canvas.concat(matrix)：用 Canvas 当前的变换矩阵和 Matrix 相乘，即基于 Canvas 当前的变换，叠加上 Matrix 中的变换。", null))
                        add(PaintItemModel("平移\nMatrix.pre/postTranslate(float dx, float dy)\n" +
                                "dx 和 dy: 表示横向和纵向的位移", MatrixTransFormView(mContext, type = MatrixTransFormView.TRANSLATE)
                        ))
                        add(PaintItemModel("旋转\nMatrix.pre/postRotate(float degrees, float px, float py)\n" +
                                "degrees: 旋转角度， 方向是顺时针为正向\n" +
                                "px 和 py: 轴心的位置", MatrixTransFormView(mContext, type = MatrixTransFormView.ROTATE)))
                        add(PaintItemModel("缩放\nMatrix.pre/postScale(float sx, float sy, float px, float py)\n" +
                                "sx sy: 横向和纵向的放缩倍数\n" +
                                "px py: 放缩的轴心", MatrixTransFormView(mContext, type = MatrixTransFormView.SCALE)))
                        add(PaintItemModel("Matrix.setPolyToPoly(float[] src, int srcIndex, float[] dst, int dstIndex, int pointCount)\n" +
                                "用点对点映射的方式设置变换\n" +
                                "单点的映射，单点映射可以实现平移。而多点的映射，就可以让绘制内容任意地扭曲。\n" +
                                "src 和 dst:是源点集合目标点集；\n" +
                                "srcIndex 和 dstIndex: 是第一个点的偏移；\n" +
                                "pointCount 是采集的点的个数（个数不能大于 4，因为大于 4 个点就无法计算变换了）。", null))
                        add(PaintItemModel("单点实现平移", MatrixTransFormView(mContext, type = MatrixTransFormView.SINGLE_POINT)))
                        add(PaintItemModel("多点效果展示", MatrixTransFormView(mContext, type = MatrixTransFormView.MULTI_POINTS)))
                    }
                }
                CAMERA_TRANSFORM -> {
                    // camera几何变换
                    mList.apply {
                        add(PaintItemModel("使用 Camera 来做三维变换\n" +
                                "Camera 的三维变换有三类：旋转、平移、移动相机\n" +
                                "translate(float x, float y, float z)", CameraTransFormView(mContext, CameraTransFormView.TRANSLATE)
                        ))
                        add(PaintItemModel("三维旋转\n" +
                                "rotateX(deg)\n" +
                                "rotateY(deg)\n" +
                                "rotateZ(deg)\n" +
                                "rotate(x, y, z)", CameraTransFormView(mContext, CameraTransFormView.ROTATE)))
                        add(PaintItemModel("设置虚拟相机的位置\n" +
                                "Camera.setLocation(x, y, z)\n" +
                                "单位是英寸,x 和 y 参数一般不会改变，直接填 0。" +
                                "用来解决因绘制的内容过大，当它翻转起来的时候，可能出现图像投影过大的「糊脸」效果", CameraTransFormView(mContext, CameraTransFormView.LOCATION)))
                    }
                }
                else -> {}
            }
        }

        recyclerShader.layoutManager = LinearLayoutManager(mContext).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recyclerShader.adapter = EffectAdapter()
    }

    override fun bindListener() {
        recyclerShader.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position >=0 && position < mList.size) {
                    val model = mList[position]
                    when {
                        model.title.contains("strokeWidth") -> {
                            titleTv.text = "线条宽度\nstrokeWidth = Float"
                        }
                        model.title.contains("Paint.Cap") -> {
                            titleTv.text = "线头形状\nstrokeCap = Paint.Cap"
                        }
                        model.title.contains("Paint.Join") -> {
                            titleTv.text = "拐角的形状\nstrokeJoin = Paint.Join"
                        }
                        model.title.contains("strokeMiter") -> {
                            titleTv.text = "MITER 型拐角的延长线的最大值\nstrokeMiter = Float"
                        }
                    }
                }
            }
        })
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