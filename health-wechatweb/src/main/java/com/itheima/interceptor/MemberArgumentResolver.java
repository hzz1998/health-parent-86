package com.itheima.interceptor;

import com.alibaba.fastjson.JSON;
import com.itheima.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    JedisUtil jedisUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //看你的方法是否有Member注解
        return parameter.hasParameterAnnotation(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //从redis获取用户信息
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = request.getHeader("token");
        String s = jedisUtil.get(token);
        //获取对象直接返回，会复制给你加注解的参数
        return JSON.parseObject(s, com.itheima.pojo.Member.class);
    }
}
