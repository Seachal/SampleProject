package com.wj.sampleproject.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.wj.sampleproject.R
import com.wj.sampleproject.base.ui.BaseActivity
import com.wj.sampleproject.constants.ACTION_TITLE
import com.wj.sampleproject.constants.ACTION_WEB_URL
import com.wj.sampleproject.databinding.AppActivityWebviewBinding
import com.wj.sampleproject.fragment.WebViewFragment
import com.wj.sampleproject.mvvm.WebViewViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * H5 界面
 */
class WebViewActivity
    : BaseActivity<WebViewViewModel, AppActivityWebviewBinding>() {

    companion object {
        /**
         * 界面入口
         *
         * @param context Context 对象
         * @param title 标题
         * @param url Web Url
         */
        fun actionStart(context: Context, title: String, url: String) {
            context.startActivity(Intent(context, WebViewActivity::class.java).apply {
                putExtra(ACTION_TITLE, title)
                putExtra(ACTION_WEB_URL, url)
                if (context !is Activity) {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
            })
        }
    }

    override val viewModel: WebViewViewModel by viewModel()

    /** WebView Fragment 对象 */
    private val webViewFragment: WebViewFragment by lazy {
        WebViewFragment.actionCreate(intent.getStringExtra(ACTION_TITLE).orEmpty(), intent.getStringExtra(ACTION_WEB_URL).orEmpty())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_activity_webview)

        // 获取标题
        viewModel.title.set(intent.getStringExtra(ACTION_TITLE).orEmpty())

        // 加载 Fragment
        supportFragmentManager.commit(true) {
            replace(R.id.fl_fragment, webViewFragment)
        }
    }

    override fun initObserve() {
        // 返回点击
        viewModel.navigationData.observe(this, Observer {
            if (!webViewFragment.onKeyDown()) {
                finish()
            }
        })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return webViewFragment.onKeyDown(keyCode) || super.onKeyDown(keyCode, event)
    }

}