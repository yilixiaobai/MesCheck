package com.chinamobile.checkmes.framework;

import com.chinamobile.checkmes.dao.CommonDao;
import com.chinamobile.checkmes.po.LoginPo;
import com.chinamobile.checkmes.until.IpUntil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;


@Component
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession obj = request.getSession();
        Object user = obj.getAttribute("user");
        if (null == obj || null == user||"0".equals(user)) {
            response.sendRedirect(request.getContextPath() + "/index");
            return false;
        }
        if(null!=obj.getAttribute("ip")&&null!=user&&!"0".equals(user)){
            if(obj.getAttribute("ip").toString().equals(IpUntil.getIpAddress(request))){
                return  true;
            }
            else {
                response.sendRedirect(request.getContextPath() + "/index");
                return false;
            }
        }
        else {
            response.sendRedirect(request.getContextPath() + "/index");
            return false;
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
