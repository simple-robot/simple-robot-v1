## 基于酷q机器人LEMOC插件和java-websocket的酷q机器人插件java开发框架
## 简单说明

​	刚刚接触酷Q没多少时间，对它的插件和java开发的框架了解不多，目前了解到的java插件开发工具的有JCQ，简单看了看，很强大（在这里先夸一下作者），但是对于我个人来讲感觉有些麻烦，好像要配置一些json啊配置必须32位的jre啊啥的。可能因为自己太懒了吧...

​	于是，作为一个Java程序员却没有找到合适的Java开发框架还是比较伤心的，但是我发现了一个叫做LEMOC的插件，他是基于websocket的，可以用java连接，甚至也可以很方便的可以与web服务器整合对接，很合我的胃口。



​	那么，有了socket接口没有框架怎么办？自己做一个呗。

​	(但是由于LEMOC作者已经弃坑许久，接口存在很多BUG且接口较旧，于是便同时在项目中整合了HTTP API插件，并将逐渐将框架的使用插件由LEMOC向HTTP API重构转化)



## 使用说明

> 注：标记了*的步骤为 **非必要** 步骤

#### 首先

- ##### 新建一个项目，导入jar包（maven暂时没有上传，所以暂时没有依赖）

- ##### 创建一个类，继承  `com.forte.qqrobot.RobotApplication` 类

> 此类为抽象类，有两个抽象方法：
>

```java
/** socket连接前 */
public abstract void beforeLink(LinkConfiguration configuration);
/** socket连接后 */
public abstract void afterLink();
```



> ​	其中，方法`beforeLink(LinkConfiguration configuration)`的参数：`LinkConfiguration` 为连接配置对象，使用它来配置连接信息：
>

```java
//配置装有LEMOC插件的服务器IP地址，不配置则默认为"localhost"
configuration.setLinkIp("127.0.0.1");
//配置LEMOC插件配置的端口号，不配置默认为25303
configuration.setPort(25303);
//配置客户端，一般情况下不需要配置，如果想要自行配置请参考后续步骤
configuration.setSocketClient(Class<? extends QQWebSocketClient> socketClient);
//配置信息接收监听器，监听器的配置请参考后续步骤
configuration.registerListeners(SocketListener... listeners);
//使用包扫描加载监听器，(有可能存在BUG)
configuration.scannerListener(String packageName);
//配置初始化连接监听器，初始化监听器的配置请参考后续步骤
configuration.registerInitListeners(InitListener... listeners);
//使用包扫描加载监听器，(有可能存在BUG)
configuration.scannerInitListener(String packageName);
//设定本机QQ号，如果不配置默认为空，在连接后框架会尝试自动获取，假如获取失败且未手动配置则会无法正常获取是否at的信息
configuration.setLocalQQCode(String qqCode);
//设置本机QQ昵称，如果不配置默认为空，在连接后框架将会尝试自动获取，假如获取失败且未手动配置则会无法正常获取本机QQ的昵称
configuration.setLocalQQNick("QQ昵称");
```



#### 监听器的实现

> 监听器的实现是实现机器人功能的重要实现手段，以下所有出现的监听器接口都在 `com.forte.qqrobot.listener` 包及其子包下

##### 初始化监听器

> 初始化监听器是在连接成功后优先执行的监听器，可以有多个也可没有。
>
> 注：多个监听器的调用顺序无序且采用异步调用的方式，所以可能会出现初始化监听器未执行完就接收到了消息的情况，敬请注意。

- ##### 监听器方法

> 创建一个类并实现初始化监听器接口：`InitListener`，初始化监听器接口存在一个方法：

```java
  /**
   * 初始化方法
   */
    void init(CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender);
```

（参数所代表的意义将会在后续讲到。）

​	

##### 普通监听器

##### 实现方式：

> 普通的监听器与初始化监听器不同，有多种实现方式(目前有两种)

###### 实现监听器接口

> 除了初始化监听器以外，其余的就是普通的监听器了，与初始化监听器相同，创建一个类并实现任意数量的以下监听器接口，即可开始完成你的逻辑了

| 监听器类名                       | 监听类型          |
| -------------------------------- | ----------------- |
| `EventFriendAddedListener`       | 事件-好友添加     |
| `EventGroupAdminListener`        | 事件-群管理员变动 |
| `EventGroupMemberJoinListener`   | 事件-群成员增加   |
| `EventGroupMemberReduceListener` | 事件-群成员减少   |
| `MsgDisGroupListener`            | 消息-讨论组       |
| `MsgGroupListener`               | 消息-群           |
| `MsgPrivateListener`             | 消息-私信         |
| `RequestFriendListener`          | 请求-好友添加     |
| `RequestGroupListener`           | 请求-群添加       |

> 以上即为目前全部可以使用的监听器了。每一个监听器都有一个返回值为布尔的`onMessage`方法，这个方法即为监听器触发的时候的回调函数。每一个`onMessage`方法都会有5个参数。除了第一个参数每个监听器不同之外，以外的全部参数都是相同的，参数的含义对照表请见后续步骤。





###### 使用监听器注解

> 除了实现监听器接口，我们也可以使用监听器注解来实现一个监听器函数。

在类或者方法上使用注解：

```java
@Listen(MsgGetTypes)
```



此注解可以使用在类上或方法上，方法必须是公共的(`public`)、非静态的。

当在类上使用此注解的时候，此类下所有符合条件的方法都会作为监听函数被尝试注册。

假如你在类上标注了`@Listen`注解，但是类中有你不想被注册方法，请在此方法上标注忽略注解：

```
@Ignore
```

> 此注解仅在使用`@Listen`注解注册函数的时候生效，接口实现的方法使用此注解将不会生效。

如果类上与方法上都有注解，优先方法上的注解。

`@Listen`注解有一个`MsgGetTypes`类型的参数，此参数为一个枚举类型，罗列的从酷Q获取到的消息的类型，以下为此枚举类型对应的消息类型对照表：











##### 参数

> 实现接口或使用注解，都会使用到以下参数，当监听方法中出现了以下参数的时候，监听函数管理器会自动将对应类型的参数注入到参数中。

| 参数类型                | 含义                                                         |
| :---------------------- | :----------------------------------------------------------- |
| `? extends MsgGet`      | 一个实现了`MsgGet`接口的封装类，代表了接收到的消息内容的封装 |
| `CQCode[] cqCode`       | 接收到的消息中，如果存在正文文本，那么此数组将代表正文中出现的CQ码信息(按照顺序)，且正文文本中的CQ码将依旧存在，不做变化 |
| `boolean at`            | 是否被at了，这个参数在非群组类型消息的时候必然为false        |
| `CQCodeUtil cqCodeUtil` | 操作CQ码的工具类，比如生成CQ码字符串、移除消息中的全部CQ码、信息字符串中提取出`CQCode`码的字符串/对象、判断是否存在@某个QQ等。 |
| `MsgSender sender`      | 消息发送器，用于响应消息                                     |

> 其中，初始化监听器中只存在`CQCodeUtil cqCodeUtil` 和 `MsgSender sender` 这两个参数。





##### 注解

> 目前的注解只能使用在普通的监听器上。

- ##### @Spare

  > 备用方法
  >
  > 此注解可以在监听器类上或监听器下的方法上，如果标注在类上则类型下所有的方法都将会被视为标记了此注解。被标注了@Spare注解的方法将作为备用方法。
  >
  > 备用方法在其他所有非备用方法都没有被使用的情况下才能够被触发。每一个监听器的回调方法都有布尔类型的返回值，此返回值标注了此方法是否是被执行成功的。
  >
  > 当一个事件出现了，框架将会先在非备用的监听器中执行，假如没有非备用的监听器执行成功，才会尝试在备用方法中进行执行。

  参数：无

  使用范围：监听器类、监听器下方法





- ##### @Filter

  > 事件过滤器，当一个事件出现了，将会根据注解的参数进行筛选，只有符合条件的方法才会被尝试执行。未标注此注解的方法则会在任何情况下被尝试执行。

  参数：

  | 参数             | 类型               | 默认值                   | 含义                                                         |
  | ---------------- | ------------------ | ------------------------ | ------------------------------------------------------------ |
  | value            | `String[]`         | `{}`                     | 如果消息有正文，则此参数代表了需要匹配的正文信息             |
  | keywordMatchType | `KeywordMatchType` | `KeywordMatchType.REGEX` | 枚举类型，代表了匹配的方式。`REGEX` ：正则匹配、`EQUALS`：完全相同匹配、`CONTAINS`：包含匹配，且在类型前加`TRIM_`则是先去除首尾空格再进行匹配，例如：`TRIM_REGEX` |
  | mostType         | `MostType`         | `MostType.ANY_MATCH`     | 枚举类型，代表如果`value`参数中数量大于1时候的多个正文匹配的匹配规则。`EVERY_MATCH`：全部匹配、`ANY_MATCH`：任意匹配、`NONE_MATCH`：没有匹配 |
  | at               | `boolean`          | `false`                  | 是否需要被at才接收此消息，默认为false。***如果为私聊且at参数为true的话的话将会永远接收不到消息*** |

  使用范围：监听器下方法( 暂时不支持在类上进行标注 )

​	

（未完待续）





