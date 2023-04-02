package com.chen.wxapi.controller;

import com.chen.base.Result;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chen
 * @date 2023.03.31 12:35
 */
@RestController
@RequestMapping("/wx/menu")
public class WxMpMenuController {

    @Autowired
    WxMpService wxMpService;

    /**
     * 获取菜单
     */
    @GetMapping("/get")
    public Result getMenu() throws WxErrorException {
        WxMpMenu wxMpMenu = wxMpService.getMenuService().menuGet();
        return Result.frontOk(wxMpMenu);
    }

    /**
     * 创建菜单
     */
    @RequestMapping("/create")
    public Result createMenu() throws WxErrorException {
        // 菜单
        List<WxMenuButton> buttons = new ArrayList<>();
        WxMenuButton btn1 = new WxMenuButton();
        btn1.setType("click");
        btn1.setName("查询城市");
        btn1.setKey("QUERY_CITY");
        WxMenuButton btn2 = new WxMenuButton();
        btn2.setType("view");
        btn2.setName("跳转网页");
        btn2.setUrl("http://www.csdn.net");
        buttons.add(btn1);
        buttons.add(btn2);
        // 创建
        WxMenu wxMenu = new WxMenu();
        wxMenu.setButtons(buttons);
        String re= wxMpService.getMenuService().menuCreate(wxMenu);
        return Result.frontOk(re);
    }
}
