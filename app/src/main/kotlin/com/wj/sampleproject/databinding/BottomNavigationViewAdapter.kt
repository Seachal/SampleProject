package com.wj.sampleproject.databinding

import androidx.databinding.BindingAdapter
import cn.wj.android.base.expanding.orFalse
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * BottomNavigationView DataBinding 适配器
 *
 * @author 王杰
 */
@Suppress("unused")
class BottomNavigationViewAdapter {

    companion object {

        /**
         * 设置条目选中监听
         *
         * @param bnv [BottomNavigationView] 对象
         * @param itemSelected 条目选中回调
         */
        @JvmStatic
        @BindingAdapter("android:bind_bnv_onItemSelected")
        fun setOnNavigationItemSelectedListener(bnv: BottomNavigationView, itemSelected: ((Int) -> Boolean)?) {
            bnv.setOnNavigationItemSelectedListener {
                itemSelected?.invoke(it.itemId).orFalse()
            }
        }
    }
}