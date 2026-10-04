package com.prank.jm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

/**
 * 第 2 屏：伪装"線路讀取中..."加载页（截图同款，约 1.5 秒）
 * 读取完毕后进入线路选择页。参数见 PrankConfig.kt
 */
class LineLoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_loading)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LineSelectActivity::class.java))
            finish()
        }, PrankConfig.LINE_LOADING_DELAY_MS)
    }
}
