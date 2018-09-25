package com.nekolr.upms.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 资源 DTO
 *
 * @author nekolr
 */
@Getter
@Setter
@ToString
public class ResourceDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String code;
    private String uri;
    private Integer type;
    private String component;
    private String parentId;
    private String method;
    private String icon;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer sortOrder;
    private Boolean status;
    private Boolean deleteFlag;
}
