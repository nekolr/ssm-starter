package com.nekolr.util;

import com.alibaba.fastjson.JSON;
import com.nekolr.common.ResultBean;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ServletResponse 工具类
 *
 * @author nekolr
 */
public class ResponseUtils {

    private ResponseUtils() {

    }

    /**
     * 响应 JSON 文本
     *
     * @param response
     * @param resultBean
     */
    public static void responseJson(ServletResponse response, ResultBean resultBean, Integer code) {
        // 设置响应头
        response.setContentType("application/json;charset=utf-8");
        // 设置状态码
        ((HttpServletResponse) response).setStatus(code);
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(JSON.toJSONString(resultBean));
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
