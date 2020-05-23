package com.tzt.common.basedepency.base

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.tzt.common.basedepency.ActivityController
import com.tzt.common.basedepency.R
import com.tzt.common.basedepency.isLightColor
import com.tzt.common.basedepency.widget.Toobar
import com.tzt.common.basedepency.widget.ToobarParams
import kotlinx.android.synthetic.main.content.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/14
 */
abstract class BaseActivity: AppCompatActivity() {
    lateinit var context: Context

    /**
     * 整个页面, 内容页面, 错误页面，空数据页面，数据页面，标题栏
     */
    private lateinit var allLayout: LinearLayout
    protected lateinit var toobar: Toobar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        allLayout = LinearLayout(context)
        // 避免控件订到状态栏
        allLayout.fitsSystemWindows = true
        allLayout.orientation = LinearLayout.VERTICAL
        allLayout.setBackgroundColor(0xffffff)

        //设置头部布局（状态栏背景和标题栏）
        val toobarParams = getToobarParams()
        if (getStatusBarColorSameAsTooBarColor()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //设置状态栏颜色
                val color = toobarParams?.backgroundColor ?: Color.parseColor("#1E90FF")

                window.statusBarColor = color
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //设置状态栏文字图标 黑色还是白色
                    window.decorView.systemUiVisibility =
                        if (isLightColor(color)) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else View.SYSTEM_UI_FLAG_VISIBLE
                }
            }
        }

        if (toobarParams != null) {
            toobar = Toobar(context)
            toobar.setBackgroundColor(toobarParams.backgroundColor)
            val params = LinearLayout.LayoutParams(-1, toobarParams.toobarHeight)
            allLayout.addView(toobar, params)
        }


        val contentParams = LinearLayout.LayoutParams(-1, 0)
        contentParams.weight = 1f
        allLayout.addView(View.inflate(context, R.layout.content, null), contentParams)

        setContentView(allLayout)

        if (layoutResID() > 0) {
            viewStub.layoutResource = layoutResID()
            viewStub.inflate()
        }

        updateTitleBar()

        initData()
        bindListener()
        showPage(PageState.DATA)

        ActivityController.addActivity(this)
    }

    /**
     * 更新标题栏
     */
    private fun updateTitleBar() {
        val  toobarParams = getToobarParams() ?: return
        toobar.mHeight = toobarParams.toobarHeight
        toobar.setTitle(toobarParams.title)
        toobar.addLeftAction(toobarParams.leftActions)
        toobar.addRightAction(toobarParams.rightActions)
    }

    /**
     * 是否显示标题栏
     */
    open fun getToobarParams() : ToobarParams? { return  null}

    /**
     * 设置是否让状态颜色和标题栏一致
     * @return 默认一致，可以重写返回false展示不一致
     */
    open fun getStatusBarColorSameAsTooBarColor(): Boolean { return true }

    /**
     * 内容布局
     * return 布局id Int
     */
    protected abstract fun layoutResID(): Int

    open fun initData() {}

    open fun bindListener() {}

    /**
     * 展示不同的页面
     */
    protected fun showPage(state: PageState) {
        when (state) {
            PageState.NULL -> {
                dataLayout.visibility = View.GONE
                nullLayout.visibility = View.VISIBLE
                errorLayout.visibility = View.GONE
            }
            PageState.ERROR -> {
                dataLayout.visibility = View.GONE
                nullLayout.visibility = View.GONE
                errorLayout.visibility = View.VISIBLE
            }
            PageState.DATA -> {
                dataLayout.visibility = View.VISIBLE
                nullLayout.visibility = View.GONE
                errorLayout.visibility = View.GONE
            }
        }
    }

    /**
     * 返回箭头
     * return Toobar.TooBarAction
     */
    fun createFinisIcon(): Toobar.TooBarAction {
        return object :Toobar.TooBarAction() {
            override fun getImageResource() = R.mipmap.back_arrow

            override fun click(view: View) {
                finish()
            }
        }
    }

    /**
     * 原创文章
     * return Toobar.TooBarAction
     */
    fun createOriginalIcon(clickListener: (view: View) -> Unit): Toobar.TooBarAction {
        return object :Toobar.TooBarAction() {
            override fun getImageResource() = R.mipmap.original

            override fun click(view: View) {
                clickListener(view)
            }
        }
    }

    /**
     * 源码show
     * return Toobar.TooBarAction
     */
    fun createCodeIcon(clickListener: (view: View) -> Unit): Toobar.TooBarAction {
        return object :Toobar.TooBarAction() {
            override fun getImageResource() = R.mipmap.code

            override fun click(view: View) {
                clickListener(view)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityController.deleteActivity(this)
    }

    /**
     * 空数据，错误，数据
     */
    enum class PageState{
        NULL, ERROR, DATA
    }
}