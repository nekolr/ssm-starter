package com.nekolr.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * XSS 过滤工具
 *
 * @author nekolr
 */
public class XssUtils {

    public static final List<Pattern> FILTER_PATTERNS = Collections.unmodifiableList(Arrays.asList(
            // Avoid common html tags
            Pattern.compile("(<input(.*?)></input>|<input(.*)/>)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<span(.*?)</span>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<div(.*)</div>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<style>(.*?)</style>", Pattern.CASE_INSENSITIVE),
            //Avoid onload= expressions
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid anything between script tags
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            // Avoid javascript:... expressions
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            // Remove any lonesome </script> tag
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid anything in a src='...' type of expression
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid eval(...) expressions
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid vbscript:... expressions
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE)
    ));

    private XssUtils() {

    }

    /**
     * 过滤 Xss 脚本
     *
     * @param value
     * @return
     */
    public static String filterXss(String value) {
        if (value != null) {
            for (Pattern pattern : FILTER_PATTERNS) {
                value = pattern.matcher(value).replaceAll("");
            }
        }
        return value;
    }
}
