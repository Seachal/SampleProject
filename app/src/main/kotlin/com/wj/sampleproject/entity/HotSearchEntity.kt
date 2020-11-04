package com.wj.sampleproject.entity

/**
 * 热门搜索数据实体类
 *
 * @param name 搜索热词
 *
 * - 创建时间：2019/10/17
 *
 * @author 王杰
 */
data class HotSearchEntity(
        val id: String? = "",
        val link: String? = "",
        val name: String? = "",
        val order: String? = "",
        val visible: String? = ""
)