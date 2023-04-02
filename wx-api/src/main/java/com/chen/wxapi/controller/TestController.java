package com.chen.wxapi.controller;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chen
 * @date 2023.03.30 13:11
 */
@RestController
public class TestController {

    @Autowired
    WxMpService wxMpService;

    @Autowired
    WxMpConfigStorage wxMpConfigStorage;

    @RequestMapping("/test")
    public String test(){
        System.out.println(wxMpConfigStorage);
        return "success";
    }
}
