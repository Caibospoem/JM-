# JM 整蛊 App（原神跳转版）

桌面图标和名称伪装成 jm，点开后**纯白空页"加载"**约 1.5 秒（什么都没有，还原 webview 壳应用冷启动）→ 进入 1:1 还原 jm 的**线路选择页**：先"線路讀取中..."转圈 1.5 秒，然后**同一页面原地**刷出線路1~4（无翻页动画）→ 点任意线路，短暂"连接中"后自动跳转**原神官网**，并从最近任务里抹掉自己。

> ⚠️ 仅供朋友间娱乐整蛊与 Android 学习使用，请勿公开分发或用于任何商业/非法用途。jm 与原神相关名称、图标版权归原作者所有。

---

## 一、项目结构

```
JmPrank/
├── app/src/main/
│   ├── AndroidManifest.xml          # 入口配置（隐藏最近任务等）
│   ├── java/com/prank/jm/
│   │   ├── MainActivity.kt          # 第1屏：伪装启动页（纯白空页）
│   │   ├── LineSelectActivity.kt    # 第2屏：线路选择页（同页先"讀取中"后出按钮，点线路跳原神）
│   │   └── PrankConfig.kt           # ★ 所有可调参数集中在这
│   └── res/
│       ├── layout/activity_main.xml         # 伪装启动页（纯白空页，无任何内容）
│       ├── layout/activity_line_select.xml  # 线路选择页（1:1 还原 jm 截图，含讀取中状态）
│       ├── drawable/bg_line_outline.xml     # 线路按钮：橙色描边胶囊
│       ├── drawable/bg_line_filled.xml      # 线路按钮：橙色填充（線路3 高亮）
│       ├── drawable/bg_green_dot.xml        # "順暢"前的绿色圆点
│       ├── values/strings.xml               # 桌面显示的应用名
│       ├── values/themes.xml                # 深色主题 + LineButton 样式
│       └── mipmap-*/                        # 应用图标（全部密度）
├── build.gradle / settings.gradle   # Gradle 配置
└── gradlew                          # Gradle Wrapper
```

## 二、用 Android Studio 打开

1. 解压本文件夹
2. Android Studio → **Open** → 选择 `JmPrank` 文件夹
3. 首次打开会自动 Sync 并下载依赖（需要联网，耐心等进度条走完）
4. 若提示 Gradle/JDK 版本问题：Gradle Wrapper 已配置 8.7，JDK 用 AS 自带的即可

## 三、三个最常用的自定义点

### 1. 换成真正的 jm 图标
项目里自带的是我画的占位图标（深色底白字 jm）。要一模一样的：
- 在 Android Studio 中右键 `res` → **New → Image Asset**
- Path 选你的 jm 图标图片（可以从已装 jm 的手机截屏图标，或网上找）
- Name 保持 `ic_launcher`，其他默认 → Next → Finish，全套密度自动生成覆盖

### 2. 改应用名称
`app/src/main/res/values/strings.xml`：
```xml
<string name="app_name">JM Comic</string>
```

### 3. 改跳转链接 / 各环节时长
所有参数集中在 `app/src/main/java/com/prank/jm/PrankConfig.kt`：
```kotlin
const val DOWNLOAD_URL = "https://ys.mihoyo.com/main/"   // 跳转目标：原神官网
const val SPLASH_DELAY_MS = 1500L                        // 第1屏 启动页"加载"时长
const val LINE_LOADING_DELAY_MS = 1500L                  // 第2屏 "線路讀取中"状态时长
const val LINE_JUMP_DELAY_MS = 700L                      // 第2屏 选线路后"连接中"时长
```
- 想直达下载页可换成：`https://ys.mihoyo.com/main/download`
- 想用 TapTap：`https://www.taptap.cn/app/139292`（原神 TapTap 页）

### 4. 改线路选择页
- **页面文案**（線路1~4、請選擇線路、順暢、顶部白框错误文字）：`res/values/strings.xml`，其中 `jm_web_error` 是白框里的"Error loading page"，想换成假公告直接改它
- **线路数量 / 布局结构**：`res/layout/activity_line_select.xml`（默认 4 条线路，截图同款，線路3 高亮）
- **点击行为**（想给某条线路特殊逻辑）：`LineSelectActivity.kt`
- **颜色**（橙/绿/灰底）：`res/values/colors.xml`

## 四、打包 APK 发给朋友

1. 菜单 **Build → Build App Bundle(s) / APK(s) → Build APK(s)**
2. 完成后点右下角弹窗 **locate**，APK 在：
   `app/build/outputs/apk/debug/app-debug.apk`
3. 把 APK 发给朋友（微信发送可先压缩成 zip 再改回，或用网盘/蓝牙），对方安装时允许"未知来源"即可
4. 支持 Android 7.0 ~ 15

## 五、整蛊效果细节（已内置）

| 细节 | 说明 |
|---|---|
| 伪装启动页 | 什么都没有的纯白背景，1.5 秒后进入线路选择页 |
| 线路选择页 | 1:1 还原 jm 截图：白色公告框、請選擇線路、線路1~4 胶囊按钮（綠点"順暢"，線路3 橙色高亮） |
| 同页加载切换 | 进页面先显示"線路讀取中..."白色转圈（含滚动条痕迹），1.5 秒后**同一页面原地**刷出按钮，无翻页动画 |
| 点线路的反馈 | 按钮变暗像"连接中"，0.7 秒后弹出原神官网 |
| 跳转后无痕 | `finishAndRemoveTask` + `excludeFromRecents`，最近任务里不残留 |
| 线路页无闪烁 | 线路页窗口背景与灰底同色，从白屏转场过来不突兀 |
| 兜底处理 | 设备没有浏览器时静默退出，不会闪退露馅 |

## 六、常见问题

**Q：Sync 报错 "Your project path contains non-ASCII characters"**
项目所在路径含中文导致（如 `E:\project\JM整蛊App源码\`），AGP 8 默认拒绝非英文路径。两种解决：
- 推荐：把项目文件夹移到**纯英文路径**，如 `E:\project\JmPrank`
- 或在 `gradle.properties` 中加一行 `android.overridePathCheck=true`（本项目已预加此行）

**Q：Gradle 发行版 / 依赖下载失败（Connection refused）**
国内网络连不上 `services.gradle.org` / `google()` 仓库导致。可用代理，或将下载源换为国内镜像（腾讯云 gradle 镜像 + 阿里云 maven 镜像）。
