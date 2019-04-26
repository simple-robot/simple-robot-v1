/**
 * 此包下定义所有sender相关方法接口并提供一个汇总接口。<br>
 * 其中，
 *  <ul>
 *      <li>{@link com.forte.qqrobot.sender.senderlist.SenderGetList }</li>
 *      <li>{@link com.forte.qqrobot.sender.senderlist.SenderSendList}</li>
 *      <li>{@link com.forte.qqrobot.sender.senderlist.SenderSetList}</li>
 *  </ul>
 *  这三个接口均继承{@link com.forte.qqrobot.sender.senderlist.SenderList} 接口，其余接口均作为汇总或增强接口，不会直接使用。
 *  <br>
 * 接口中所有返回值为布尔类型的方法若无特殊标注则均代表方法的执行成功与否。
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @since JDK1.8
 **/
package com.forte.qqrobot.sender;