package com.nekolr.admin.server.vo.resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 菜单 VO
 *
 * @author nekolr
 */
@Getter
@Setter
@ToString
@ApiModel(value = "Menu 对象", description = "菜单")
public class Menu {
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "组件")
    private String component;
    @ApiModelProperty(value = "路径")
    private String path;
    @ApiModelProperty(value = "图标")
    private String icon;
    @ApiModelProperty(value = "父菜单主键")
    private String parentId;
    @ApiModelProperty(value = "子菜单列表")
    private List<Menu> children;
}
