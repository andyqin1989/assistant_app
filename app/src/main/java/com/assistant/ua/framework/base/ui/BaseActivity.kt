package com.assistant.ua.framework.base.ui

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.assistant.ua.framework.base.BasePresenter
import com.assistant.ua.framework.permission.*

/** Created by qinbaoyuan on 2018/11/10
 */
abstract class BaseActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 0x12

    private var presenter: BasePresenter? = null
    fun initPresenter() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
    }

    /**
     * 查询是否需要授权
     */
    fun askPermission(allPermissions: Array<String>?) {
        if (allPermissions == null || allPermissions.isEmpty()) {
            onPermissionGranted()
            return
        }
        val needApplyList = ArrayList<String>()
        var needApplyArray: Array<String?>? = null
        var needRationale = false
        for (permission in allPermissions) {
            if (!PermissionHelper.hasPermission(this, permission)) {
                needApplyList.add(permission)
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    needRationale = true
                }
            }
        }
        if (needApplyList.size > 0) {
            needApplyArray = arrayOfNulls(needApplyList.size)
            needApplyList.toArray(needApplyArray)
        }
        if (needApplyArray != null && needApplyArray.isNotEmpty()) {
            if (needRationale) {
                Toast.makeText(this, "为了您的正常使用，请授权给小秘书", Toast.LENGTH_SHORT).show()
            }
            requestPermission(needApplyArray)
        } else {
            onPermissionGranted()
        }
    }

    /**
     * 调用系统方法，获取授权
     */
    private fun requestPermission(permissions: Array<String?>) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
    }

    /**
     * 外部传入授权回调，确定授权结果之后，通过此回调，将结果传递给调用者
     */
    var resultAction: PermissionResultAction? = null

    /**
     * 权限-确定授权
     */
    private fun onPermissionGranted() {
        resultAction?.onGranted()
    }

    /**
     * 权限-拒绝授权
     */
    private fun onPermissionDenied(permissions: ArrayList<String>) {
        resultAction?.onDenied(permissions)
    }

    /**
     * 授权结果方法，系统调用，在此方法中处理结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val count = grantResults.size
            var result = true
            val deniedPermissionList = ArrayList<String>()

            for (i in 0 until count) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    result = false
                    deniedPermissionList.add(permissions[i])
                }
            }
            if (result) {
                onPermissionGranted()
            } else {
                onPermissionDenied(deniedPermissionList)
            }
        }
    }

    /**
     * 基础权限授权
     */
    fun applyBasePermission() {
        PermissionManager.getInstance()
            .applyPermission(this, Permission.getBasePermission(), basePermissionResultAction)
    }

    /**
     * 基础权限授权回调
     */
    private val basePermissionResultAction = object : PermissionResultAction {
        override fun onGranted() {
            onBasePermissionGranted()
        }

        override fun onDenied(permissions: java.util.ArrayList<String>?) {
            PermissionManager.getInstance()
                .showPermissionDialog(this@BaseActivity, permissions?.get(0), basePermissionDialogListener)
        }
    }

    /**
     * 基础服务
     */
    val basePermissionDialogListener = object : PermissionDialogListener {
        override fun cancel() {
            this@BaseActivity.finish()
        }

        override fun goSetting() {
            this@BaseActivity.finish()
        }

    }


    protected open fun onBasePermissionGranted() {}


    /**
     * 授权提醒弹出框，按钮点击之后的回调
     */
    var permissionDialogListener: PermissionDialogListener? = null

    /**
     * 授权提醒弹出框，按钮点击回调
     */
    private val permissionDialogClickListener = DialogInterface.OnClickListener { dialog, which ->
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> {
                dialog.dismiss()
                PermissionSettings.goSetting(this)
                permissionDialogListener?.goSetting()
            }
            DialogInterface.BUTTON_NEGATIVE -> {
                dialog.dismiss()
                permissionDialogListener?.cancel()
            }
        }
    }

    fun showPermissionDialog(permission: String, listener: PermissionDialogListener?) {
        AlertDialog.Builder(this).setTitle("权限设置")
            .setMessage(PermissionHelper.getPermissionMsg(permission))
            .setPositiveButton("去设置", permissionDialogClickListener)
            .setNegativeButton("取消", permissionDialogClickListener)
            .create()
            .show()
        permissionDialogListener = listener
    }

}