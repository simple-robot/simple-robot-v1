## 基于酷q机器人LEMOC插件和java-websocket的酷q机器人插件java开发框架
## 简单说明

​	刚刚接触酷Q没多少时间，对它的插件和java开发的框架了解不多，目前了解到的java插件开发工具的有JCQ，简单看了看，很强大，（在这里先佩服一下作者..），但是对于我个人来讲感觉有些麻烦，好像要配置一些json啊配置必须32位的jre啊啥的。可能因为自己太懒了吧...

​	于是，作为一个Java程序员却没有找到合适的Java开发框架还是比较伤心的，但是我发现了一个叫做LEMOC的插件，他是基于websocket的，可以用java连接，甚至也可以很方便的可以与web服务器整合对接，很合我的胃口。（在这里再次感谢LEMOC插件作者 @[佐天泪子亲卫队](https://cqp.cc/home.php?mod=space&uid=372587)）

​	那么，有了socket接口没有框架怎么办？自己做一个呗。



## 使用说明

> 标记了*的步骤为非必要步骤

- ##### 新建一个项目，导入jar包（maven暂时没有上传，所以暂时没有依赖）

- ##### 创建一个类，继承  `com.forte.qqrobot.RobotApplication` 类

  此类为抽象类，有两个抽象方法：

  ```java
  /** socket连接前 */
  public abstract void beforeLink(LinkConfiguration configuration);
  /** socket连接后 */
  public abstract void afterLink();
  ```



​	其中，方法`beforeLink(LinkConfiguration configuration)`的参数：`LinkConfiguration` 为连接配置对象，使用它来配置连接信息：

```java
//配置装有LEMOC插件的服务器IP地址，不配置则默认为"localhost"
setLinkIp("127.0.0.1");
//配置LEMOC插件配置的端口号，不配置默认为25303
setPort(25303);
//配置客户端，一般情况下不需要配置，如果想要自行配置请参考后续步骤
setSocketClient(Class<? extends QQWebSocketClient> socketClient);
//配置信息接收监听器，监听器的配置请参考后续步骤
registerListeners(SocketListener... listeners);
//使用包扫描加载监听器，(有可能存在BUG)
scannerListener(String packageName);
//配置初始化连接监听器，初始化监听器的配置请参考后续步骤
registerInitListeners(InitListener... listeners);
//使用包扫描加载监听器，(有可能存在BUG)
scannerInitListener(String packageName);
//设定本机QQ号，如果不配置默认为空，则将会无法正常获取是否at的信息
setLocalQQCode(String qqCode);
```



（未完待续）





