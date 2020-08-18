# Interchange
临时文件
# Airtest基础

> 官网：http://airtest.netease.com/

## 安装

方式1：使用 `pip` 来管理安装包和自动安装所有依赖。

```
pip install -U airtest
```

方式2：直接从Git仓库安装。

```
git clone https://github.com/AirtestProject/Airtest.git
pip install -e airtest
```

方式3：AirtestIDE

> 账号：Zombie_July/Yhs45616

zip压缩包，免安装，自带python，直接启动`AirtestIDE.exe`即可。

## 安装模拟器

> 官网：http://mumu.163.com/
>
> 开发者必备：http://mumu.163.com/help/func/20190129/30131_797867.html

在设置中开启开发者模式和usb调试模式。

**连接设备**：`adb connect 127.0.0.1:7555`

注意：如果设备列表里没有显示127.0.0.1:7555设备，则可多次尝试adb kill-server和adb connect 127.0.0.1:7555连接。

