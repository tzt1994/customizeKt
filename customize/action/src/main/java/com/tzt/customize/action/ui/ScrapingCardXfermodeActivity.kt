package com.tzt.customize.action.ui

import android.widget.FrameLayout
import android.widget.Toast
import com.tzt.common.basedepency.base.BaseActivity
import com.tzt.common.basedepency.widget.ToobarParams
import com.tzt.customize.action.R
import com.tzt.customize.action.widget.ScrapingCardView
import kotlinx.android.synthetic.main.activity_scraping_card.*


/**
 * Description: 刮刮卡
 *
 * @author tangzhentao
 * @since 2020/5/13
 */
class ScrapingCardXfermodeActivity: BaseActivity() {
    private lateinit var scrapingCardView: ScrapingCardView

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            "刮刮卡.Xfermode",
            createFinisIcon(),
            createCodeIcon {
                Toast.makeText(context, "点击了code", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override var layoutResID = R.layout.activity_scraping_card

    override fun initData() {
        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        scrapingCardView = ScrapingCardView(context)
        scrapingCardView.setImageResource(R.mipmap.spider_black)
        flScrapingCard.addView(scrapingCardView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        scrapingCardView.recycle()
    }
}