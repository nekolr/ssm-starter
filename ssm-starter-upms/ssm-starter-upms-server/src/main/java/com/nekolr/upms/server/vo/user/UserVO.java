package com.nekolr.upms.server.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别（0女 1男 2保密）")
    private Integer sex;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "修改时间")
    private String updateTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除（0未删除 1已删除）")
    private Boolean deleteFlag;
}
