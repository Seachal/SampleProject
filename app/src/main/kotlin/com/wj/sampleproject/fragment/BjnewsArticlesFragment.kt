package com.wj.sampleproject.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import cn.wj.android.recyclerview.layoutmanager.WrapContentLinearLayoutManager
import com.wj.sampleproject.R
import com.wj.sampleproject.adapter.ArticleListRvAdapter
import com.wj.sampleproject.base.ui.BaseFragment
import com.wj.sampleproject.constants.ACTION_CATEGORY
import com.wj.sampleproject.databinding.AppFragmentBjnewsArticlesBinding
import com.wj.sampleproject.entity.CategoryEntity
import com.wj.sampleproject.mvvm.BjnewsArticlesViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 公众号文章页面
 */
class BjnewsArticlesFragment
    : BaseFragment<BjnewsArticlesViewModel, AppFragmentBjnewsArticlesBinding>() {

    companion object {
        /**
         * 创建 Fragment
         *
         * @return 公众号文章列表 Fragment
         */
        fun actionCreate(bjnews: CategoryEntity): BjnewsArticlesFragment {
            return BjnewsArticlesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ACTION_CATEGORY, bjnews)
                }
            }
        }
    }

    override val viewModel: BjnewsArticlesViewModel by viewModel()

    override val layoutResId: Int = R.layout.app_fragment_bjnews_articles

    private val bjnews: CategoryEntity? by lazy { arguments?.getParcelable<CategoryEntity>(ACTION_CATEGORY) }

    /** 文章列表适配器 */
    private val mArticlesAdapter: ArticleListRvAdapter by inject()

    override fun onResume() {
        super.onResume()

        if (firstLoad) {
            // 刷新数据
            viewModel.refreshing.set(true)
        }
    }

    override fun initView() {
        // 保存公众号 id
        viewModel.bjnewsId = bjnews?.id.orEmpty()

        // 配置文章列表
        mArticlesAdapter.viewModel = viewModel
        mBinding.rvBjnewsArticles.layoutManager = WrapContentLinearLayoutManager()
        mBinding.rvBjnewsArticles.adapter = mArticlesAdapter
    }

    override fun initObserve() {
        // 文章列表
        viewModel.articleListData.observe(this, Observer {
            // 更新文章列表
            mArticlesAdapter.submitList(it)
        })
    }
}