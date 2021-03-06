package com.wj.sampleproject.repository

import cn.wj.android.common.ext.orFalse
import com.wj.sampleproject.net.WebService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 项目相关数据仓库
 */
class ProjectRepository(private val mWebService: WebService) {

    /** 获取新项目分类列表 */
    suspend fun getProjectCategory() = withContext(Dispatchers.IO) {
        mWebService.getProjectCategory()
    }

    /** 根据分类id[categoryId]、页码[pageNum]获取并返回项目列表 */
    suspend fun getProjectList(categoryId: String, pageNum: Int) = withContext(Dispatchers.IO) {
        // 获取项目列表
        val result = mWebService.getProjectList(pageNum, categoryId)
        // 处理收藏状态
        result.data?.datas?.forEach { it.collected.set(it.collect?.toBoolean().orFalse()) }
        result
    }
}