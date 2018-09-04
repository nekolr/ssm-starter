package com.nekolr.util;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;

import java.util.regex.Pattern;

/**
 * XSS 过滤工具，底层使用 ESAPI
 *
 * @author nekolr
 */
public class XssUtils {

    /**
     * 简单处理文本内容
     *
     * @param content 文本内容
     * @return
     */
    private static String canonicalize(String content) {
        return ESAPI.encoder().canonicalize(content);
    }

    /**
     * 过滤 Xss Script
     *
     * @param content
     * @return
     */
    public static String filterXssScript(String content) {
        String encoded = canonicalize(content);
        if (encoded != null) {
            // Avoid null characters
            encoded = content.replaceAll("", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            encoded = scriptPattern.matcher(encoded).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            encoded = scriptPattern.matcher(encoded).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE
                    | Pattern.MULTILINE | Pattern.DOTALL);
            encoded = scriptPattern.matcher(encoded).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE
                    | Pattern.MULTILINE | Pattern.DOTALL);
            encoded = scriptPattern.matcher(encoded).replaceAll("");

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE
                    | Pattern.MULTILINE | Pattern.DOTALL);
            encoded = scriptPattern.matcher(encoded).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            encoded = scriptPattern.matcher(encoded).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            encoded = scriptPattern.matcher(encoded).replaceAll("");

            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE
                    | Pattern.MULTILINE | Pattern.DOTALL);
            encoded = scriptPattern.matcher(encoded).replaceAll("");
        }
        return encoded;
    }

    /**
     * 过滤 Sql 注入文本
     *
     * @param content
     * @return
     */
    public static String filterXssSql(String content) {
        return (null == content) ? null : content.replaceAll("('.+--)|(--)|(%7C)", "");
    }

    /**
     * 编码 Html 文本
     *
     * @param content
     * @return
     */
    public static String encodeHtml(String content) {
        return ESAPI.encoder().encodeForHTML(content);
    }

    /**
     * 解码 Html 文本
     *
     * @param encoded 被编码过的文本
     * @return
     */
    public static String decodeHtml(String encoded) {
        return ESAPI.encoder().decodeForHTML(encoded);
    }

    /**
     * 编码 Javascript 文本
     *
     * @param content
     * @return
     */
    public static String encodeJavaScript(String content) {
        return ESAPI.encoder().encodeForJavaScript(content);
    }

    /**
     * 编码 MySQL Sql 文本
     *
     * @param content
     * @return
     */
    public static String encodeSqlOfMySql(String content) {
        return ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), content);
    }
}
