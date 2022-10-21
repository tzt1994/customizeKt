package com.tzt.common.basedepency.base

import android.annotation.SuppressLint
import com.tzt.common.basedepency.databinding.ActivityWebviewBinding
import com.tzt.common.basedepency.widget.ToolbarParams


/**
 * Description:
 *
 * @author tangzhentao
 * @since 2020/5/23
 */
class BaseWebView: BaseActivity<ActivityWebviewBinding>() {
    companion object {
        const val LOAD_URL = "load_url"
        const val TITLE = "title"
    }

    override fun getToolbarParams() = ToolbarParams(createFinisIcon(), "")

    override fun layoutBinding() = ActivityWebviewBinding.inflate(layoutInflater, null, false)

    @SuppressLint("SetJavaScriptEnabled")
    override fun initData() {
        mBinding.apply {
            webView.settings.javaScriptEnabled = true
            intent.getStringExtra(LOAD_URL)?.let { webView.loadUrl(it) }
            intent.getStringExtra(TITLE)?.let { toolbar?.setTitle(it) }
        }
    }
}