package com.etc.mainboot.filter;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;

/**
 * XSSRequestWrapper是Request的包装类,用于修改Request请求，这是拦截器Interceptor所不能做到的：
 * @author \
 * @date 2019/6/21 0021
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

    public XSSRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 对数组参数进行特殊字符过滤
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = clearXss(values[i]);
        }
        return encodedValues;
    }

    /**
     * 对参数中特殊字符进行过滤
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null) {
            return null;
        }
        return clearXss(value);
    }

    /**
     * 获取attribute,特殊字符过滤
     */
    @Override
    public Object getAttribute(String name) {
        Object value = super.getAttribute(name);
        if (value != null && value instanceof String) {
            clearXss((String) value);
        }
        return value;
    }

    /**
     * 对请求头部进行特殊字符过滤
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return clearXss(value);
    }

    /**
     * @Title: clearXss
     * @Description: TODO(xss攻击处理)
     * @param: @param value
     * @param: @return
     * @return: String
     * @throws
     */
    private String clearXss(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        try {
            value = new String(value.getBytes("ISO8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return XssFilterUtil.stripXss(value);
    }
}
