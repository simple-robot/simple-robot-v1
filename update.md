## 版本更新记录

- ##### 1.0.01-BETA
为定时任务增加一个抽象类

- ##### 1.0-BETA
增加依赖注入功能：
    增加注解：@Beans, @Depend
    配置类中增加自定义依赖获取接口
    *从本版本开始，所有监听函数均需要标注@Beans注解。*


- ##### 0.9.01-BETA
为InfoResultList接口增加实现接口：Iterable,现在如果返回值为List类型的数据，则可以直接进行遍历了
为InfoResultList接口增加默认实现方法：Stream<T> stream(),现在如果返回值为List类型的数据，则可以转化为Stream对象了

- ##### 0.9-BETA
实现定时任务功能

- ##### 0.8.04-BETA
为BaseApplication增加Closeable接口，表示开发者需要实现用户可手动关闭连接的操作

- ##### 0.8.03-BETA
移除数据库相关依赖
