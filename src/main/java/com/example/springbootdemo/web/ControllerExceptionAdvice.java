package com.example.springbootdemo.web;

import com.example.springbootdemo.config.MessageCodeConsts;
import com.example.springbootdemo.exception.UserNoticeException;
import com.example.springbootdemo.util.ApiResult;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:hxd
 * @date:2020/5/31
 */
@RestControllerAdvice
public class ControllerExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionAdvice.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * 自定义用户通知异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(UserNoticeException.class)
    ApiResult<String> notice(HttpServletRequest request, UserNoticeException e) {
        String message = this.messageSource.getMessage(e.getMessageCode(), e.getArgs(), this.localeResolver.resolveLocale(request));
        return new ApiResult<>(e.getCode(), message);
    }

    /**
     * Spring MVC 参数缺失异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    ApiResult parameterMissing(HttpServletRequest request, MissingServletRequestParameterException e) {
        String message = this.messageSource.getMessage(
                MessageCodeConsts.PARAMETER_MISSING,
                new Object[]{e.getParameterName()},
                this.localeResolver.resolveLocale(request));
        return new ApiResult(HttpStatus.BAD_GATEWAY.value(), message);
    }

    /**
     * Spring MVC 参数值无效异常处理，如字符串无法转换为需要的 Enum，Long 等
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ApiResult parameterInvalid(HttpServletRequest request, MethodArgumentTypeMismatchException e) {
        String message = this.messageSource.getMessage(
                MessageCodeConsts.PARAMETER_INVALID,
                new Object[]{e.getName(), e.getValue()},
                this.localeResolver.resolveLocale(request));
        return new ApiResult(HttpStatus.BAD_GATEWAY.value(), message);
    }

    /**
     * mybatis 操作数据库异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(MyBatisSystemException.class)
    ApiResult dbErrorHandler(HttpServletRequest request, MyBatisSystemException e) {
        String message = this.messageSource.getMessage(
                MessageCodeConsts.DB_ERROR,
                null,
                this.localeResolver.resolveLocale(request));
        return new ApiResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    /**
     * 通用的的默认异常处理，用来兜底
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    ApiResult commonErrorHandler(HttpServletRequest request, Exception e) {
        String message = this.messageSource.getMessage(
                MessageCodeConsts.UNKNOW_ERROR,
                null,
                this.localeResolver.resolveLocale(request));
        return new ApiResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }
}
