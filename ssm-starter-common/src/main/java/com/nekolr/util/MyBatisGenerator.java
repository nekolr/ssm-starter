package com.nekolr.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.nekolr.common.Constants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * MyBatis Plus 代码生成器
 *
 * @author nekolr
 */
public class MyBatisGenerator {

    public static void main(String[] args) {
        MyBatisGenerator generator = new MyBatisGenerator();
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generate() throws IOException {
        /**
         * 加载 Generator 配置
         */
        InputStream inputStream = ClassUtils.getDefaultClassLoader().getResourceAsStream("generator.properties");
        Properties props = new Properties();
        props.load(inputStream);

        String outputDir = props.getProperty("generator.outputDir");
        Boolean fileOverride = Boolean.valueOf(props.getProperty("generator.fileOverride"));
        Boolean activeRecord = Boolean.valueOf(props.getProperty("generator.activeRecord"));
        Boolean enableCache = Boolean.valueOf(props.getProperty("generator.enableCache"));
        Boolean baseResultMap = Boolean.valueOf(props.getProperty("generator.baseResultMap"));
        Boolean baseColumnList = Boolean.valueOf(props.getProperty("generator.baseColumnList"));
        String author = props.getProperty("generator.author");
        String serviceName = props.getProperty("generator.serviceName");
        String serviceImplName = props.getProperty("generator.serviceImplName");
        String entityName = props.getProperty("generator.entityName");
        String idType = props.getProperty("generator.idType");
        Boolean swagger2 = Boolean.valueOf(props.getProperty("generator.swagger2"));

        DbType dbTpe = DbType.valueOf(props.getProperty("generator.dbType"));
        String driverName = props.getProperty("generator.driverName");
        String username = props.getProperty("generator.username");
        String password = EncryptUtils.aesDecrypt(props.getProperty("generator.password"));
        String url = props.getProperty("generator.url");

        Boolean capitalMode = Boolean.valueOf(props.getProperty("generator.capitalMode"));
        String[] tablePrefix = StringUtils.isEmpty(props.getProperty("generator.tablePrefix")) ? null : props.getProperty("generator.tablePrefix").split(Constants.SPLIT_SEPARATOR);
        String naming = props.getProperty("generator.naming");
        String columnNaming = props.getProperty("generator.columnNaming");
        String[] include = StringUtils.isEmpty(props.getProperty("generator.include")) ? null : props.getProperty("generator.include").split(Constants.SPLIT_SEPARATOR);
        String[] exclude = StringUtils.isEmpty(props.getProperty("generator.exclude")) ? null : props.getProperty("generator.exclude").split(Constants.SPLIT_SEPARATOR);
        String superEntityClass = props.getProperty("generator.superEntityClass");
        String[] superEntityColumns = StringUtils.isEmpty(props.getProperty("generator.superEntityColumns")) ? null : props.getProperty("generator.superEntityColumns").split(Constants.SPLIT_SEPARATOR);
        String superMapperClass = props.getProperty("generator.superMapperClass");
        String superServiceClass = props.getProperty("generator.superServiceClass");
        String superServiceImplClass = props.getProperty("generator.superServiceImplClass");
        String superControllerClass = props.getProperty("generator.superControllerClass");
        Boolean entityColumnConstant = Boolean.valueOf(props.getProperty("generator.entityColumnConstant"));
        Boolean entityBuilderModel = Boolean.valueOf(props.getProperty("generator.entityBuilderModel"));
        Boolean entityLombokModel = Boolean.valueOf(props.getProperty("generator.entityLombokModel"));
        Boolean entityTableFieldAnnotationEnable = Boolean.valueOf(props.getProperty("generator.entityTableFieldAnnotationEnable"));
        Boolean restControllerStyle = Boolean.valueOf(props.getProperty("generator.restControllerStyle"));
        String logicDeleteFieldName = props.getProperty("generator.logicDeleteFieldName");

        String parent = props.getProperty("generator.parent");
        String moduleName = props.getProperty("generator.moduleName");
        String controller = props.getProperty("generator.controller");
        String service = props.getProperty("generator.service");
        String serviceImpl = props.getProperty("generator.serviceImpl");
        String mapper = props.getProperty("generator.mapper");
        String xml = props.getProperty("generator.xml");
        String entity = props.getProperty("generator.entity");

        Boolean addDtoServiceAndImpl = Boolean.valueOf(props.getProperty("generator.addDtoServiceAndImpl"));
        String dtoPackageName = props.getProperty("generator.dtoPackageName");

        /**
         * 全局配置
         */
        GlobalConfig globalConfig = new GlobalConfig();
        // 输出目录
        globalConfig.setOutputDir(outputDir);
        // 文件覆盖
        globalConfig.setFileOverride(fileOverride);
        // 不需要 ActiveRecord 特性的可以改为 false
        globalConfig.setActiveRecord(activeRecord);
        // 全局的 service 接口名
        globalConfig.setServiceName(serviceName);
        // 全局的 service impl 名称
        globalConfig.setServiceImplName(serviceImplName);
        // 全局的 entity 名称
        globalConfig.setEntityName(entityName);
        // 主键策略
        globalConfig.setIdType(IdType.valueOf(idType));
        // XML 二级缓存
        globalConfig.setEnableCache(enableCache);
        // XML ResultMap
        globalConfig.setBaseResultMap(baseResultMap);
        // XML columnList
        globalConfig.setBaseColumnList(baseColumnList);
        // 作者
        globalConfig.setAuthor(author);
        // 启用 Swagger2
        globalConfig.setSwagger2(swagger2);

        /**
         * 数据源配置
         */
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        // 数据库类型
        dataSourceConfig.setDbType(dbTpe);
        // 自定义数据库表字段类型转换，使用默认的实现
        dataSourceConfig.setTypeConvert((config, fieldType) -> new MySqlTypeConvert().processTypeConvert(config, fieldType));
        // 驱动全限定名
        dataSourceConfig.setDriverName(driverName);
        // 用户名
        dataSourceConfig.setUsername(username);
        // 密码
        dataSourceConfig.setPassword(password);
        // URL
        dataSourceConfig.setUrl(url);

        /**
         * 策略配置
         */
        StrategyConfig strategyConfig = new StrategyConfig();
        // 全局大写命名
        strategyConfig.setCapitalMode(capitalMode);
        // 表前缀
        strategyConfig.setTablePrefix(tablePrefix);
        // 表名生成策略
        strategyConfig.setNaming(NamingStrategy.valueOf(naming));
        // 列名生成策略
        strategyConfig.setColumnNaming(NamingStrategy.valueOf(columnNaming));
        // 需要生成的表
        strategyConfig.setInclude(include);
        // 需要排除的表
        strategyConfig.setExclude(exclude);
        // 自定义实体父类
        strategyConfig.setSuperEntityClass(superEntityClass);
        // 自定义实体父类字段
        strategyConfig.setSuperEntityColumns(superEntityColumns);
        // 自定义 mapper 父类
        strategyConfig.setSuperMapperClass(superMapperClass);
        // 自定义 service 父类
        strategyConfig.setSuperServiceClass(superServiceClass);
        // 自定义 service 实现父类
        strategyConfig.setSuperServiceImplClass(superServiceImplClass);
        // 自定义 controller 父类
        strategyConfig.setSuperControllerClass(superControllerClass);
        // 是否生成字段常量
        strategyConfig.setEntityColumnConstant(entityColumnConstant);
        // 是否为构建者模型
        strategyConfig.setEntityBuilderModel(entityBuilderModel);
        // 是否使用 Lombok
        strategyConfig.setEntityLombokModel(entityLombokModel);
        // 自动生成注解
        strategyConfig.entityTableFieldAnnotationEnable(entityTableFieldAnnotationEnable);
        // REST 注解
        strategyConfig.setRestControllerStyle(restControllerStyle);
        // 逻辑删除字段
        strategyConfig.setLogicDeleteFieldName(logicDeleteFieldName);

        /**
         * 包配置
         */
        PackageConfig packageConfig = new PackageConfig();
        // 基本包名
        packageConfig.setParent(parent);
        // 模块名
        packageConfig.setModuleName(moduleName);
        // 自定义 controller 包名
        packageConfig.setController(controller);
        // 自定义的 service 包名
        packageConfig.setService(service);
        // 自定义的 service impl 包名
        packageConfig.setServiceImpl(serviceImpl);
        // 自定义的 mapper 包名
        packageConfig.setMapper(mapper);
        // 自定义的 mapper xml 包名
        packageConfig.setXml(xml);
        // 自定义的 entity 包名
        packageConfig.setEntity(entity);

        /**
         * 自动生成器创建
         */
        AutoGenerator autoGenerator = new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);

        // 是否添加 DTO service 接口和实现
        if (addDtoServiceAndImpl) {
            /**
             * 自定义配置
             */
            InjectionConfig injectionConfig = new InjectionConfig() {
                @Override
                public void initMap() {
                    Map<String, Object> map = new HashMap<>(3);
                    // 这样设置后，可以在 vm 模板中使用 cfg.addDtoServiceAndImpl
                    map.put("addDtoServiceAndImpl", true);
                    map.put("dtoPackageName", dtoPackageName);
                    this.setMap(map);
                }
            };

            /**
             * 新模板添加
             */
            List<FileOutConfig> focList = new ArrayList<>();
            // 添加 DTO service 模板
            focList.add(new FileOutConfig(Constants.DTO_SERVICE_TEMPLATE_PATH) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出的文件位置
                    String packageName = parent + "." + moduleName + "." + service;
                    String packagePath = packageName.replaceAll("\\.", "\\" + File.separator);
                    return outputDir + File.separator + packagePath + File.separator + tableInfo.getEntityName() + Constants.DTO_SERVICE_NAME;
                }
            });
            // 添加 DTO service impl 模板
            focList.add(new FileOutConfig(Constants.DTO_SERVICE_IMPL_TEMPLATE_PATH) {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输出的文件位置
                    String packageName = parent + "." + moduleName + "." + serviceImpl;
                    String packagePath = packageName.replaceAll("\\.", "\\" + File.separator);
                    return outputDir + File.separator + packagePath + File.separator + tableInfo.getEntityName() + Constants.DTO_SERVICE_IMPL_NAME;
                }
            });
            injectionConfig.setFileOutConfigList(focList);

            // 设置自定义注入
            autoGenerator.setCfg(injectionConfig);
        }

        /**
         * 执行生成
         */
        autoGenerator.execute();
    }
}
