# 产品简介
>本产品是`极验验证`在移动端的产品SDK，为移动端用户提供安全验证的服务。

# 限定条件
> 为了让本产品在您的应用中获得最佳的用户体验，有如下建议，请开发人员务必考虑到：

1. 尽量在WIFI环境下才开启此功能。       
- 请在竖屏状态下使用本SDK。
- 系统要求：android 2.2以上。
- 屏幕硬件要求：不低于800*480的分辨率。

## WIFI环境
> 出于验证安全考虑，目前的验证数据都是在服务器端生成，下载到本地会产生一定的图片流量，故建议在免费的WIFI环境下

## 横向屏幕
> 本SDK目前仅针对竖屏进行了界面设计，后续会根据用户反馈再考虑加入横屏的支持。

## 系统要求
> 安卓2.2以下的操作系统不能保证完全正常运行。古董机型的适配成本太高了。

# 后续计划

1. 用户体验计划持续改进。色彩、交互行为、控件。
- 更多机型的兼容和适配。
- 持续测试和BUG的持续修复




# 项目下载地址

1. [GitHub项目主页](https://github.com/GeeTeam/gt-app-android)
- [发布版下载地址](https://github.com/GeeTeam/gt-app-android/releases)

# Demo启用步骤

1. 从`GitHub`中下载最新发布版项目到本地工作空间
- 使用`Eclipse+ADT`的开发环境`Import`工作空间的两个项目
    - `GtApp`：核心SDK开发包
    - `GtAppSdkDemo`:调用示例
- 直接运行`GtAppSdkDemo`项目

使用以上步骤，用户可以一分钟运行`Demo`示例。


# 自建项目引用

假设用户自建项目名称为：`CustomerProject`

1. 在极验官方主页`www.geetest.com`注册账号并申请相应的应用`公钥`
- 将`GtAppSdk`项目和`CustomerProject`项目Import到同一个工作空间
- 将GtApp项目以`Android Library`的方式进行引用
    右键项目-`Properties`-`Android`-`Library`-`Add`即可
- 以`Dialog`的方式对`GtApp`进行调用，在项目三处`TODO`中替换成用户自已的处理代码。具体示例参见`GtAppSdkDemo`项目


# 重要备注

1. 使用本项目前请务必申请一个自己的应用的公钥并应用到APP中，方便极验后台做针对性的用户流量统计和今后可能的广告收益计算。
- 如果是`Windows`开发平台，请将`GtAppSdk`项目和`CustomerProject`放在同一逻辑盘内，例如`D`盘
- SDK中提供了一个`failback`的验证备选方案功能，为用户极验服务在极端情况下`down`机时提供备选方案。


# 运行效果图

![](res/Screenshot_2014-07-28-20-57-56.png)

# 用户反馈

用户QQ交流群：`161510048`    
在线留言：[用户反馈](http://www.geetest.com/contact/#report)

