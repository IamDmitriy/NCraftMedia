package com.easyapps.ncraftmedia

import android.app.Application
import com.easyapps.ncraftmedia.repository.PostRepository
import com.easyapps.ncraftmedia.repository.PostRepositoryNetworkImpl

class App : Application() {
    companion object {
        lateinit var postRepository: PostRepository
    }

    override fun onCreate() {
        super.onCreate()

        postRepository = PostRepositoryNetworkImpl()
    }

}
