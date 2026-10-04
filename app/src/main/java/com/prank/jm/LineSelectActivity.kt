package com.prank.jm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

/**
 * 第 2 屏：1:1 仿 jm 的"請選擇線路"页面（单页面）
 * 流程：先显示"線路讀取中..."转圈 → 时间到后【同一页面原地】刷出线路按钮（无翻页动画）
 * → 点击任意线路 → 短暂"连接中" → 跳转原神官网，并抹掉自己。参数见 PrankConfig.kt
 */
class LineSelectActivity : AppCompatActivity() {

    private var jumping = false   // 防止连点导致多次跳转

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_select)

        val loadingSection = findViewById<LinearLayout>(R.id.loading_section)
        val buttonsSection = findViewById<LinearLayout>(R.id.buttons_section)
        val scrollHint = findViewById<View>(R.id.web_scroll_hint)

        // 四条线路全部接上同一个跳转逻辑（想给某条线路不同行为改这里）
        val buttons = listOf(
            findViewById<LinearLayout>(R.id.btn_line1),
            findViewById(R.id.btn_line2),
            findViewById(R.id.btn_line3),
            findViewById(R.id.btn_line4)
        )
        buttons.forEach { it.setOnClickListener { view -> go(view) } }

        // 模拟"線路讀取中..."：时间到后同一页面原地切换，没有任何转场动画
        Handler(Looper.getMainLooper()).postDelayed({
            loadingSection.visibility = View.GONE
            scrollHint.visibility = View.GONE     // 滚动条痕迹随读取完成消失
            buttonsSection.visibility = View.VISIBLE
        }, PrankConfig.LINE_LOADING_DELAY_MS)
    }

    private fun go(button: View) {
        if (jumping) return
        jumping = true
        button.alpha = 0.6f   // 给个"点了"的反馈，像真的在连线路

        Handler(Looper.getMainLooper()).postDelayed({
            try {
                // 交给系统浏览器打开原神下载页
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PrankConfig.DOWNLOAD_URL)))
            } catch (_: Exception) {
                // 设备上没有任何浏览器时静默退出
            } finally {
                // 结束自己，且不在"最近任务"里留下痕迹
                finishAndRemoveTask()
            }
        }, PrankConfig.LINE_JUMP_DELAY_MS)
    }
}
