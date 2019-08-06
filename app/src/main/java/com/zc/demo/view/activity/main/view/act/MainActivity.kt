package com.zc.demo.view.activity.main.view.act

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import com.zc.demo.MainEvent
import com.zc.demo.R
import com.zc.demo.adapter.FragmentViewPagerAdapter
import com.zc.demo.view.activity.main.contract.MainContract
import com.zc.demo.view.activity.main.presenter.MainPresenter
import com.zc.demo.view.activity.main.view.fragment.HomeFragment
import com.zc.demo.view.activity.main.view.fragment.MineFragment
import com.zc.demo.view.activity.main.view.fragment.ProjectFragment
import com.zc.demo.view.activity.main.view.fragment.SystemFragment
import com.zc.demo.view.activity.mvp.BaseMvpFragmentActivity
import com.zc.utillibrary.LogUtil
import com.zc.utillibrary.Util
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.top_title_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @Author wenchao
 * @Date 2019/08/02
 * @Description input description
 **/
class MainActivity : ViewPager.OnPageChangeListener, BaseMvpFragmentActivity<MainContract.IPresenter>(), View.OnClickListener, MainContract.IView {
    var homeFragment: HomeFragment? = null
    var projectFragment: ProjectFragment? = null
    var systemFragment: SystemFragment? = null
    var mineFragment: MineFragment? = null
    var index = 1
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.homeLayout -> {
                index = 1
            }
            R.id.projectLayout -> {
                index = 2
            }
            R.id.systemLayout -> {
                index = 3
            }
            R.id.mineLayout -> {
                index = 4
            }
            else -> {

            }
        }
        changeTabState()
        viewPager.currentItem = index - 1
    }


    private fun changeTabState() {
        homeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        projectLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        systemLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        mineLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        home_img.setImageResource(R.drawable.ic_wa_home)
        project_img.setImageResource(R.drawable.ic_wa_project)
        system_img.setImageResource(R.drawable.ic_wa_system)
        mine_img.setImageResource(R.drawable.ic_wa_mine)
        home_tv.setTextColor(ContextCompat.getColor(this, R.color.home_item_default_color))
        project_tv.setTextColor(ContextCompat.getColor(this, R.color.home_item_default_color))
        system_tv.setTextColor(ContextCompat.getColor(this, R.color.home_item_default_color))
        mine_tv.setTextColor(ContextCompat.getColor(this, R.color.home_item_default_color))
        when (index) {
            1 -> {
                homeLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.color_home_item_selected))
                home_img.setImageResource(R.drawable.ic_wa_home_select)
                home_tv.setTextColor(ContextCompat.getColor(this, R.color.title_bar_color))
            }
            2 -> {
                projectLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.color_home_item_selected))
                project_img.setImageResource(R.drawable.ic_wa_project_select)
                project_tv.setTextColor(ContextCompat.getColor(this, R.color.title_bar_color))
            }
            3 -> {
                systemLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.color_home_item_selected))
                system_img.setImageResource(R.drawable.ic_wa_system_select)
                system_tv.setTextColor(ContextCompat.getColor(this, R.color.title_bar_color))
            }
            4 -> {
                mineLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.color_home_item_selected))
                mine_img.setImageResource(R.drawable.ic_wa_mine_select)
                mine_tv.setTextColor(ContextCompat.getColor(this, R.color.title_bar_color))
            }
            else -> {

            }
        }
    }

    override fun registerPresenter() = MainPresenter::class.java

    override fun getLayoutId(): Int {
        return R.layout.main_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        if (savedInstanceState == null) {
            homeLayout.setOnClickListener(this)
            projectLayout.setOnClickListener(this)
            systemLayout.setOnClickListener(this)
            mineLayout.setOnClickListener(this)
            homeFragment = HomeFragment()
            projectFragment = ProjectFragment()
            systemFragment = SystemFragment()
            mineFragment = MineFragment()
            val data: MutableList<Fragment> = mutableListOf()
            data.add(homeFragment!!)
            data.add(projectFragment!!)
            data.add(systemFragment!!)
            data.add(mineFragment!!)
            val adapter = FragmentViewPagerAdapter(supportFragmentManager, data)
            viewPager.adapter = adapter
            viewPager.offscreenPageLimit = 3
            viewPager.addOnPageChangeListener(this)
            Util.showView(id_top_layout_back_img, false)
            id_top_layout_title_tv.text = "玩Android"
            changeTabState()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun goShowProject(event: MainEvent) {
        val intent = Intent(this, ShowProjectActivity::class.java)
        intent.putExtra("url", event.msg)
        intent.putExtra("type",event.type)
        startActivity(intent)
        when (event.type) {
            0 -> {

            }
            1 -> {
            }
            else -> {

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        index = position + 1
        changeTabState()
    }
}
