package com.nekolr.common;

/**
 * 全局常量类
 *
 * @author nekolr
 */
public class Constants {

    // ----------------------------------------- Global -----------------------------------------

    /**
     * 扫描基础包名
     */
    public static final String BASE_PACKAGE_PATH = "com.nekolr";

    /**
     * 用户权限管理系统框架配置文件
     */
    public static final String UPMS_CONFIG_FILE_PATH = "classpath:/upms/framework.properties";

    /**
     * 全局默认 split 分隔符
     */
    public static final String SPLIT_SEPARATOR = ",";

    // ----------------------------------------- MyBatis Generator -----------------------------------------

    /**
     * DTO 服务接口模板路径
     */
    public static final String DTO_SERVICE_TEMPLATE_PATH = "/templates/dtoService.java.vm";

    /**
     * DTO 服务实现模板路径
     */
    public static final String DTO_SERVICE_IMPL_TEMPLATE_PATH = "/templates/dtoServiceImpl.java.vm";

    /**
     * DTO 服务接口名称
     */
    public static final String DTO_SERVICE_NAME = "DtoService.java";

    /**
     * DTO 服务实现名称
     */
    public static final String DTO_SERVICE_IMPL_NAME= "DtoServiceImpl.java";
}
