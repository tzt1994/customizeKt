package com.tzt.customize.paint.fragment.text

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.tzt.common.basedepency.dpToPx
import com.tzt.customize.paint.R
import com.tzt.customize.paint.databinding.FragmentPaintCommonBinding
import com.tzt.customize.paint.widget.text.DrawTextView
import com.tzt.customize.paint.widget.text.FontTextView
import com.tzt.customize.paint.widget.text.ShowTextView


/**
 * Description: 绘制文字
 *
 * @author tangzhentao
 * @since 2020/5/8
 */
class PaintTextFragment: BaseFragment<FragmentPaintCommonBinding>() {
    companion object {
        const val TEXT_STYLE = 0
        const val TEXT_EFFECT = 1
        const val TEXT_FONT = 2
    }

    private val mList = ArrayList<PaintItemModel>()

    override fun layoutBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPaintCommonBinding.inflate(inflater, container, false)

    @SuppressLint("SetTextI18n")
    override fun initData() {
        val effect = arguments?.getInt("text_type", -1)
        binding.titleTv.visibility = View.GONE
        mList.clear()
        effect?.let {
            when (it) {
                TEXT_STYLE -> {
                    // 绘制文字样式
                    mList.apply {
                        add(PaintItemModel("drawText(String text, float x, float y, Paint paint)\n" +
                                "text:  要绘制的文字\nx:   文字的x坐标(和文字对齐方式有关)\ny:   文字的基线(Baseline)\npaint: 画笔",
                            DrawTextView(mContext, type = DrawTextView.DRAW_TEXT)
                        ))
                        add(PaintItemModel("drawTextRun() 是在 API 23 新加入的方法, 为了其他语言中的奇怪文字适用的\n" +
                                "drawTextRun(CharSequence text, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint paint)\n" +
                                "text：要绘制的文字\n" +
                                "start：从那个字开始绘制\n" +
                                "end：绘制到哪个字结束\n" +
                                "contextStart：上下文的起始位置。contextStart 需要小于等于 start\n" +
                                "contextEnd：上下文的结束位置。contextEnd 需要大于等于 end\n" +
                                "x：文字左边的坐标\n" +
                                "y：文字的基线坐标\n" +
                                "isRtl：是否是 RTL（Right-To-Left，从右向左\n" +
                                "paint: 画笔",
                            DrawTextView(mContext, type = DrawTextView.DRAW_TEXT_RUN)
                        ))
                        add(PaintItemModel("drawTextOnPath() 沿着一条 Path 来绘制文字\n" +
                                "drawTextOnPath(String text, Path path, float hOffset, float vOffset, Paint paint)\n" +
                                "hOffset: 向右偏移量\nvOffset: 向下偏移量", DrawTextView(mContext, type = DrawTextView.DRAW_TEXT_PATH)
                        ))
                        add(PaintItemModel("绘制多行文字\n" +
                                "StaticLayout(CharSequence source, TextPaint paint, int width, Alignment align, float spacingmult, float spacingadd, boolean includepad)\n" +
                                "width 是文字区域的宽度，文字到达这个宽度后就会自动换行；\n" +
                                "align 是文字的对齐方向；\n" +
                                "spacingmult 是行间距的倍数，通常情况下填 1 就好；\n" +
                                "spacingadd 是行间距的额外增加值，通常情况下填 0 就好；\n" +
                                "includepad 是指是否在文字上下添加额外的空间，来避免某些过高的字符的绘制出现越界。", DrawTextView(mContext, type = DrawTextView.STATIC_LAYOUT)
                        ))
                    }
                }
                TEXT_EFFECT -> {
                    // 文字设置效果
                    mList.apply {
                        add(PaintItemModel("TsetTextSize(float textSize)\n" +
                                "设置文字大小, 像素值", ShowTextView(mContext, type = ShowTextView.TEXT_SIZE)
                        ))
                        add(PaintItemModel("setTypeface(Typeface typeface)\n" +
                                "设置字体, Typeface", ShowTextView(mContext, type = ShowTextView.TEXT_TYPE_FACE)
                        ))
                        add(PaintItemModel("setFakeBoldText(boolean fakeBoldText)\n" +
                                "是否使用伪粗体", ShowTextView(mContext, type = ShowTextView.FAKE_BOLD_TEXT)
                        ))
                        add(PaintItemModel("setStrikeThruText(boolean strikeThruText)\n" +
                                "是否加删除线", ShowTextView(mContext, type = ShowTextView.STRIKE_THRU_TEXT)
                        ))
                        add(PaintItemModel("setUnderlineText(boolean underlineText)\n" +
                                "是否加下划线", ShowTextView(mContext, type = ShowTextView.UNDER_LINE_TEXT)
                        ))
                        add(PaintItemModel("setTextSkewX(float skewX)\n" +
                                "设置文字横向错切角度。其实就是文字倾斜度的啦。", ShowTextView(mContext, type = ShowTextView.TEXT_SKEW_X)
                        ))
                        add(PaintItemModel("setTextScaleX(float scaleX)\n" +
                                "设置文字横向放缩。也就是文字变胖变瘦", ShowTextView(mContext, type = ShowTextView.TEXT_SCALE_X)
                        ))
                        add(PaintItemModel("setLetterSpacing(float letterSpacing)\n" +
                                "设置字符间距。默认值是 0(API >= 21)。", ShowTextView(mContext, type = ShowTextView.LETTER_SPACING)
                        ))
                        add(PaintItemModel("setFontFeatureSettings(String settings)\n" +
                                "用 CSS 的 font-feature-settings 的方式来设置文字。(API >= 21)", ShowTextView(mContext, type = ShowTextView.FONT_FEATURE_SETTINGS)
                        ))
                        add(PaintItemModel("setTextAlign(Paint.Align align)" +
                                "设置文字的对齐方式。\n一共有三个值：LEFT CETNER 和 RIGHT。默认值为 LEFT。", ShowTextView(mContext, type = ShowTextView.TEXT_ALIGN)
                        ))
                        add(PaintItemModel("setTextLocale(Locale locale)\nsetTextLocales(LocaleList locales)\n" +
                                "设置绘制所使用的 Locale。就是设置区域语言", ShowTextView(mContext, type = ShowTextView.TEXT_LOCALE)
                        ))
                        val image = ImageView(mContext)
                        image.setImageResource(R.mipmap.hinting)
                        add(PaintItemModel("setHinting(int mode)\n" +
                                "设置是否启用字体的 hinting （字体微调\n" +
                                "17年以后基本没用了，手机屏幕的像素密度已经非常高，几乎不会再出现字体尺寸小到需要靠 hinting 来修正的情况\n", image))
                        add(PaintItemModel("\nsetElegantTextHeight(boolean elegant)\n" +
                                "这个方法对中国人没用。\n设置是否开启文字的 elegant height 。开启之后，文字的高度就变优雅了（误）。\n", null))
                        add(PaintItemModel("\nsetSubpixelText(boolean subpixelText)\n" +
                                "是否开启次像素级的抗锯齿。\n" +
                                "根据程序所运行的设备的屏幕类型，来进行针对性的次像素级的抗锯齿计算，从而达到更好的抗锯齿效果。\n", null))
                        add(PaintItemModel("\nsetLinearText(boolean linearText)\n" +
                                "允许文本平滑线性缩放的绘制标志。\n" +
                                "启用此标志实际上不会缩放文本，而是调整文本绘制操作，以优雅地处理平滑的缩放调整。启用此标志时，将禁用字体提示以防止缩放因子之间的形状变形，并且由于将生成大量的字形图像，将禁用字形缓存。\n" +
                                "子像素文本标志应与此标志一起使用，以防止字形位置在调整比例因子时捕捉到整个像素值。\n", null))
                    }
                }
                TEXT_FONT -> {
                    // 文字尺寸测量
                    mList.apply {
                        add(PaintItemModel("getFontSpacing()\n" +
                                "获取推荐的行距,即推荐的两行文字的 baseline 的距离",
                            FontTextView(mContext, type = FontTextView.FONT_SPACING)
                        ))
                        add(PaintItemModel("getFontMetrics() 获取Paint的 FontMetrics\n" +
                                "FontMetrics 是个相对专业的工具类，它提供了几个文字排印方面的数值：\n" +
                                "ascent, descent, top, bottom, leading。\n" +
                                "baseline值为0，上面的值为为负，下面的值为正。\n" +
                                "baseline: 文字显示的基线\n" +
                                "ascent,descent: 限制普通字符的顶部和底部范围\n" +
                                "top,bottom: 限制所有字形（ glyph ）的顶部和底部范围。\n" +
                                "leading: 行的额外间距，即对于上下相邻的两行，上行的 bottom 线和下行的 top 线的距离", FontTextView(mContext, type = FontTextView.FONT_METRICS)
                        ))
                        add(PaintItemModel("getTextBounds(String text, int start, int end, Rect bounds)\n" +
                                "测量的是文字的显示范围(最小矩形)\n" +
                                "text: 要测量的文字\nstart end: 分别是文字的起始和结束位置\n" +
                                "bounds 是存储文字显示范围的对象，方法在测算完成之后会把结果写进 bounds", FontTextView(mContext, type = FontTextView.TEXT_BOUNDS)
                        ))
                        add(PaintItemModel("float = measureText(String text)\n" +
                                "测量的是文字绘制时所占用的宽度", FontTextView(mContext, type = FontTextView.MEASURE_TEXT)
                        ))
                        add(PaintItemModel("\ngetTextWidths(String text, float[] widths)\n" +
                                "获取字符串中每个字符的宽度，并把结果填入参数 widths\n" +
                                "这相当于 measureText() 的一个快捷方法，它的计算等价于对字符串中的每个字符分别调用 measureText() ，并把它们的计算结果分别填入 widths 的不同元素。\n", null))
                        add(PaintItemModel("int = breakText(String text, boolean measureForwards, float maxWidth, float[] measuredWidth)\n" +
                                "breakText() 的返回值是截取的文字个数（如果宽度没有超限，则是文字的总个数）。\n" +
                                "text: 要测量的文字\nmeasureForwards: 表示文字的测量方向,true表示由左往右测量；\n" +
                                "maxWidth: 是给出的宽度上限\nmeasuredWidth: 是用于接受数据，而不是用于提供数据的：方法测量完成后会把截取的文字宽度（" +
                                "如果宽度没有超限，则为文字总宽度）赋值给 measuredWidth[0]", FontTextView(mContext, type = FontTextView.BREAK_TEXT)
                        ))
                        add(PaintItemModel("光标相关\n" +
                                "getRunAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, int offset)\n" +
                                "计算出某个字符处光标的 x 坐标\n" +
                                "start end: 是文字的起始和结束坐标；\n" +
                                "contextStart contextEnd: 是上下文的起始和结束坐标\n" +
                                "isRtl 是文字的方向；\n" +
                                "offset 是字数的偏移，即计算第几个字符处的光标\n" +
                                "国旗占了4个字符，光标不能出现在符号中间。", FontTextView(mContext, type = FontTextView.RUN_ADVANCE)
                        ))
                        add(PaintItemModel("\ngetOffsetForAdvance(CharSequence text, int start, int end, int contextStart, int contextEnd, boolean isRtl, float advance)\n" +
                                "给出一个位置的像素值，计算出文字中最接近这个位置的字符偏移量（即第几个字符最接近这个坐标\n" +
                                "text: 是要测量的文字\nstart end: 是文字的起始和结束坐标\n" +
                                "contextStart contextEnd: 是上下文的起始和结束坐标\n" +
                                "isRtl 是文字方向\nadvance 是给出的位置的像素值。填入参数，对应的字符偏移量将作为返回值返回" +
                                "getOffsetForAdvance() 配合上 getRunAdvance() 一起使用，就可以实现「获取用户点击处的文字坐标」的需求\n", null))
                        val glyphImage = ImageView(mContext)
                        glyphImage.setImageResource(R.mipmap.glyph)
                        add(PaintItemModel("boolean = hasGlyph(String string)\n" +
                                "检查指定的字符串中是否是一个单独的字形 (glyph", glyphImage))
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