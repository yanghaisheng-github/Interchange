参考：

http://www.flutterj.com/

仿微信示例：https://github.com/fluttercandies/wechat_flutter

* 运行修改：

* 问题1： Could not resolve com.android.tools.build:gradle:3.1.2

  * 解决方法如下：

  * 修改android/build.gradle文件

    * ```
      ext.kotlin_version = '1.3.21'
      dependencies {
              classpath 'com.android.tools.build:gradle:3.5.3'
              classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
      }
      ```

    * ext.kotlin_version修改为1.3.21

    * 第一个classpath修改为android studio的版本号，可通过打开android studio查看

  * 修改android/gradle/wrapper/gradle-wrapper.properties文件

    * 需要将distributionUrl修改为本地路径，原先是 https::url 类型的  在 [gradle](https://services.gradle.org/distributions/)官网下载gradle包。但是无法下载。
    * distributionUrl=file:///C:/Users/Administrator/.gradle/wrapper/dists/gradle-5.6.2-all/9st6wgf78h16so49nn74lgtbb/gradle-5.6.2-all.zip

​           