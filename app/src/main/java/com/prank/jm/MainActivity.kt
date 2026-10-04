package com.prank.jm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

/**
 * 第 1 屏：伪装启动页（什么都没有的纯白空页）
 * 加载完成后进入线路选择页。参数见 PrankConfig.kt
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 伪装成 App 正在启动加载中……
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LineSelectActivity::class.java))
            finish()
        }, PrankConfig.SPLASH_DELAY_MS)
    }
}
