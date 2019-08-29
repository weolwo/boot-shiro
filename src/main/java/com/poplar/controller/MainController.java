package com.poplar.controller;

import com.poplar.po.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class MainController {

    @RequestMapping("/main")
    public String index(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("root", request.getContextPath());

        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("root", request.getContextPath());

        return "login";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, User user){
        response.setHeader("root", request.getContextPath());
        String userName = user.getName();
        String passWord =user.getPassWord();

        if(!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(passWord)){
            // 1.获取Subject
            Subject subject = SecurityUtils.getSubject();
            // 2.封装用户数据
            UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
            // 3.执行登录方法
            try{
                subject.login(token);
                return "redirect:/main";
            } catch (UnknownAccountException e){
                System.out.println("用户名不存在！");
                request.setAttribute("msg","用户名不存在！");
            } catch (IncorrectCredentialsException e){
                System.out.println("密码错误！");
                request.setAttribute("msg","用户名或密码错误！");
            }
        }

        return "login";
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "redirect:/main";
    }

    @RequestMapping("/error/unAuth")
    public String unAuth(){
        return "/error/unAuth";
    }
}
