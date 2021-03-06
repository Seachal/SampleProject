package com.wj.sampleproject.fragment

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import com.wj.sampleproject.R
import com.wj.sampleproject.activity.WebViewActivity
import com.wj.sampleproject.base.ui.BaseFragment
import com.wj.sampleproject.constants.ACTION_PARCELABLE
import com.wj.sampleproject.databinding.AppFragmentWebViewBinding
import com.wj.sampleproject.viewmodel.BlankViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * WebView 页面
 */
class WebViewFragment
    : BaseFragment<BlankViewModel, AppFragmentWebViewBinding>() {

    override val viewModel: BlankViewModel by viewModel()

    override val layoutResId: Int = R.layout.app_fragment_web_view

    private val mUrl: String by lazy {
        arguments?.getParcelable<WebViewActivity.ActionModel>(ACTION_PARCELABLE)?.url.orEmpty()
    }

    /** 当前 url */
    val currentUrl: String
        get() = mBinding.wv.url

    override fun initView() {
        // 添加返回事件监听
        mContext.onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (mBinding.wv.canGoBack()) {
                    mBinding.wv.goBack()
                } else {
                    mContext.finish()
                }
            }
        })

        // 配置 WebView
        val webSettings = mBinding.wv.settings
        webSettings.domStorageEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        @SuppressLint("SetJavaScriptEnabled")
        webSettings.javaScriptEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE

        mBinding.wv.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        mBinding.wv.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    mBinding.pb.visibility = View.GONE
                } else {
                    if (View.INVISIBLE == mBinding.pb.visibility) {
                        mBinding.pb.visibility = View.VISIBLE
                    }
                    mBinding.pb.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }

        mBinding.wv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }
        }

        // 加载页面
        mBinding.wv.loadUrl(mUrl)
    }

    override fun onResume() {
        super.onResume()
        mBinding.wv.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.wv.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.wv.clearCache(true)
        mBinding.wv.removeAllViews()
        mBinding.wv.destroy()
    }

    companion object {

        /** 创建并返回 [WebViewFragment]，传递网页数据[webData] */
        fun actionCreate(webData: WebViewActivity.ActionModel?): WebViewFragment {
            return WebViewFragment().apply {
                arguments = bundleOf(
                        ACTION_PARCELABLE to webData
                )
            }
        }
    }
}