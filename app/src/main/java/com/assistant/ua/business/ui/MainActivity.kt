package com.assistant.ua.business.ui

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.assistant.ua.LFUserInfo
import com.assistant.ua.R
import com.assistant.ua.business.service.DownloadBinder
import com.assistant.ua.business.service.DownloadService
import com.assistant.ua.business.ui.life.ASAmuseMentFragment
import com.assistant.ua.business.ui.login.LoginActivity
import com.assistant.ua.business.ui.studywork.ASWorkFragment
import com.assistant.ua.business.ui.blog.ASBlogFragment
import com.assistant.ua.databinding.ActivityMainBinding
import com.assistant.ua.datasource.room.DbRepository
import com.assistant.ua.framework.base.ui.ServiceActivity
import com.assistant.ua.imageloader.glide.GlideLoader
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : ServiceActivity(), NavigationView.OnNavigationItemSelectedListener
    , View.OnClickListener {

    companion object {
        private const val LOGIN_REQUEST = 0X01
    }

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var drawerHeader: View
    private lateinit var nickNameTxt: TextView
    private lateinit var portraitImg: ImageView

    private lateinit var blogFragment: ASBlogFragment


    private var downloadBinder: DownloadBinder? = null

    private var serviceConnection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            this@MainActivity.downloadBinder = service as DownloadBinder
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
        setupDrawerLayout()
        showUserInfo()
        setupViewPager()
        bindService(Intent(this, DownloadService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun initViews() {
        viewPager = mainBinding.includeAppBarMain.mainContent.vpMain
        tabLayout = mainBinding.includeAppBarMain.tbLayoutMain
        setSupportActionBar(mainBinding.includeAppBarMain.toolbar)
        mainBinding.nvMain.setNavigationItemSelectedListener(this)

        drawerHeader = mainBinding.nvMain.getHeaderView(0)
        nickNameTxt = drawerHeader.findViewById(R.id.txt_main_header_name)
        portraitImg = drawerHeader.findViewById(R.id.img_main_header_portrait)

        var options = BitmapFactory.Options()
        options.inSampleSize
    }

    private fun setupDrawerLayout() {
        drawerLayout = mainBinding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            mainBinding.includeAppBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setupViewPager() {
        tabLayout.setupWithViewPager(viewPager)
        val adapter = MainViewPagerAdapter(supportFragmentManager)
        blogFragment = ASBlogFragment()
        adapter.addFragments(blogFragment, "博客")
        adapter.addFragments(ASWorkFragment(), "工作")
        adapter.addFragments(ASAmuseMentFragment(), "其他")
        viewPager.adapter = adapter
    }


    private fun showUserInfo() {
        GlideLoader.loadPortraitImage(this, LFUserInfo.instance.portraitUrl, portraitImg)
        if (LFUserInfo.instance.isLogin()) {
            nickNameTxt.text = "您好 ".plus(LFUserInfo.instance.userName)
            nickNameTxt.setOnClickListener(null)

        } else {
            nickNameTxt.text = ("登录").toString()
            if (LFUserInfo.instance.userName.isEmpty()) {
                nickNameTxt.setOnClickListener(this)
            }
        }
    }

    override fun onBackPressed() {
        if (!closeDrawerLayout()) {
            super.onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txt_main_header_name -> {
                startLoginActivity()
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        closeDrawerLayout()
                    }
                }, 500)
            }
        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.nav_check_new_version -> {
                Toast.makeText(this, "检查版本更新", Toast.LENGTH_LONG).show()
                downloadBinder?.startDownLoad(this@MainActivity)
                closeDrawerLayout()
            }

            R.id.nav_exit_login -> {
                DbRepository.instance.deleteLoginInfo()
                LFUserInfo.instance.initUserInfo()
                showUserInfo()
            }
        }
        return true
    }

    private fun closeDrawerLayout(): Boolean {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }
        return false
    }

    /**
     * 打开登录页
     */
    private fun startLoginActivity() {
        startActivityForResult(Intent(this, LoginActivity::class.java), LOGIN_REQUEST)
    }

    /**
     * 登录成功，更新测滑头部视图
     */
    private fun loginSucceed() {
        showUserInfo()
        blogFragment.loginSucceed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                LOGIN_REQUEST -> loginSucceed()
            }
        }
    }

    override fun onDestroy() {
        unbindService(serviceConnection)
        super.onDestroy()
    }
}