package com.yili.gymxmjpa.controller;

import com.yili.gymxmjpa.dao.MemberttypeDao;
import com.yili.gymxmjpa.dao.MenberDao;
import com.yili.gymxmjpa.dao.chongzhiDao;
import com.yili.gymxmjpa.entity.Chongzhi;
import com.yili.gymxmjpa.entity.Coach;
import com.yili.gymxmjpa.entity.Member;
import com.yili.gymxmjpa.service.MembertypeDaoImpl;
import com.yili.gymxmjpa.entity.Membertype;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 会员卡类型信息Controller控制层
 * @Author: yili
 * @Date: 2021/4/4
 */
@Controller
@RequestMapping("/metype")
public class MetypeController {

    @Autowired
    private MembertypeDaoImpl membertypeDaoImpl;
    @Autowired
    private MemberttypeDao memberttypeDao;
    @Autowired
    private MenberDao menberDao;
    @Autowired
    private chongzhiDao chongzhiDao;
    /**
     * 前台进入办续卡页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/result")
    public String  xukaresult(int typeId, Model model){
        Member member= (Member) SecurityUtils.getSubject().getPrincipal(); // 获取当前登录用户
        Membertype membertype = memberttypeDao.queryById(typeId);
        member.setMemberbalance(member.getMemberbalance()+membertype.getTypemoney());
//        获取到期时间
        Calendar c = Calendar.getInstance();
        Date now = new Date();
        if(null != member.getMemberxufei()){
            now = member.getMemberxufei();
        }
        c.setTime(now); // 使用到期时间
        c.add(Calendar.DATE, (int) membertype.getTypeDay()); // 添加会员卡的天数
        Date date =(java.util.Date) c.getTime();
        java.sql.Date s = new java.sql.Date(date.getTime());
        member.setMemberxufei(s);
        member.setMemberStatic(1L);
        member.setMembertypes(membertype);
        menberDao.save(member);
//        添加充值记录
        Chongzhi chongzhi = new Chongzhi();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.sql.Timestamp dat=java.sql.Timestamp.valueOf(df.format(new Date()));
        chongzhi.setDate(dat);
        chongzhi.setCzStatic(2L);
        chongzhi.setMoney((long) membertype.getTypemoney());
        chongzhi.setMember(member);
        chongzhi.setMembertype(membertype);
        chongzhiDao.save(chongzhi);
        if(null != member){
            model.addAttribute("member",member);
        }
        return "th/cardresult";
    }

    /**
     * 前台进入办续卡页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/xuka")
    public String  xuka(Model model){
        Member member= (Member) SecurityUtils.getSubject().getPrincipal(); // 获取当前登录用户
        if(null != member){
            model.addAttribute("member",member);
        }
        List<Membertype> mty = memberttypeDao.querybynative();
        model.addAttribute("mty1",mty.get(0));
        model.addAttribute("mty2",mty.get(1));
        model.addAttribute("mty3",mty.get(2));
        model.addAttribute("mty4",mty.get(3));
        return "th/card";
    }


    /**
     * @Description: 会员卡类型-删除
     * @Author: yili
     * @Date: 2021/4/4
     */
    @RequestMapping("/del")
    @ResponseBody
    public  Map<String,Object> del(long typeId,String typeName, int pageSize, int pageNumber){
     memberttypeDao.deleteById(typeId);
     Map<String,Object>  map1=new HashMap<String,Object>();
     map1.put("typeName",typeName);
     map1.put("qi",(pageNumber-1)*pageSize);
     map1.put("shi",pageSize);
     return membertypeDaoImpl.query(map1);
    }

    /**
     * @Description: 会员卡类型-添加会员卡类型
     * @Author: yili
     * @Date: 2021/4/4
     */
    @RequestMapping("/add")
    @ResponseBody
    public  void save(Membertype membertype){

        memberttypeDao.save(membertype);
    }

    /**
     * @Description: 会员卡类型-根据id查询
     * @Author: yili
     * @Date: 2021/4/4
     */
    @RequestMapping("/cha")
    @ResponseBody
    public Optional<Membertype> one(long typeId){
        return memberttypeDao.findById(typeId);
    }

    /**
     * @Description: 会员卡类型-修改会员卡类型信息
     * @Author: yili
     * @Date: 2021/4/4
     */
    @RequestMapping("/upd")
    @ResponseBody
    public void upd(Membertype membertype){ memberttypeDao.save(membertype);
    }

}
