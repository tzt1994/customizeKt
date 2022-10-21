package com.tzt.customize.paint.fragment.effect

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tzt.common.basedepency.BaseFragment
import com.tzt.common.basedepency.PaintItemModel
import com.tzt.common.basedepency.angleByMiter
import com.tzt.common.basedepency.dpToPx
import com.tzt.customize.paint.R
import com.tzt.customize.paint.databinding.FragmentPaintCommonBinding
import com.tzt.customize.paint.widget.effect.*
import com.tzt.customize.paint.widget.effect.patheffect.PathDashPathEffectView
import com.tzt.studykt.customView.paint.widget.effect.maskfilter.BlurMaskFilterView
import com.tzt.studykt.customView.paint.widget.effect.maskfilter.EmbossMaskFilterView
import com.tzt.studykt.customView.paint.widget.effect.patheffect.*


/**
 * Description:效果
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class PaintEffectFragment: BaseFragment<FragmentPaintCommonBinding>() {
    companion object {
        const val ANTI_ALIAS = 0
        const val STYLE = 1
        const val LINE_SHAPE = 2
        const val COLOR_OPTIMIZATION = 3
        const val PATH_EFFECT = 4
        const val MASK_FILTER = 5
        const val GET_PATH = 6
    }

    private val mList = ArrayList<PaintItemModel>()

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPaintCommonBinding.inflate(inflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val effect = arguments?.getInt("effect_type", -1)

        mList.clear()
        effect?.let {
            when (it) {
                ANTI_ALIAS -> {
                    binding.titleTv.text = "抗锯齿效果\nPaint.isAntiAlias = Boolean\nPaint(Paint.ANTI_ALIAS_FLAG)"
                    mList.apply {
                        add(PaintItemModel("开启",
                            AntiAliasView(
                                mContext,
                                true
                            )
                        ))
                        add(PaintItemModel("关闭",
                            AntiAliasView(
                                mContext,
                                false
                            )
                        ))
                    }
                }
                STYLE -> {
                    binding.titleTv.text = "Paint.Style\n设置图形是线条风格还是填充风格的\nPaint.style = Paint.Style"
                    mList.apply {
                        add(PaintItemModel("Paint.Style.FILL",
                            StyleView(
                                mContext,
                                Paint.Style.FILL
                            )
                        ))
                        add(PaintItemModel("Paint.Style.STROKE",
                            StyleView(
                                mContext,
                                Paint.Style.STROKE
                            )
                        ))
                        add(PaintItemModel("Paint.Style.FILL_AND_STROKE",
                            StyleView(
                                mContext,
                                Paint.Style.FILL_AND_STROKE
                            )
                        ))
                    }
                }
                LINE_SHAPE -> {
                    binding.titleTv.text = "线条宽度\nstrokeWidth = Float"
                    mList.apply {
                        add(PaintItemModel("线宽 strokeWidth",
                            LineShapeView(
                                mContext,
                                lineType = LineShapeView.STROKE_WIDTH
                            )
                        ))
                        add(PaintItemModel("线头 Paint.Cap.BUTT",
                            LineShapeView(
                                mContext,
                                lineType = LineShapeView.STROKE_CAP,
                                strokeCap = Paint.Cap.BUTT
                            )
                        ))
                        add(PaintItemModel("线头 Paint.Cap.ROUND",
                            LineShapeView(
                                mContext,
                                lineType = LineShapeView.STROKE_CAP,
                                strokeCap = Paint.Cap.ROUND
                            )
                        ))
                        add(PaintItemModel("线头 Paint.Cap.SQUARE",
                            LineShapeView(
                                mContext,
                                lineType = LineShapeView.STROKE_CAP,
                                strokeCap = Paint.Cap.SQUARE
                            )
                        ))
                        add(PaintItemModel("拐角形状 Paint.Join",
                            LineShapeView(
                                mContext,
                                lineType = LineShapeView.STROKE_JOIN
                            )
                        ))
                        val imageView = ImageView(mContext)
                        imageView.setImageResource(R.mipmap.miter)
                        add(PaintItemModel("strokeMiter(MITER型拐角的延长线的最大值)\n" +
                                "默认情况下延长线的最大值为4f\n" +
                                "即最小的角度值是${angleByMiter(4f)}°\n" +
                                ">最小角度会被保留，<最小角度自动改用Paint.Join.BEVEL来渲染连接点", imageView))
                        add(PaintItemModel("strokeMiter = 4f",
                            LineShapeView(
                                mContext,
                                lineType = LineShapeView.STROKE_MITER
                            )
                        ))
                    }
                }
                COLOR_OPTIMIZATION -> {
                    binding.titleTv.text = "色彩优化\nsetDither(boolean dither)\nsetFilterBitmap(boolean filter)"
                    mList.apply {
                        val dither = ImageView(mContext)
                        dither.setImageResource(R.mipmap.dither)
                        add(PaintItemModel("设置抖动来优化色彩深度降低时的绘制效果", dither))
                        add(PaintItemModel("dither, 不过对于现在（2017年）而言， setDither(dither) 已经没有当年那么实用了，因为现在的 Android 版本的绘制，默认的色彩深度已经是 32 位的 ARGB_8888 ，效果已经足够清晰了。只有当你向自建的 Bitmap 中绘制，并且选择 16 位色的 ARGB_4444 或者 RGB_565 的时候，开启它才会有比较明显的效果",
                            ColorOptimizationView(
                                mContext,
                                ditherType = true
                            )
                        ))
                        val filterBitmap = ImageView(mContext)
                        filterBitmap.setImageResource(R.mipmap.filter_bitmap)
                        add(PaintItemModel("设置双线性过滤来优化Bitmap放大绘制的效果", filterBitmap))
                        add(PaintItemModel("filterBitmap",
                            ColorOptimizationView(
                                mContext,
                                ditherType = false
                            )
                        ))
                    }
                }
                PATH_EFFECT -> {
                    binding.titleTv.text = "使用 PathEffect 来给图形的轮廓设置效果\n对 Canvas 所有的图形绘制有效"
                    mList.apply {
                        add(PaintItemModel("CornerPathEffect 所有的拐角边圆角\nCornerPathEffect(float radius)\nradius: 圆角的半径, 示例20f",
                            CornerPathEffectView(mContext,20f)))
                        add(PaintItemModel("DiscretePathEffect 线条进行随机的偏离\nDiscretePathEffect(float segmentLength, float deviation)\nsegmentLength: 分段长度, 示例20f\ndeviation: 偏离, 示例5f",
                            DiscretePathEffectView(mContext,20f, 5f)))
                        add(PaintItemModel("DashPathEffect 虚线\nDashPathEffect(float[] intervals, float phase)\nintervals: 长度>=2\nphase: 相位偏移到间隔数组中",
                            DashPathEffectView(mContext, floatArrayOf(10f, 5f), 5f)))
                        add(PaintItemModel("PathDashPathEffect 使用一个 Path 来绘制「虚线」\nPathDashPathEffect(Path shape, float advance, float phase, PathDashPathEffect.Style style)\n" +
                                "shape: 用来绘制的path\nadvance: 两个相邻shape之间的间隔\nphase: 虚线的偏移\nstyle:用来指定拐弯改变的时候 shape 的转换方式",
                            PathDashPathEffectView(mContext)
                        ))
                        add(PaintItemModel("SumPathEffect 组合效果,分别按照两种 PathEffect 分别对目标进行绘制\nSumPathEffect(PathEffect first, PathEffect second)\n" +
                                "first: 第二种PathEffect\nsecond: 第二种PathEffect",
                            SumPathEffectView(mContext)))
                        add(PaintItemModel("ComposePathEffect 组合效果,先对目标 Path 使用一个 PathEffect，然后再对这个改变后的 Path 使用另一个 PathEffect\n" +
                                "ComposePathEffect(PathEffect outerpe, PathEffect innerpe)\n" +
                                "outerpe: 后应用\ninnerpe: 先应用",
                            ComposePathEffectView(mContext)))
                    }
                }
                MASK_FILTER -> {
                    binding.titleTv.text = "MaskFilter 设置的是在绘制层上方的附加效果,基于整个画面来进行过滤"
                    mList.apply {
                        add(PaintItemModel("BlurMaskFilter 模糊效果\nBlurMaskFilter(float radius, BlurMaskFilter.Blur style)\nradius: 模糊的范围\nstyle: 模糊的类型" +
                                "\nNORMAL 内外都模糊绘制",
                            BlurMaskFilterView(mContext, BlurMaskFilter.Blur.NORMAL)))
                        add(PaintItemModel("SOLID 内部正常绘制，外部模糊",
                            BlurMaskFilterView(mContext, BlurMaskFilter.Blur.SOLID)))
                        add(PaintItemModel("INNER 内部模糊，外部不绘制",
                            BlurMaskFilterView(mContext, BlurMaskFilter.Blur.INNER)))
                        add(PaintItemModel("OUTER 内部不绘制，外部模糊",
                            BlurMaskFilterView(mContext, BlurMaskFilter.Blur.OUTER)))
                        add(PaintItemModel("EmbossMaskFilter 浮雕效果\nEmbossMaskFilter(float[] direction, float ambient, float specular, float\n" +
                                "direction: 3个元素的数组，指定了光源的方向: 模糊的范围\n ambient: 环境光的强度，数值范围是 0 到 1\n" +
                                "specular: 炫光的系数\nblurRadius: 应用光线的范围",
                            EmbossMaskFilterView(mContext)))
                    }
                }
                GET_PATH -> {
                    binding.titleTv.text = "根据paint的设置，计算出绘制Path或文字时的实际Path"
                    mList.apply {
                        add(PaintItemModel("getFillPath(Path src, Path dst)\n" +
                                "src: 原Path\ndst: 实际Path的保存位置\n" +
                                "绘制效果", GetPathView(mContext, path = true, real = false)
                        ))
                        add(PaintItemModel("实际Path", GetPathView(mContext, path = true, real = true)))
                        add(PaintItemModel("绘制效果", GetPathView(mContext, path = true, real = false,
                            pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 5f))
                        ))
                        add(PaintItemModel("实际Path", GetPathView(mContext, path = true, real = true,
                            pathEffect = DashPathEffect(floatArrayOf(20f, 10f), 5f))
                        ))
                        add(PaintItemModel("getTextPath(String text, int start, int end, float x, float y, Path path)\n\ngetTextPath(char[] text, int index, int count, float x, float y, Path path)", GetPathView(mContext, path = false)))
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

    override fun bindListener() {
        binding.recyclerShader.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position >=0 && position < mList.size) {
                    val model = mList[position]
                    when {
                        model.title.contains("strokeWidth") -> {
                            binding.titleTv.text = "线条宽度\nstrokeWidth = Float"
                        }
                        model.title.contains("Paint.Cap") -> {
                            binding.titleTv.text = "线头形状\nstrokeCap = Paint.Cap"
                        }
                        model.title.contains("Paint.Join") -> {
                            binding.titleTv.text = "拐角的形状\nstrokeJoin = Paint.Join"
                        }
                        model.title.contains("strokeMiter") -> {
                            binding.titleTv.text = "MITER 型拐角的延长线的最大值\nstrokeMiter = Float"
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
//                // 是否需要设置为居中正方形
//                val isSuqare = model.view is ComposeShaderView && !model.view.debug
//                val params = FrameLayout.LayoutParams(if(isSuqare) dpToPx(200).toInt() else FrameLayout.LayoutParams.MATCH_PARENT, dpToPx(200).toInt())
//                if (isSuqare) params.gravity = Gravity.CENTER
                val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dpToPx(200).toInt())
                holder.contextView.addView(model.view, params)
            }
        }
    }
}