package com.easyapps.ncraftmedia

import android.app.Application
import android.util.Log
import com.easyapps.ncraftmedia.repository.PostRepository
import com.easyapps.ncraftmedia.repository.PostRepositoryNetworkImpl
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {
    companion object {
        const val TAG = "TEST_PUSH"

        lateinit var postRepository: PostRepository
    }

    override fun onCreate() {
        super.onCreate()

        postRepository = PostRepositoryNetworkImpl()

        obtainToken()
    }

    private fun obtainToken() {
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                val appId = AGConnectServicesConfig.fromContext(this@App).getString("client/app_id")
                Log.d(TAG, "appId = $appId")

                val token = HmsInstanceId.getInstance(this@App).getToken(appId, "HCM")
                if (token.isNotEmpty()) {
                    Log.d(TAG, "token = $token")
                }
            }.onFailure {
                Log.d(TAG, "Error", it)
            }
        }
    }

}
