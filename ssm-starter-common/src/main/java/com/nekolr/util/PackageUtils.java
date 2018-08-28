package com.nekolr.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 包搜索类
 *
 * @author nekolr
 */
public class PackageUtils {

    /**
     * 包名搜索
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static List<String> scannerPackageName(String... path) throws Exception {
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(patternResolver);
        List<String> packageName;
        Set<String> packageSet = new HashSet<>();
        for (String p : path) {
            // 统配路径组装
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(p) + "/" + "**/*.class";
            Resource[] resources = patternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                String pkName = metadataReader.getClassMetadata().getClassName();
                pkName = pkName.substring(0, pkName.lastIndexOf("."));
                // 只获取一遍包名
                if (!packageSet.contains(pkName)) {
                    packageSet.add(pkName);
                }
            }
        }
        packageName = new ArrayList<>(packageSet);
        return packageName;
    }

    /**
     * 将 classpath: 路径替换为资源路径
     *
     * @param p
     * @return
     */
    private static String resolveBasePackage(String p) {
        return ClassUtils.convertClassNameToResourcePath(p);
    }

}
