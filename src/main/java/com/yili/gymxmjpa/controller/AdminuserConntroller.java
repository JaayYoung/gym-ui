package com.yili.gymxmjpa.controller;



import com.yili.gymxmjpa.dao.AdminuserDao;
import com.yili.gymxmjpa.entity.Adminuser;
import com.yili.gymxmjpa.real.AdminUserToken;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 管理员登录Controller控制层
 * @Author: yili
 * @Date: 2021/4/4
 */
@Controller
@RequestMapping("/")
public class AdminuserConntroller {
    @Autowired
    private AdminuserDao adminuserDao;

    /**
     * 输入端口号，直接进入主页
     * @return
     */
    @RequestMapping("/")
    public String test(){
        return "th/index";
    }
    /**
     * @Description: 跳转管理员登录界面
     * @Author: yili
     * @Date: 2021/4/29
     */
    @RequestMapping("/admin")
    public String beforeLogin(){
        return "admin";
    }

    /**
     * @Description: 管理员登录验证方法
     * @Author: yili
     * @Date: 2021/4/4
     */
    @RequestMapping("/dl/yz")
    public String login(String username, String password,HttpSession httpSession,Model model){

        Subject subject= SecurityUtils.getSubject();
        AdminUserToken userToken=new AdminUserToken(username,DigestUtils.md5Hex(password));
        try{
            subject.login(userToken);
            Adminuser a= adminuserDao.findByAdminNameAndAdminPassword(username,DigestUtils.md5Hex(password));
            httpSession.setAttribute("user",a);
            return "WEB-INF/jsp/index";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名或密码错误,请重新输入");
            return "login";
        }

        /*Adminuser a= adminuserDao.findByAdminNameAndAdminmima(username,password);
        if(a!=null){
            httpSession.setAttribute("user",a);
            return "WEB-INF/jsp/index" ;
        }
        model.addAttribute("mag","账号或密码错误");
        return "login";*/
    }

    /**
     * @Description: 退出登录后清楚session
     * @Author: yili
     * @Date: 2021/5/1
     */
    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";

    }

    /**
     * @Description: 跳转到修改密码界面
     * @Author: yili
     * @Date: 2021/5/1
     */
    @RequestMapping("/updPassword")
    public String updPassword(){
        return "WEB-INF/jsp/updPassword";
    }


    /**
     * @Description: 修改密码
     * @Author: yili
     * @Date: 2021/5/1
     */
    @RequestMapping("/upd/updPassword")
    public String updPasswordConfirm(String oldPassword,String newPassword,String newPasswordAgain,HttpSession httpSession,Model model){
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!.%*#?&])[A-Za-z\\d$@$!.%*#?&]{8,}$");
        Matcher m = p.matcher(newPassword);
        if(!m.matches()){
            model.addAttribute("msg","新密码最少为8位并为字母+数字+特殊字符");
            return "WEB-INF/jsp/updPassword";
        }
        if(!newPassword.equals(newPasswordAgain)){
            model.addAttribute("msg","两次输入新密码不一致,请重新输入");
            return "WEB-INF/jsp/updPassword";
        }
        Adminuser adminuser=(Adminuser) httpSession.getAttribute("user");
        if(null != adminuser){
            if(!adminuser.getAdminPassword().equals(DigestUtils.md5Hex(oldPassword))){
                model.addAttribute("msg","原密码不正确,请重新输入");
                return "WEB-INF/jsp/updPassword";
            }
            adminuserDao.updPassword(adminuser.getAdminId(), DigestUtils.md5Hex(newPassword));
        }
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
       return "redirect:/admin.jsp";
    }


}
