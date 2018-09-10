package com.nekolr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 资源文件工具类
 * <p>
 * 暂时不处理 JBoss VFS
 *
 * @author nekolr
 */
public class ResourceUtils {

    /**
     * classpath 下的资源文件前缀
     */
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 普通文件前缀
     */
    public static final String URL_PROTOCOL_FILE = "file";

    /**
     * jar 文件前缀
     */
    public static final String URL_PROTOCOL_JAR = "jar";

    /**
     * war 文件前缀
     */
    public static final String URL_PROTOCOL_WAR = "war";

    /**
     * zip 文件前缀
     */
    public static final String URL_PROTOCOL_ZIP = "zip";

    private ResourceUtils() {

    }

    /**
     * 判断是否为 URL
     * <p>
     * 以 classpath: 为前缀的也作为 URL
     * <p>
     * URL 应该包含协议、主机、端口号和文件
     *
     * @param resourceLocation 资源路径
     * @return
     */
    public static boolean isURL(String resourceLocation) {
        if (resourceLocation == null) {
            return false;
        }
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            return true;
        }
        try {
            new URL(resourceLocation);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * 判断是否为文件 URL
     *
     * @param url URL
     * @return
     */
    public static boolean isFileURL(URL url) {
        return URL_PROTOCOL_FILE.equals(url.getProtocol());
    }

    /**
     * 判断是否为 jar URL
     *
     * @param url URL
     * @return
     */
    public static boolean isJarURL(URL url) {
        String protocol = url.getProtocol();
        return URL_PROTOCOL_JAR.equals(protocol) || URL_PROTOCOL_WAR.equals(protocol) || URL_PROTOCOL_ZIP.equals(protocol);
    }


    /**
     * 获取 URL
     *
     * @param resourceLocation 资源路径
     * @return
     * @throws FileNotFoundException
     */
    public static URL getURL(String resourceLocation) throws FileNotFoundException {
        if (resourceLocation == null) {
            throw new IllegalArgumentException("Resource location must not be null");
        }
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
            URL url = classLoader != null ? classLoader.getResource(path) : ClassLoader.getSystemResource(path);
            if (url == null) {
                throw new FileNotFoundException("Class path resource [" + path + "] " +
                        "cannot be resolved to URL because it does not exist");
            }
            return url;
        }
        try {
            return new URL(resourceLocation);
        } catch (MalformedURLException e1) {
            try {
                // 不是 URL，尝试直接作为文件加载
                return new File(resourceLocation).toURI().toURL();
            } catch (MalformedURLException e2) {
                throw new FileNotFoundException("Resource location [" + resourceLocation + "] " +
                        "is neither a URL not a well-formed file path");
            }
        }
    }

    /**
     * 根据给定的资源路径获取文件
     *
     * @param resourceLocation
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(String resourceLocation) throws FileNotFoundException {
        if (resourceLocation == null) {
            throw new IllegalArgumentException("Resource location must not be null");
        }
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            String path = resourceLocation.substring(CLASSPATH_URL_PREFIX.length());
            ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
            URL url = classLoader != null ? classLoader.getResource(path) : ClassLoader.getSystemResource(path);
            if (url == null) {
                throw new FileNotFoundException("Class path resource [" + path + "] " +
                        "cannot be resolved to URL because it does not exist");
            }
            return getFile(url);
        }
        try {
            return getFile(new URL(resourceLocation));
        } catch (MalformedURLException e) {
            // 尝试直接加载文件
            return new File(resourceLocation);
        }
    }

    /**
     * 根据给定的 URL 获取文件
     *
     * @param url
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(URL url) throws FileNotFoundException {
        if (url == null) {
            throw new IllegalArgumentException("Resource URL must not be null");
        }
        if (!URL_PROTOCOL_FILE.equals(url.getProtocol())) {
            throw new FileNotFoundException("URL cannot be resolved to absolute file path");
        }
        try {
            return new File(toURI(url).getSchemeSpecificPart());
        } catch (URISyntaxException e) {
            // 一般不会发生
            return new File(url.getFile());
        }
    }

    /**
     * 根据给定的 URI 获取文件
     *
     * @param uri
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(URI uri) throws FileNotFoundException {
        if (uri == null) {
            throw new IllegalArgumentException("Resource URI must not be null");
        }
        if (!URL_PROTOCOL_FILE.equals(uri.getScheme())) {
            throw new FileNotFoundException("URI cannot be resolved to absolute file path");
        }
        return new File(uri.getSchemeSpecificPart());
    }

    /**
     * 根据给定的资源路径获取输入流
     * <p>
     * 某些场景中，比如在 SpringBoot 中，项目打包成 jar，这时资源文件的 URL 都是在 jar
     * 文件中的，因此需要在获取到资源文件的 URL 后需要获取流来处理
     *
     * @param resourceLocation 资源路径，如果是 classpath 环境变量下的资源文件要加 classpath: 前缀
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream getInputStream(String resourceLocation) throws FileNotFoundException {
        URL url = getURL(resourceLocation);
        if (isJarURL(url)) {
            return ClassUtils.getDefaultClassLoader().getResourceAsStream(resourceLocation);
        } else {
            File file = getFile(resourceLocation);
            return new FileInputStream(file);
        }
    }

    /**
     * 将 URL 转成 URI，只处理空格
     *
     * @param url
     * @return
     * @throws URISyntaxException
     */
    public static URI toURI(URL url) throws URISyntaxException {
        return new URI(url.toString().replaceAll(" ", "%20"));
    }
}
