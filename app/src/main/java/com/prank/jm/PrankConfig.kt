package com.prank.jm

/**
 * 整蛊配置中心：改这里就够了
 * 流程：启动页 → 線路選擇页（同页先"讀取中"后出按钮）→ 点线路跳转
 */
object PrankConfig {
    /** 跳转目标：原神官网（自带下载入口），可换成下载页或 TapTap 链接 */
    const val DOWNLOAD_URL = "https://ys.mihoyo.com/main/"

    /** 伪装"启动加载"的时长（毫秒），0 = 打开立刻进线路选择页 */
    const val SPLASH_DELAY_MS = 1500L

    /** 线路选择页内"線路讀取中..."状态的时长（毫秒），时间到同一页面原地显示按钮 */
    const val LINE_LOADING_DELAY_MS = 1500L

    /** 点线路后的"正在连接"时长（毫秒），增强真实感 */
    const val LINE_JUMP_DELAY_MS = 700L
}
