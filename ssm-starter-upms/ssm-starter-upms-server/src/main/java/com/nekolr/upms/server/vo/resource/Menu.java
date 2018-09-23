package com.nekolr.upms.server.vo.resource;

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
public class Menu {
    private String id;
    private String name;
    private String code;
    private String uri;
    private List<Menu> children;
}
