package cn.wj.android.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Fragment基类
 * - 维护 [mContext]，当前界面的 Context 对象
 * - 添加 [initBefore] 方法，在一切流程开始前预处理一些数据
 * - [onCreateView] 方法中对 [layoutResID] 对应的 [View] 进行加载，并在 [initView] 中进行初始化操作
 * - 维护了 [mRootView]、[mDialogWidth]、[mDialogHeight]、[mGravity] 等参数，在 [onActivityCreated] 方法中进行配置
 * - 维护了 [onSelectVisiableChanged] 方法，以实现对多 [Fragment] 之间切换的感知
 *
 * @author 王杰
 */
abstract class BaseLibFragment
    : Fragment() {

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: AppCompatActivity

    /** 跟布局对象 */
    protected lateinit var mRootView: View

    /** 页面标题 */
    open val mPageTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 保存当前 Context 对象
        mContext = activity as AppCompatActivity

        // 初始化数据
        initBefore()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mRootView = inflater.inflate(layoutResID, container, false)

        // 初始化布局
        initView()

        return mRootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        // 使用 FragmentPagerAdapter 会调用
        onSelectVisiableChanged(isVisibleToUser)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        // 使用 FragmentManager 切换回调用
        onSelectVisiableChanged(!hidden)
    }

    /**
     * 初始化数据，最先调用
     */
    protected open fun initBefore() {}

    /**
     * 初始化布局
     */
    protected open fun initView() {}

    /**
     * Fragment 显示状态变化
     * - 仅在多个 Fragment 切换有效
     *
     * @param visiable 是否显示
     */
    protected open fun onSelectVisiableChanged(visiable: Boolean) {}

    /** 界面布局 id */
    abstract val layoutResID: Int
}