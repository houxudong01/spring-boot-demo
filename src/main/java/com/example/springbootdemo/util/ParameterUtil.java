package com.example.springbootdemo.util;

import com.example.springbootdemo.config.MessageCodeConsts;
import com.example.springbootdemo.exception.UserNoticeException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author:hxd
 * @date:2020/5/31
 */
public class ParameterUtil {
    public static void required(Object parameter, String parameterName) {
        if (null == parameter) {
            throw new UserNoticeException(MessageCodeConsts.PARAMETER_MISSING, parameterName);
        }
        if (parameter instanceof String && StringUtils.isBlank((String) parameter)) {
            throw new UserNoticeException(MessageCodeConsts.PARAMETER_MISSING, parameterName);
        }
        if (parameter instanceof Collection<?> && ((Collection) parameter).isEmpty()) {
            throw new UserNoticeException(MessageCodeConsts.PARAMETER_MISSING, parameterName);
        }
        if (parameter instanceof Map<?, ?> && ((Map) parameter).isEmpty()) {
            throw new UserNoticeException(MessageCodeConsts.PARAMETER_MISSING, parameterName);
        }
    }
}
