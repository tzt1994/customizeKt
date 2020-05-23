package com.tzt.custom.canvas

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tzt.common.basedepency.CanvasDrawModel
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import kotlinx.android.synthetic.main.activity_canvas_draw.*


/**
 * Description:绘制api接口
 *
 * @author tangzhentao
 * @since 2020/4/26
 */
class CanvasDrawActivity: BaseActivity() {

    private var pageModelList = ArrayList<CanvasDrawModel>()

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            "drawXXX()",
            createOriginalIcon {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://hencoder.com/ui-1-1/")))
            }
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_canvas_draw
    }

    override fun initData() {
        pageModelList.apply {
            add(CanvasDrawModel(0, "drawColor()", R.layout.sample_color))
            add(CanvasDrawModel(R.mipmap.sample_circle, "drawCircle()", R.layout.sample_circle))
            add(CanvasDrawModel(R.mipmap.sample_rect, "drawRect()", R.layout.sample_rect))
            add(CanvasDrawModel(R.mipmap.sample_point, "drawPoint()", R.layout.sample_point))
            add(CanvasDrawModel(R.mipmap.sample_oval, "drawOval()", R.layout.sample_oval))
            add(CanvasDrawModel(R.mipmap.sample_line, "drawLine()", R.layout.sample_line))
            add(CanvasDrawModel(R.mipmap.sample_round_rect, "drawRoundRect()", R.layout.sample_round_rect))
            add(CanvasDrawModel(R.mipmap.sample_arc, "drawArc()", R.layout.sample_arc))
            add(CanvasDrawModel(R.mipmap.sample_path, "drawPath()", R.layout.sample_path))
            add(CanvasDrawModel(R.mipmap.sample_histogram, "直方图", R.layout.sample_bar))
            add(CanvasDrawModel(R.mipmap.sample_pie_chart, "饼图", R.layout.sample_pie))
        }

        viewPage.adapter = PageAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        tabLayout.setupWithViewPager(viewPage)
    }

    inner class PageAdapter(fragmentManager: FragmentManager, behavior: Int): FragmentPagerAdapter(fragmentManager, behavior) {
        override fun getItem(position: Int): Fragment {
            val canvasDrawModel = pageModelList[position]

            return PageFragment.newInstance(canvasDrawModel.imageRes, canvasDrawModel.layoutRes)
        }

        override fun getCount() = pageModelList.size

        override fun getPageTitle(position: Int): CharSequence? {
            return pageModelList[position].title
        }
    }
}