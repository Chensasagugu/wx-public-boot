package com.chen.wxapi.config;

import com.chen.wxapi.handler.SubscribeHandler;
import com.chen.wxapi.handler.TextHandler;
import com.chen.wxapi.handler.UnSubscriveHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chen
 * @date 2023.03.31 16:39
 */
@Configuration
public class MessageRouterConfig {

    @Autowired
    WxMpService wxMpService;

    @Autowired
    TextHandler textHandler;
    @Autowired
    SubscribeHandler subscribeHandler;
    @Autowired
    UnSubscriveHandler unSubscriveHandler;

    @Bean
    public WxMpMessageRouter messageRouter() {
        // 创建消息路由
        final WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 添加文本消息路由
        router.rule().async(false).msgType(WxConsts.XmlMsgType.TEXT).handler(textHandler).end();

        //添加关注事件路由
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE)
                .handler(subscribeHandler).end();
        //添加取消关注事件路由
        router.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(unSubscriveHandler).end();
        return router;
    }
}
