package com.chen.wxapi.controller;

import com.chen.base.Result;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2023.03.30 15:31
 */
@RequestMapping("/wx")
@RestController
@Slf4j
public class WxController {
    @Autowired
    WxMpService wxMpService;
    @Autowired
    WxMpMessageRouter wxMpMessageRouter;

    /**
     * 用户配置 token和url时的服务认证
     *  url配置为：http://ip:port/wx
     * 	如果接收不到微信服务的调用，把这个接口开大点，不要限制GET等
     */
    @GetMapping
    public String transfer(String signature, String timestamp, String nonce, String echostr) {
        log.info("接收到来自微信服务器的认证消息：signature ：{}，timestamp：{}，nonce：{}，echostr：{}", signature, timestamp, nonce
                , echostr);
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            log.error("消息不合法");
            return "error";
        }
        return echostr;
    }
    /**
     * 接受消息
     */
    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String handleMessage(@RequestBody String requestBody,
                                @RequestParam("signature") String signature,
                                @RequestParam("timestamp") String timestamp,
                                @RequestParam("nonce") String nonce) {
        log.info("handleMessage调用");
        // 校验消息是否来自微信
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        // 解析消息体，封装为对象
        WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
        WxMpXmlOutMessage outMessage;
        try {
            // 将消息路由给对应的处理器，获取响应
            outMessage = wxMpMessageRouter.route(inMessage);
        } catch (Exception e) {
            log.error("微信消息路由异常", e);
            outMessage = null;
        }
        // 将响应消息转换为xml格式返回
        return outMessage == null ? "" : outMessage.toXml();
    }

    /**
     * 获取关注用户列表
     */
    @GetMapping("/follower")
    public Result<WxMpUserList> getFollowerList() throws WxErrorException {
        String nextUserId = null;
        WxMpUserList wxMpUserList = wxMpService.getUserService().userList(nextUserId);
        return Result.frontOk(wxMpUserList);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/userInfoList")
    public Result getUserInfoList() throws WxErrorException {
        String nextUserId = null;
        WxMpUserList wxMpUserList = wxMpService.getUserService().userList(nextUserId);
        List<WxMpUser> userInfoList = new ArrayList<>();
        for (String openid : wxMpUserList.getOpenids()) {
            userInfoList.add(wxMpService.getUserService().userInfo(openid));
        }
        return Result.frontOk(userInfoList);
    }

}
