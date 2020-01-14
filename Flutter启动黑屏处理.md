# Flutter启动黑屏处理

参考：

黑屏处理：https://flutter.dev/docs/development/add-to-app/android/add-splash-screen

优化：https://juejin.im/post/58ad90518ac2472a2ad9b684

好看的UI界面：https://uimovement.com/?page=2



黑屏解决方案：

```xml
//在 AndroidManifest.xml添加如下meta
<meta-data
    android:name="io.flutter.embedding.android.SplashScreenDrawable"
    android:resource="@drawable/my_splash"
/>
```