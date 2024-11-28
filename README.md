 SafeKeys

SafeKeys 是一个用于生成安全密码的 Android 应用程序。它提供了多种选项来定制密码的复杂性和长度，以确保密码的安全性。



UI界面

![UI 界面](https://github.com/dennyops666/SafeKeys/blob/master/ui-demo/ui-demo.jpg?raw=true)

 功能

密码生成：根据用户的设置生成随机密码。
密码选项：
  包含大写字母 (A-Z)
  包含小写字母 (a-z)
  
  包含数字 (0-9)
  包含符号 (!@#$%^&*)
  避免混淆字符 (如 I, l, 1, O, 0)
  设置最少数字和符号的数量
密码长度：用户可以通过滑动条设置密码的长度。
密码强度指示：根据密码长度显示密码强度。
密码可见性切换：用户可以选择显示或隐藏密码。
复制到剪贴板：一键复制生成的密码到剪贴板。

 界面

应用名称 "SafeKeys" 显示在界面的右上角，使用现代化的字体。
直观的用户界面，易于使用。

 安装

1. 克隆此仓库到你的本地机器：
   ```bash
   git clone https://github.com/dennyops666/SafeKeys.git
   ```
2. 使用 Android Studio 打开项目。
3. 连接 Android 设备或启动模拟器。
4. 点击运行按钮以安装应用程序。

 使用

1. 启动 SafeKeys 应用程序。
2. 根据需要调整密码选项和长度。
3. 点击生成按钮以创建新密码。
4. 使用复制按钮将密码复制到剪贴板。

 构建

最低 SDK 版本：24

目标 SDK 版本：34

编译 SDK 版本：35

Kotlin 版本：1.7

gradle 版本：8.11

JDK版本：java 17

依赖
Jetpack Compose
Material3
AndroidX
