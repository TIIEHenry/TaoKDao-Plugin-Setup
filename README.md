# TaoKDao-Plugin-Setup

TaoKDao的插件安装程序

##### 基于内置插件引擎(APK_Plugin)实现的基础通用插件安装程序

### 基于本项目开发的插件

- [RhinoEngine：运行js、Rhino插件引擎支持](https://github.com/TIIEHenry/TaoKDao-APK_Plugin-RhinoEngine)

- [LuaDoc：Lua中文文档](https://github.com/TIIEHenry/TaoKDao-APK_Plugin-LuaDoc)

- [APKSigner：秘钥的管理和加载、签名APK](https://github.com/TIIEHenry/TaoKDao-APK_Plugin-APKSigner)

- [ALuaSDK：运行Lua、AndroLua+工程支持](https://github.com/TIIEHenry/TaoKDao-APK_Plugin-ALuaSDK)

### 使用方法

- 参照 [TaoKDao-API](https://github.com/TIIEHenry/TaoKDao-API)


- 参照已有的插件工程


- ```groovy
  implementation "taokdao.plugins.setup:public_library:+"
  ```


- 在assets/taokdao目录下写插件配置文件

### 调试技巧

1. 在插件工程（非本项目）中，点击AndroidStudio运行按钮左边的app>>edit configurations
2. 找到launch Options>>选择nothing
3. 找到Before launch，点击 + >>run Gradle Task >> Gradle project 选择TaoKDao-API，Tasks填写```:restartTaoKDao```
4. 保存运行即可