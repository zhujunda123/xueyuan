package com.online.edu.eduservice.controller;

import com.online.edu.common.R;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 获取系统环境信息
 */

@Controller
@RequestMapping(value = "/eduservice")
@CrossOrigin
public class SystemInfoController {

    @RequestMapping("/system")
    @ResponseBody

    public R index(HttpServletRequest request) {
        String s = request.getHeader("x-token");
        System.out.println(s);
        Properties props = System.getProperties();
        //java版本
        String javaVersion = props.getProperty("java.version");
        //操作系统名称
//        String osName = props.getProperty("os.name") + props.getProperty("os.version");
        String osName = props.getProperty("os.name");
        //用户的主目录
        String userHome = props.getProperty("user.home");
        //用户的当前工作目录
        String userDir = props.getProperty("user.dir");
        //服务器IP
        String serverIP = request.getLocalAddr();
        //客户端IP
        String clientIP = request.getRemoteHost();
        //WEB服务器
        String webVersion = request.getServletContext().getServerInfo();
        //CPU个数
        String cpu = Runtime.getRuntime().availableProcessors() + "核";
        //虚拟机内存总量
        String totalMemory = (Runtime.getRuntime().totalMemory() / 1024 / 1024) + "M";
        //虚拟机空闲内存量
        String freeMemory = (Runtime.getRuntime().freeMemory() / 1024 / 1024) + "M";
        //虚拟机使用的最大内存量
        String maxMemory = (Runtime.getRuntime().maxMemory() / 1024 / 1024) + "M";

        //网站根目录
        String webRootPath = request.getSession().getServletContext().getRealPath("");
        Map<String, String> propsMap = new HashMap<String, String>();
        propsMap.put("java版本", javaVersion);
        propsMap.put("操作系统", osName);

        propsMap.put("客户端IP", clientIP);
        propsMap.put("服务器IP", serverIP);
        propsMap.put("CPU核心数", cpu);
        propsMap.put("java虚拟机内存总量", totalMemory);
        propsMap.put("java虚拟机空闲内存量", freeMemory);
        propsMap.put("java虚拟机使用的最大内存量", maxMemory);
        propsMap.put("WEB服务器", webVersion);


        return R.ok().data("propsMap", propsMap);
    }

}
