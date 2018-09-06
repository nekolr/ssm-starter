package com.nekolr.util;

import org.apache.commons.lang.text.StrTokenizer;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * IP 工具类
 *
 * @author nekolr
 */
public class IpUtils {

    private static final String N255 = "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    /**
     * 匹配 IPv4 地址
     */
    private static final Pattern IPV4_ADDRESS = Pattern.compile("^(?:" + N255 + "\\.){3}" + N255 + "$");
    /**
     * 代理头，RFC2616 中 Header 不区分大小写
     */
    private static final List<String> proxyHeaders = Arrays.asList(
            "X-FORWARDED-FOR",
            "PROXY-CLIENT-IP",
            "WL-PROXY-CLIENT-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
            "X-REAL-IP"
    );

    private IpUtils() {
    }


    /**
     * 获取客户端的 IP 地址
     *
     * @param request
     * @return IPv4 地址
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = null;
        for (String proxyHeader : proxyHeaders) {
            ip = request.getHeader(proxyHeader);
            if (StringUtils.isEmpty(ip) || "UNKNOWN".equalsIgnoreCase(ip)) {
                continue;
            }
        }
        if (StringUtils.isEmpty(ip) || "UNKNOWN".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 这里没有直接取第一个，而是选择其中的某个公网 IP 返回
        StrTokenizer tokenizer = new StrTokenizer(ip, ",");
        while (tokenizer.hasNext()) {
            ip = tokenizer.nextToken().trim();
            if (isIPv4Valid(ip) && !isIPv4Private(ip)) {
                return ip;
            }
        }
        // 取第一个
        return ip;
    }

    /**
     * 将 IPv4 地址转换成 long 型数字
     *
     * @param ip
     * @return
     */
    private static long ipv4ToLong(String ip) {
        String[] slots = ip.split("\\.");
        return (Long.parseLong(slots[0]) << 24) + (Integer.parseInt(slots[1]) << 16)
                + (Integer.parseInt(slots[2]) << 8) + Integer.parseInt(slots[3]);
    }

    /**
     * 是否是私有 IPv4 型地址
     *
     * @param ip
     * @return
     */
    private static boolean isIPv4Private(String ip) {
        long longIp = ipv4ToLong(ip);
        return (longIp >= ipv4ToLong("10.0.0.0") && longIp <= ipv4ToLong("10.255.255.255"))
                || (longIp >= ipv4ToLong("172.16.0.0") && longIp <= ipv4ToLong("172.31.255.255"))
                || longIp >= ipv4ToLong("192.168.0.0") && longIp <= ipv4ToLong("192.168.255.255");
    }

    /**
     * 是否是 IPv4 型的地址
     *
     * @param ip
     * @return
     */
    private static boolean isIPv4Valid(String ip) {
        return IPV4_ADDRESS.matcher(ip).matches();
    }
}
