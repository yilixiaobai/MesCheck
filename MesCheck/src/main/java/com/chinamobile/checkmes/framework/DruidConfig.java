package com.chinamobile.checkmes.framework;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.chinamobile.checkmes.until.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DruidConfig {

    private Logger logger = LoggerFactory.getLogger(DruidConfig.class);


    private String username="sys";
    private String dbUrl="jdbc:mysql://127.0.0.1:3306/sys?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private String password=RSAUtil.deCode("KxbYEdfN0H1AMJrc78VdpkLnYt+1rgj/zTsKIdRATlfnYXLRkmoHJFUjc9WROQ4Tw+4euV7pA1ZiGYGwA7XBOA==");
    //http接口的密码
    public static String http_key=RSAUtil.deCode("ANWKLUfXX/Gcmtk5zJgXMmv+aojfHr7L5AL0qLOBUs091F348wm1HNZQS35KosTujW56YPt7Alvd9nSDFy8o6w==");

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("loginUsername", username);
        reg.addInitParameter("loginPassword", password);
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName("com.mysql.jdbc.Driver");

        return datasource;
    }

}