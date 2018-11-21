package com.assistant.ua.business.ui.blog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.assistant.ua.ASApp
import com.assistant.ua.R
import com.assistant.ua.business.ui.blog.widget.BlogItemView
import com.assistant.ua.databinding.FragmentBlogBinding
import com.assistant.ua.datasource.entity.BlogEntity
import com.assistant.ua.framework.base.ui.ServiceFragment
import com.assistant.ua.framework.downloader.AsDUtil
import com.assistant.ua.framework.downloader.callback.SimpleDownloadCallback
import com.assistant.ua.framework.recycler.LFRecyclerAdapter
import com.assistant.ua.framework.recycler.LFRecyclerItemModel
import com.assistant.ua.framework.recycler.RecyclerItemDecoration
import java.io.File

/** Created by qinbaoyuan on 2018/11/12
 */
class ASBlogFragment : ServiceFragment(), View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var blogBinding: FragmentBlogBinding
    private lateinit var adapter: LFRecyclerAdapter
    private lateinit var viewModel: StudyViewModel

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_blog -> {
                //testNofication()
            }
        }
    }

    override fun onRefresh() {
        viewModel.getAllTask()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        blogBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_blog, null, false)
        initViews()
        viewModel = ViewModelProviders.of(this).get(StudyViewModel::class.java)
        listenViewModel()
        return blogBinding.root
    }

    private fun initViews() {
        setRecyclerView()
        blogBinding.fabBlog.setOnClickListener(this)
        blogBinding.srBlogLayout.setOnRefreshListener(this)
    }

    /**
     * 设置RecyclerView
     */
    private fun setRecyclerView() {
        blogBinding.rvBlog.layoutManager = LinearLayoutManager(activity)
        blogBinding.rvBlog.addItemDecoration(RecyclerItemDecoration())
        adapter = LFRecyclerAdapter()
        blogBinding.rvBlog.adapter = adapter
    }

    /**
     * 监听ViewModel
     */
    private fun listenViewModel() {
        viewModel.allBlogLiveData.observe(this,
            Observer<List<BlogEntity>> { t ->
                blogBinding.isLoading = false
                adapter.resetAdapter()
                t?.forEach { data ->
                    val itemMode = LFRecyclerItemModel<BlogEntity>(activity, data, BlogItemView::class.java)
                    itemMode.setItemOnClickListener(object : LFRecyclerItemModel.ItemOnClickListener<BlogEntity> {
                        override fun onItemClick(view: View?, data: BlogEntity?, position: Int) {
                            val intent = Intent(activity, BlogDetailActivity::class.java)
                            intent.putExtra("blog_content", data?.blogContent)
                            intent.putExtra("title_name", data?.titleName)
                            activity?.startActivity(intent)
                        }
                    }
                    )
                    adapter.addItem(itemMode)
                    adapter.notifyDataSetChanged()
                }
            })

        viewModel.errorLiveData.observe(this, Observer<String> { src ->
            Toast.makeText(activity, src, Toast.LENGTH_SHORT).show()
            blogBinding.isLoading = false
        })
    }

    /**
     * 登录成功，更新列表数据
     */
    fun loginSucceed() {
        viewModel.getAllTask()
    }


    fun testNofication() {
        AsDUtil.init(ASApp.instance)
            .url("http://downap.oss.aliyuncs.com/WKZF_3.15.apk")
            .childTaskCount(1)
            .name("wkzf_" + "newVersion" + ".apk")
            .build().start(mDownloadCallback)
    }


    private val mDownloadCallback = object : SimpleDownloadCallback() {
        override fun onStart(taskId: Int, currentSize: Long, totalSize: Long, progress: Float) {
            Log.e("andy", "onStart currentSize = $currentSize +  totalSize = $totalSize + progress = $progress")
        }

        override fun onProgress(taskId: Int, currentSize: Long, totalSize: Long, progress: Float) {
            Log.e("andy", "onProgress currentSize = $currentSize +  totalSize = $totalSize + progress = $progress")

        }

        override fun onPause(taskId: Int) {
            Log.e("andy", "onPause")
        }

        override fun onFinish(taskId: Int, file: File) {
        }

        override fun onError(taskId: Int, error: String) {
        }
    }
}