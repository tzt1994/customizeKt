package com.tzt.common.basedepency.base

import android.annotation.SuppressLint
import com.tzt.common.basedepency.R
import com.tzt.common.basedepency.widget.ToobarParams
import kotlinx.android.synthetic.main.activity_webview.*


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/23
 */
class BaseWebView: BaseActivity() {
    companion object {
        const val LOAD_URL = "load_url"
        const val TITLE = "title"
    }

    override fun getToobarParams(): ToobarParams? {
        return ToobarParams(
            createFinisIcon(),
            ""
        )
    }

    override fun layoutResID(): Int {
        return R.layout.activity_webview
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initData() {
        webView.settings.javaScriptEnabled = true

        intent.getStringExtra(LOAD_URL)?.let {
            webView.loadUrl(it)
        }

        intent.getStringExtra(TITLE)?.let {
            toobar.setTitle(it)
        }

    }
}