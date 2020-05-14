package com.tzt.customize.paint.fragment.color

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.graphics.Shader
import android.os.Bundle
import android.view.Gravity
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
import com.tzt.customize.paint.R
import com.tzt.customize.paint.widget.color.ComposeView
import com.tzt.customize.paint.widget.color.colorFilter.LightingColorFilterView
import com.tzt.customize.paint.widget.color.colorFilter.PorterDuffColorFilterView
import com.tzt.customize.paint.widget.color.shader.BitmapShaderView
import com.tzt.customize.paint.widget.color.shader.ComposeShaderView
import com.tzt.studykt.customView.paint.widget.color.shader.*
import kotlinx.android.synthetic.main.fragment_paint_common.*

/**
 * Description:颜色相关
 *
 * @author tangzhentao
 * @since 2020/5/6
 */
class PaintColorFragment : BaseFragment() {
    companion object {
        const val SHADER_TYPE_BASE = 0
        const val SHADER_TYPE_BITMAP = 1
        const val SHADER_TYPE_COMPOSE = 2
        const val COLOR_FILTER = 3
    }

    private val mList = ArrayList<PaintItemModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_paint_common, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments?.getInt("shader_type", 0)

        mList.clear()
        bundle?.let {
            when(it) {
                SHADER_TYPE_BASE -> {
                    titleTv.text = "LinearGradient 线性渐变\n 设置两个点和两种颜色，以这两个点作为端点，使用两种颜色的渐变来绘制颜色"
                    mList.apply {
                        // 线性渐变
                        add(PaintItemModel("LinearGradient  构造函数：\n" +
                                "LinearGradient(float x0, float y0, float x1, float y1, int color0, int color1, Shader.TileMode tile)\n\n" +
                                "x0 y0 x1 y1：渐变的两个端点的位置\ncolor0 color1 是端点的颜色\ntile：端点范围之外的着色规则，类型是 TileMode。TileMode 一共有 3 个值可选： CLAMP, MIRROR 和 REPEAT。\nCLAMP会在端点之外延续端点处的颜色；MIRROR 是镜像模式；REPEAT 是重复模式。", null))
                        add(PaintItemModel("TileMode.CLAMP",
                            LinearGradientView(
                                mContext,
                                Shader.TileMode.CLAMP
                            )
                        ))
                        add(PaintItemModel("TileMode.MIRROR",
                            LinearGradientView(
                                mContext,
                                Shader.TileMode.MIRROR
                            )
                        ))
                        add(PaintItemModel("TileMode.REPEAT",
                            LinearGradientView(
                                mContext,
                                Shader.TileMode.REPEAT
                            )
                        ))
                        // 辐射渐变
                        add(PaintItemModel("RadialGradient 构造函数\n" +
                                "RadialGradient(float centerX, float centerY, float radius, int centerColor, int edgeColor, TileMode tileMode)\n" +
                                "centerX centerY：辐射中心的坐标\nradius：辐射半径\ncenterColor：辐射中心的颜色\nedgeColor：辐射边缘的颜色\ntileMode：辐射范围之外的着色模式",null))
                        add(PaintItemModel("TileMode.CLAMP",
                            RadialGradientView(
                                mContext,
                                Shader.TileMode.CLAMP
                            )
                        ))
                        add(PaintItemModel("TileMode.MIRROR",
                            RadialGradientView(
                                mContext,
                                Shader.TileMode.MIRROR
                            )
                        ))
                        add(PaintItemModel("TileMode.REPEAT",
                            RadialGradientView(
                                mContext,
                                Shader.TileMode.REPEAT
                            )
                        ))
                        // 扫描渐变
                        add(PaintItemModel("SweepGradient 扫描渐变\n构造函数：\n" +
                                "SweepGradient(float cx, float cy, int color0, int color1)\n" +
                                "cx cy ：扫描的中心\n" +
                                "color0：扫描的起始颜色\n" +
                                "color1：扫描的终止颜色",
                            SweepGradientView(
                                mContext
                            )
                        ))
                    }
                }
                SHADER_TYPE_BITMAP -> {
                    titleTv.text = "BitmapShader 图片着色器 \n用Bitmap的像素来做为图片或文字的填充"
                    mList.apply {
                        add(PaintItemModel("BitmapShader 构造函数:\n" +
                                "BitmapShader(Bitmap bitmap, Shader.TileMode tileX, Shader.TileMode tileY)\n\n" +
                                "bitmap：做模板的Bitmap对象\ntileX：横向的TileMode\ntileY：纵向的TileMode", null))
                        add(PaintItemModel("过程演示 Shader.TileMode.CLAMP",
                            BitmapShaderView(
                                mContext,
                                Shader.TileMode.CLAMP,
                                true
                            )
                        ))
                        add(PaintItemModel("Shader.TileMode.CLAMP",
                            BitmapShaderView(
                                mContext,
                                Shader.TileMode.CLAMP,
                                false
                            )
                        ))
                        add(PaintItemModel("Shader.TileMode.MIRROR",
                            BitmapShaderView(
                                mContext,
                                Shader.TileMode.MIRROR,
                                false
                            )
                        ))
                        add(PaintItemModel("Shader.TileMode.REPEAT",
                            BitmapShaderView(
                                mContext,
                                Shader.TileMode.REPEAT,
                                false
                            )
                        ))
                    }
                }
                SHADER_TYPE_COMPOSE -> {
                    titleTv.text = "ComposeShader 混合着色器 \n所谓混合，就是把两个 Shader 一起使用。"
                    mList.apply {
                        add(
                            PaintItemModel("构造函数:\n" +
                                    "ComposeShader(Shader shaderA, Shader shaderB, PorterDuff.Mode mode)\n参数详解:\n" +
                                    "shaderA, shaderB：两个相继使用的shader\nmodel：两个shader的叠加模式\n" +
                                    "PorterDuff.Mode 是用来指定两个图像共同绘制时的颜色策略的", null)
                        )
                        add(PaintItemModel("过程演示",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.SRC_OVER,
                                true
                            )
                        ))
                        add(PaintItemModel("源图像(Src)    目标图像(Dst)",
                            ComposeView(
                                mContext
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.SRC",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.SRC,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.SRC_IN",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.SRC_IN,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.SRC_ATOP",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.SRC_ATOP,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.SRC_OUT",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.SRC_OUT,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DST",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.DST,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DST_IN",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.DST_IN,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DST_OUT",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.DST_OUT,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DST_OVER",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.DST_OVER,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DST_ATOP",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.DST_ATOP,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.ADD",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.ADD,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.CLEAR",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.CLEAR,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DARKEN",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.DARKEN,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.LIGHTEN",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.LIGHTEN,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.MULTIPLY",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.MULTIPLY,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.OVERLAY",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.OVERLAY,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.SCREEN",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.SCREEN,
                                false
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.XOR",
                            ComposeShaderView(
                                mContext,
                                PorterDuff.Mode.XOR,
                                false
                            )
                        ))
                    }
                }
                COLOR_FILTER -> {
                    titleTv.text = "LightingColorFilter\n用来模拟简单的光照效果的"
                    mList.apply {
                        add(PaintItemModel("构造函数\n" +
                                "LightingColorFilter(@ColorInt int mul, @ColorInt int add)\n" +
                                "mul: 用来和目标像素相乘\nadd: 用来和目标像素相加", null))
                        add(PaintItemModel("LightingColorFilter(0xffffff, 0x113000)",
                            LightingColorFilterView(
                                mContext
                            )
                        ))
                        add(PaintItemModel("构造函数:\n" +
                                "PorterDuffColorFilter(int color, PorterDuff.Mode mode)\n" +
                                "color: 指定的颜色,ARGB格式color\nmodel: 指定的PorterDuff.Mode", null))
                        add(PaintItemModel("PorterDuff.Mode.SRC",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.SRC
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.SRC_OUT",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.SRC_OUT
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.SRC_ATOP",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.SRC_ATOP
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.SRC_IN",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.SRC_IN
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.SRC_OVER",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.SRC_OVER
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DST",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.DST
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DST_ATOP",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.DST_ATOP
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DST_OVER",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.DST_OVER
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DST_IN",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.DST_IN
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DST_OUT",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.DST_OUT
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.XOR",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.XOR
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.SCREEN",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.SCREEN
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.OVERLAY",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.OVERLAY
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.MULTIPLY",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.MULTIPLY
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.LIGHTEN",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.LIGHTEN
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.DARKEN",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.DARKEN
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.CLEAR",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.CLEAR
                            )
                        ))
                        add(PaintItemModel("PorterDuff.Mode.ADD",
                            PorterDuffColorFilterView(
                                mContext,
                                PorterDuff.Mode.ADD
                            )
                        ))
                    }
                }
                else -> {}
            }
        }

        recyclerShader.layoutManager = LinearLayoutManager(mContext).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        recyclerShader.adapter = ShaderAdapter()

        recyclerShader.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position >=0 && position < mList.size) {
                    val model = mList[position]
                    if (model.title.contains("LinearGradient") || (model.view != null && model.view is LinearGradientView)) {
                        titleTv.text = "LinearGradient 线性渐变\n 设置两个点和两种颜色，以这两个点作为端点，使用两种颜色的渐变来绘制颜色"
                    } else if (model.title.contains("RadialGradient") || (model.view != null && model.view is RadialGradientView)) {
                        titleTv.text = "RadialGradient 辐射渐变\n 从中心向周围辐射状的渐变"
                    } else if (model.title.contains("LightingColorFilter") || (model.view != null && model.view is LightingColorFilterView)) {
                        titleTv.text = "LightingColorFilter\n用来模拟简单的光照效果的"
                    } else if (model.title.contains("PorterDuffColorFilter") || (model.view != null && model.view is PorterDuffColorFilterView)) {
                        titleTv.text = "PorterDuffColorFilter\n使用一个指定的颜色和一种指定的 PorterDuff.Mode 来与绘制对象进行合成"
                    }
                }
            }
        })
    }

    inner class ShaderAdapter: RecyclerView.Adapter<ShaderAdapter.ShaderViewHolder>() {
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
                // 是否需要设置为居中正方形
                val isSuqare = model.view is ComposeShaderView && !(model.view as ComposeShaderView).debug
                val params = FrameLayout.LayoutParams(if(isSuqare) dpToPx(200).toInt() else FrameLayout.LayoutParams.MATCH_PARENT, dpToPx(200).toInt())
                if (isSuqare) params.gravity = Gravity.CENTER
                holder.contextView.addView(model.view, params)
            }
        }
    }
}