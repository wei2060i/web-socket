package com.websocket.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yue WeiWei
 * @date 2021/6/1 18:27
 */
@Data
@ApiModel(value = "Message", description = "新添加设备")
public class Message {

    @ApiModelProperty(value = "uid")
    private String userId;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "密码")
    private String passWord;
    @ApiModelProperty(value = "ip")
    private String ip;
    @ApiModelProperty(value = "端口")
    private String port;
}
