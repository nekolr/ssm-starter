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

    private static final Pattern SCRIPT_PATTERN = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static final Pattern CLOSE_SCRIPT_PATTERN = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    private static final Pattern BEGIN_SCRIPT_PATTERN = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern EVAL_SCRIPT_PATTERN = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern EXPRESSION_SCRIPT_PATTERN = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern JAVASCRIPT_SCRIPT_PATTERN = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    private static final Pattern VB_SCRIPT_SCRIPT_PATTERN = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
    private static final Pattern ON_LOAD_SCRIPT_PATTERN = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);


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
            // null
            encoded = content.replaceAll("", "");
            // script
            encoded = SCRIPT_PATTERN.matcher(encoded).replaceAll("");
            // </script>
            encoded = CLOSE_SCRIPT_PATTERN.matcher(encoded).replaceAll("");
            // <script ...>
            encoded = BEGIN_SCRIPT_PATTERN.matcher(encoded).replaceAll("");
            // eval()
            encoded = EVAL_SCRIPT_PATTERN.matcher(encoded).replaceAll("");
            // expression()
            encoded = EXPRESSION_SCRIPT_PATTERN.matcher(encoded).replaceAll("");
            // javascript:
            encoded = JAVASCRIPT_SCRIPT_PATTERN.matcher(encoded).replaceAll("");
            // vbscript:
            encoded = VB_SCRIPT_SCRIPT_PATTERN.matcher(encoded).replaceAll("");
            // onload=
            encoded = ON_LOAD_SCRIPT_PATTERN.matcher(encoded).replaceAll("");
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
