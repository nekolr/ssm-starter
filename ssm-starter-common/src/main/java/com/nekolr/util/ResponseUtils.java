package com.nekolr.util;

import com.alibaba.fastjson.JSON;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ServletResponse 工具类
 *
 * @author nekolr
 */
public class ResponseUtils {

    /**
     * 响应 JSON 文本
     *
     * @param response
     * @param data
     */
    public static void responseJson(ServletResponse response, Object data) {
        // 设置响应头
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(JSON.toJSONString(data));
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
