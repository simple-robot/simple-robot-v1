# 基于网络接口的酷Q机器人java开发框架核心

# 警告！
从2019-04-29开始，核心部分与组件部分开始分离！

~~*目前还没想好是组件与核心分git地址还是分分支*~~

*目前核心框架存在于分支`core`中*

**以下文档为旧文档，内容与新框架内容几乎完全是两个东西..目前留着仅作为纪念。**

新文档请见文档地址：
https://www.kancloud.cn/forte-scarlet/simple-coolq-doc/1049734

由于目前核心部分与组件部分相互分离，所以核心与组件的github地址也相互分离了。
所有组件均依赖于核心框架，~~但是由于支持组件均暂时未上传maven中央仓库，所以请暂时加群以获取jar包~~

已经支持maven坐标，具体坐标请见文档或入群咨询

qq群号：782930037

LEMOC支持组件GIT地址：https://github.com/ForteScarlet/simple-robot-component-lemoc


HTTP API支持组件GIT地址：https://github.com/ForteScarlet/simple-robot-component-httpapi 


------

# 以下为已弃用文档，仅留作纪念
# 以下为已弃用文档，仅留作纪念
# 以下为已弃用文档，仅留作纪念

------


## 简单说明

   
​	刚刚接触酷Q没多少时间，对它的插件和java开发的框架了解不多，目前了解到的java插件开发工具的有JCQ，简单看了看，很强大（在这里先夸一下作者），但是对于我个人来讲感觉有些麻烦，好像要配置一些json啊配置必须32位的jre啊啥的。可能因为自己太懒了吧...

​	于是，作为一个Java程序员却没有找到合适的Java开发框架还是比较伤心的，但是我发现了一个叫做LEMOC的插件，他是基于websocket的，可以用java连接，甚至也可以很方便的可以与web服务器整合对接，很合我的胃口。



​	那么，有了socket接口没有框架怎么办？自己做一个呗。

​	(但是由于LEMOC作者已经弃坑许久，接口存在很多BUG且接口较旧，于是便同时在项目中整合了HTTP API插件，并将逐渐将框架的使用插件由LEMOC向HTTP API重构转化)



> 虽然没什么人，但是我姑且还是建了个群
>
> 而且写文档好麻烦好累啊...

qq群号：<a target="_blank" href="//shang.qq.com/wpa/qunwpa?idkey=49b51808c33ec198f3799476e786f7577d9ca5ea6a857ba00bbac07dac03ca09"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="simple-coolQ扯" title="simple-coolQ扯"></a>

qq群号：782930037


## 使用说明

***！！！警告！！！目前项目正在重构，将会极大程度的更变包结构、API调用方式、参数接收形式等，以下文档仅部分适用于版本0.61或以下。此文档目前请仅用于参考。(注解使用方式没有大变化)***

> 注：如果标记了*的步骤为 **非必要** 步骤

#### 首先

- ##### 新建一个项目，导入jar包（maven暂时没有上传，所以暂时没有依赖）

- ##### 创建一个类，继承  `com.forte.qqrobot.xxxApplication` 类

> ※ 后期此类将会分离为两个类以兼容两种插件
>
> 此类为抽象类，有两个抽象方法：

```java
/** socket连接前，参数为配置类，使用此配置类来对必要的参数进行配置(详见下文) */
public abstract void beforeLink(LinkConfiguration configuration);
/** socket连接后，参数为CQ码工具类和socket消息发送器，主要目的为便于进行测试 */
public abstract void afterLink(CQCodeUtil cqCodeUtil, QQWebSocketMsgSender sender);
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
//使用包扫描加载监听器
configuration.scannerListener(String packageName);
//配置初始化连接监听器，初始化监听器的配置请参考后续步骤
configuration.registerInitListeners(InitListener... listeners);
//使用包扫描加载监听器
configuration.scannerInitListener(String packageName);
//*设定本机QQ号，如果不配置默认为空，在连接后框架会尝试自动获取，假如获取失败且未手动配置则会无法正常获取是否at的信息
configuration.setLocalQQCode(String qqCode);
//*设置本机QQ昵称，如果不配置默认为空，在连接后框架将会尝试自动获取，假如获取失败且未手动配置则会无法正常获取本机QQ的昵称
configuration.setLocalQQNick("QQ昵称");

//配置HTTP API插件信息
//HTTP API插件动态交互监听ip和端口,如果不配置将无法使用相关API
//IP默认为localhost
configuration.setHTTP_API_ip("xxx.xxx.xx.xx");
//端口默认为8877
configuration.setHTTP_API_port(8877);
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
@Listen(MsgGetTypes[])
```



此注解可以使用在类上或方法上，方法必须是公共的(`public`)、非静态的。

当在类上使用此注解的时候，此类下所有符合条件的方法都会作为监听函数被尝试注册。

假如你在类上标注了`@Listen`注解，但是类中有你不想被注册方法，请在此方法上标注忽略注解：

```java
@Ignore
```

> 此注解仅在使用`@Listen`注解注册函数的时候生效，接口实现的方法使用此注解将不会生效。



如果类上与方法上都有注解，优先方法上的注解。

`@Listen`注解有一个`MsgGetTypes`类型的参数，此参数为一个枚举类型，罗列的从酷Q获取到的消息的类型。

> (详情参考后续 ***枚举*** 条目)



如果你的监听函数所在的类在创建实例的时候不能使用无参构造，那么请使用实例注解：

```java
@Constr
```



使用此注解标注一个静态方法，方法的返回值应为此监听器自己的类型，监听函数解析器会优先尝试使用标注了`@Constr`的静态方法来获取一个监听器的实例类。

> 被`@Constr`标注的静态方法请不要有参数。





##### 参数

> 实现接口或使用注解，都会使用到以下参数，当监听方法中出现了以下参数的时候，监听函数管理器会自动将对应类型的参数注入到参数中。※以下参数的数据类型在参数集中唯一存在。

| 参数类型                            | 含义                                                         |
| :---------------------------------- | :----------------------------------------------------------- |
| `? extends MsgGet`                  | 一个实现了`MsgGet`接口的封装类，代表了接收到的消息内容的封装(详见接收消息类型列表或者查阅java doc文档) |
| `CQCode[] cqCode`                   | 接收到的消息中，如果存在正文文本，那么此数组将代表正文中出现的CQ码信息(按照顺序)，且正文文本中的CQ码将依旧存在，不做变化 |
| `boolean at`                        | 是否被at了，这个参数在非群组类型消息的时候必然为false        |
| `CQCodeUtil cqCodeUtil`             | 操作CQ码的工具类，比如生成CQ码字符串、移除消息中的全部CQ码、信息字符串中提取出`CQCode`码的字符串/对象、判断是否存在@某个QQ等。 |
| `MsgSender sender`                  | 消息发送器，用于**消息响应**、**信息获取**和进入**阻断状态**。消息发送器中(目前)存在两个公共静态常量参数：`QQWebSocketMsgSender SOCKET_MSG_SENDER` （LEMOC插件的socket消息发送器），   `QQHttpMsgSender   HTTP_MSG_SENDER`（HTTP API插件的动态交互消息获取器）；两个参数可以单独注入(如下列) |
| `QQWebSocketMsgSender socketSender` | 基于LEMOC插件API的信息发送器，可以用于消息的响应与部分信息的获取(登录信息等)  ※ 主要用于发送消息，其用于获取部分信息也是使用socket，框架对其的处理不保证不存在一些BUG |
| `QQHttpMsgSender httpSender`        | 基于HTTP API插件API的信息发送器，目前仅能用于信息的获取(群列表等)  ※ 目前主要用于信息的获取，由于是HTTP请求与响应的形式，不会出现获取的信息内容不准确等问题。 |

> 其中，初始化监听器中只存在`CQCodeUtil cqCodeUtil` 和 `MsgSender sender` 这两个参数。





##### 注解

> 目前的注解只能使用在普通的监听器上。

除了上面提到用来注册监听函数的`@Listen`、忽略用的`@Ignore`、获取实例对象的`@Constr`注解以外，还有一些别的注解，有着不同的功能：

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
  | keywordMatchType | `KeywordMatchType` | `KeywordMatchType.REGEX` | 枚举类型，代表了匹配的方式。*（详细类型详见后续的枚举条目）* |
  | mostType         | `MostType`         | `MostType.ANY_MATCH`     | 枚举类型，代表如果`value`参数中数量大于1时候的多个正文匹配的匹配规则*。（详细类型详见后续的枚举条目）* |
  | at               | `boolean`          | `false`                  | 是否需要被at才接收此消息，默认为false。***如果为私聊且at参数为true的话的话将会永远接收不到消息，毕竟没人会在私聊的时候@你*** |

  使用范围：监听器下方法( 暂时不支持在类上进行标注 )

- ##### @Block

  > 阻断注解，用于定义一个监听函数的阻断名称	
  >
  > 此注解并不是必须的，但是如果你需要使用阻断机制，我建议你加上。毕竟阻断机制阻断一个函数集是依据阻断名称来分类的。

  参数：

  | 参数    | 类型       | 默认值 | 含义                                     |
  | ------- | ---------- | ------ | ---------------------------------------- |
  | `value` | `String[]` | `{}`   | 被标注的函数的阻断名。阻断名可以有多个。 |

- ##### @BlockFilter

  > 此注解的参数、含义与`@Filter`完全相同，唯一一点不同的就是此注解是当标注的函数进入了阻断状态的时候才生效。
  >
  > 当消息接收，且函数为阻断状态，那么事件过滤器会优先用时`@BlockFilter`来过滤消息，假如没有则会尝试使用`@Filter`的过滤机制来过滤。



##### 枚举

> 此条目会将全部出现的枚举类型的各个类别和其对应的意义罗列出来，以便查阅

- ##### CQCodeTypes

  > 此枚举定义了我从官方文档中看到的全部CQ码类型

  | 类型           | 含义                                             |
  | -------------- | ------------------------------------------------ |
  | `defaultType`  | 一个默认的空类型，一般情况下不会遇到他。         |
  | `face`         | QQ表情                                           |
  | `bface`        | 原创表情，id为路径，存放在酷Q目录的data\bface\下 |
  | `sface`        | 小表情                                           |
  | `image`        | 图片                                             |
  | `record`       | 语音                                             |
  | `at`           | @某人                                            |
  | `rps`          | 猜拳魔法表情                                     |
  | `dice`         | 掷骰子魔法表情                                   |
  | `shake`        | 戳一戳（原窗口抖动，仅支持好友消息使用）         |
  | `anonymous`    | 匿名发消息（仅支持群消息使用）                   |
  | `music`        | 音乐分享，目前支持qq、163、xiami                 |
  | `music_custom` | 音乐自定义分享                                   |
  | `share`        | 链接分享                                         |
  | `emoji`        | emoji表情                                        |

- ##### KeywordMatchType

  > 此枚举定义了使用`@Listen`注解的时候，对消息的过滤的匹配原则。

  | 类型                      | 含义                                   |
  | ------------------------- | -------------------------------------- |
  | `REGEX`                   | 使用正则规则匹配                       |
  | `TRIM_REGEX`              | 开头结尾去空后正则匹配                 |
  | `RE_CQCODE_REGEX`         | 移除掉所有CQ码后正则匹配               |
  | `RE_CQCODE_TRIM_REGEX`    | 移除掉所有CQ码并开头结尾去空后正则匹配 |
  | `EQUALS`                  | 使用完全相同匹配                       |
  | `TRIM_EQUALS`             | 开头结尾去空后相同匹配                 |
  | `RE_CQCODE_EQUALS`        | 移除掉所有CQ码后相同匹配               |
  | `RE_CQCODE_TRIM_EQUALS`   | 移除掉所有CQ码并开头结尾去空后相同匹配 |
  | `CONTAINS`                | 包含匹配                               |
  | `TRIM_CONTAINS`           | 去空的包含匹配                         |
  | `RE_CQCODE_CONTAINS`      | 移除掉所有CQ码后包含匹配               |
  | `RE_CQCODE_TRIM_CONTAINS` | 移除掉所有CQ码并开头结尾去空后包含匹配 |

- ##### MostType

  > 此枚举只有在你从`@Listen`注解中过滤的关键词大于1的时候才生效；此枚举规定了当有多个关键词过滤的时候，这多个关键词的匹配规则。

  | 类型        | 含义                   |
  | ----------- | ---------------------- |
  | EVERY_MATCH | 全部关键词都匹配才通过 |
  | ANY_MATCH   | 任意一个关键词匹配即可 |
  | NONE_MATCH  | 没有关键词匹配才通过   |

- ##### MsgGetTypes

  > 此枚举你可能不会遇到，但是姑且在这里提一下..这个枚举保存了所有(LEMOC插件)事件的类型

  | 类型                     | 含义                                                   |
  | ------------------------ | ------------------------------------------------------ |
  | `msgPrivate`             | 私信信息                                               |
  | `msgDisGroup`            | 讨论组信息                                             |
  | `msgGroup`               | 群消息                                                 |
  | `eventFriendAdded`       | 事件-好友添加                                          |
  | `eventGroupAdmin`        | 事件-管理员变动                                        |
  | `eventGroupMemberJoin`   | 事件-群成员增加                                        |
  | `eventGroupMemberReduce` | 事件-群成员减少                                        |
  | `requestFriend`          | 请求-添加好友                                          |
  | `requestGroup`           | 请求-群添加                                            |
  | `unknownMsg`             | 未知的消息，当出现了不是以上类型的类型时，使用此类型。 |

（未完待续）





