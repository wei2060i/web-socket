package com.websocket.api;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yue WeiWei
 * @date 2021/6/29 17:04
 */
@CrossOrigin(origins = "*", allowCredentials = "true")
@RestController
@RequestMapping
public class QrCodeApi {

    /**
     * 获取二维码
     *
     * @param request
     * @param response
     */
    @GetMapping("getLoginQr")
    public void createCodeImg(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        try {
            //这里没啥操作 就是生成一个UUID插入 数据库的表里。
            //String uuid = userService.createQrImg();
            String uuid = RandomUtil.randomNumbers(5);
            response.setHeader("uuid", uuid);
            //这里是开源工具类 huTool里的QrCodeUtil
            QrCodeUtil.generate(uuid, 300, 300, "jpg", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
