package com.yili.gymxmjpa.controller;



import antlr.CommonToken;
import com.yili.gymxmjpa.dao.MenberDao;
import com.yili.gymxmjpa.dao.chongzhiDao;
import com.yili.gymxmjpa.entity.Adminuser;
import com.yili.gymxmjpa.real.AdminUserToken;
import com.yili.gymxmjpa.real.CommonUserToken;
import com.yili.gymxmjpa.service.MenberDaoImpl;
import com.yili.gymxmjpa.entity.Chongzhi;
import com.yili.gymxmjpa.entity.Member;
import com.yili.gymxmjpa.entity.Membertype;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 会员管理Controller控制层
 * @Author: yili
 * @Date: 2021/4/6
 */
@Controller
@RequestMapping("/menber")
public class MenberConntroller {

    @Autowired
    private  chongzhiDao chongzhiDao;

    @Autowired
    private MenberDao menberDao;
    @Autowired
    private MenberDaoImpl menberDaoiImpl;

    /**
     * 跳转注册
     * @return
     */
    @RequestMapping(value = "/register")
    public String register(){
        return "th/register";
    }

    /**
     * 新用户注册逻辑处理
     * @param member
     * @return
     */
    @RequestMapping(value = "/reg")
    public String register(Member member){
        member.setMemberStatic(0);
        java.sql.Date s = new java.sql.Date(new Date().getTime());
        member.setNenberDate(s);
        member.setMemberbalance(0);
//        Object obj = new SimpleHash("MD5", member.getMemberPassword(), null, 1);
        String hashpassword = DigestUtils.md5Hex(member.getMemberPassword());
        member.setMemberPassword(hashpassword);
        menberDao.save(member);
        return "login";
    }




    /**
     * 会员跳转首页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    public String selectlesson(Model model){
        Member member= (Member) SecurityUtils.getSubject().getPrincipal(); // 获取当前登录用户
        if(null != member){
            model.addAttribute("member",member);
        }
        return "th/afterlogin";
    }

    /**
     * 会员登录
     * @param username
     * @param password
     * @param model
     * @return
     */
    @RequestMapping(value = "/memberlogin")
    public String login(String username, String password,  Model model) {
        Subject subject = SecurityUtils.getSubject();
        CommonUserToken userToken = new CommonUserToken(username, DigestUtils.md5Hex(password));
        try {
            subject.login(userToken);
            Member a = menberDao.findByMemberAccountAndMemberPassword(username, DigestUtils.md5Hex(password));
            if(null != a){
                model.addAttribute("member",a);
            }
            return "th/afterlogin";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名或密码错误,请重新输入");
            return "login";
        }
    }
    /**
     * @Description: 会员管理-进入会员到期界面
     * @Author: yili
     * @Date: 2021/4/6
     */
    @RequestMapping("/jin2")
    public String jin2(){
        return "WEB-INF/jsp/HYMemberDaoQi";
    }

    /**
     * @Description: 会员管理-进入会员续费充值界面
     * @Author: yili
     * @Date: 2021/4/6
     */
    @RequestMapping("/jin3")
    public String jin3(){
        return "WEB-INF/jsp/HYMemberChongZhi";
    }

    /**
     * @Description: 会员管理-进入会员余额充值界面
     * @Author: yili
     * @Date: 2021/4/6
     */
    @RequestMapping("/jin11")
    public String jin11(){
        return "WEB-INF/jsp/HYMemberyeChongZhi";
    }

    /**
     * @Description: 会员管理-进入会员列表界面
     * @Author: yili
     * @Date: 2021/4/6
     */
    @RequestMapping("/jin")
    public String jin(){

        return "WEB-INF/jsp/HYMember";
    }

    /**
     * @Description: 教练管理-进入会员私教详情界面
     * @Author: yili
     * @Date: 2021/4/8
     */
    @RequestMapping("/jin4")
    public String jin4(){

        return "WEB-INF/jsp/privatecoachinfo";
    }

    /**
     * @Description: 会员列表-分页查询
     * @Author: yili
     * @Date: 2021/4/6
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String,Object> query(int ktype,String hyname, int pageSize, int pageNumber){
        Map<String,Object>  map1=new HashMap<String,Object>();
        map1.put("hyname",hyname);
        map1.put("ktype",ktype);
        map1.put("qi",(pageNumber-1)*pageSize);
        map1.put("shi",pageSize);
        return menberDaoiImpl.query(map1);
    }

    /**
     * @Description: 会员到期-分页查询
     * @Author: yili
     * @Date: 2021/4/6
     */
    @RequestMapping("/query2")
    @ResponseBody
    public Map<String,Object> query2(int ktype,String hyname, int pageSize, int pageNumber){
        Map<String,Object>  map1=new HashMap<String,Object>();
        map1.put("hyname",hyname);
        map1.put("ktype",ktype);
        map1.put("qi",(pageNumber-1)*pageSize);
        map1.put("shi",pageSize);
        return menberDaoiImpl.query2(map1);
    }

    /**
     * @Description: 会员续卡-根据会员id查询会员信息
     * @Author: yili
     * @Date: 2021/4/6
     */
    @RequestMapping("/cha")
    @ResponseBody
    public Member query2(int id){

        return menberDaoiImpl.cha(id);
    }

    /**
     * @Description: 会员管理-根据会员id删除
     * @Author: LiuJian
     * @Date: 2021/4/6
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String,Object> del(int memid){

        menberDaoiImpl.del(memid);
        return query(0,"",5,1);
    }

    /**
     * @Description: 会员管理-添加新会员
     * @Author: LiuJian
     * @Date: 2021/4/6
     */
    @RequestMapping("/insert")
    @ResponseBody
    public Map<String,Object> insert(Member member){
        menberDaoiImpl.insert(member);
        return query(0,"",5,1);
    }

    /**
     * @Description: 会员管理-修改会员信息
     * @Author: LiuJian
     * @Date: 2021/4/6
     */
    @RequestMapping("/update")
    @ResponseBody
    public Map<String,Object> update(Member member){
        menberDaoiImpl.update(member);
        return query(0,"",5,1);
    }


    /**
     * @Description: 会员卡续费-添加会员卡续费记录
     * @Author: LiuJian
     * @Date: 2021/4/6
     */
    @RequestMapping("/ins")
    @ResponseBody
    public Map<String, Object> insert(Chongzhi chongzhi, String daoqi){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        java.sql.Timestamp dat=java.sql.Timestamp.valueOf(df.format(new Date()));

        chongzhi.setDate(dat);
        chongzhi.setCzStatic(2L);
        chongzhiDao.save(chongzhi);
        Membertype membertype=new Membertype();
        membertype.setTypeId(chongzhi.getMembertype().getTypeId());

        Member member=menberDao.findById(chongzhi.getMember().getMemberId()).get();
        member.setMemberId(chongzhi.getMember().getMemberId());
        member.setMemberStatic(1L);
        member.setMembertypes(membertype);

        java.sql.Date date=java.sql.Date.valueOf(daoqi);

        member.setMemberxufei(date);
        menberDao.save(member);

        return query(0,null,5,1);
    }



}
