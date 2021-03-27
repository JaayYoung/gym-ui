package com.yili.gymxmjpa.controller;

import com.yili.gymxmjpa.dao.*;
import com.yili.gymxmjpa.entity.*;
import com.yili.gymxmjpa.service.SubjectDaoImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Description: 课程管理Controller控制层
 * @Author: yili
 * @Date: 2021/4/8
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {
   @Autowired
   private SubjectDaoImpl subjectDaoImpl;
   @Autowired
   private SubjectDao subjectDao;
   @Autowired
   private PrivateCoachInfoDao privateCoachInfoDao;
   @Autowired
   private CoachDao coachDao;
    @Autowired
    private MenberDao menberDao;

    /**
     * 前台会员选课 ——课逻辑处理
     * @param coachId
     * @param subId
     * @param model
     * @return
     */
   @RequestMapping(value = "/select")
   public String select(int coachId,int  subId,Model model){
       try {
           Member member= (Member) SecurityUtils.getSubject().getPrincipal(); // 获取当前登录用户
           if(null != member){
               model.addAttribute("member",member);
           }
           Subject subject = subjectDao.queryById(subId);
           if (null != subject){
               model.addAttribute("subject",subject);
           }
           if (member.getMemberbalance()<subject.getSellingPrice()){
               return "th/failresult";
           }
           member.setMemberbalance((float) (member.getMemberbalance()-subject.getSellingPrice()));
           menberDao.save(member);
           Coach coach = coachDao.querybyid(coachId);
           PrivateCoachInfo p = new PrivateCoachInfo();
           p.setCoach(coach); p.setMember(member); p.setSubject(subject);
           p.setCount(1); p.setCountprice(subject.getSellingPrice());p.setState(1);
           java.sql.Date s = new java.sql.Date(new Date().getTime());
           p.setDate(s);
           privateCoachInfoDao.save(p);
           return "th/result";
       } catch (Exception e) {
          return "erroy";
       }


   }


    /**
     * @Description: 前台课程展示-进入课程展示界面
     * @Author: yili
     * @Date: 2021/4/8
     */
    @RequestMapping("/show")
    public String show(Model model){
        Member member= (Member) SecurityUtils.getSubject().getPrincipal(); // 获取当前登录用户
        if(null != member){
            model.addAttribute("member",member);
        }
        List<Coach> coachs = coachDao.querybyCoachStatic(0);
        model.addAttribute("coach1",coachs.get(0));
        model.addAttribute("coach2",coachs.get(1));
        model.addAttribute("coach3",coachs.get(2));
        model.addAttribute("coach4",coachs.get(3));
        return "th/product";
    }
    /**
     * @Description: 前台关于展示-进入关于我们界面
     * @Author: yili
     * @Date: 2021/4/8
     */
    @RequestMapping("/about")
    public String about(Model model){

        Member member= (Member) SecurityUtils.getSubject().getPrincipal(); // 获取当前登录用户
        if(null != member){
            model.addAttribute("member",member);
        }
        return "th/about";
    }

    /**
     * @Description: 课程管理-进入课程信息界面
     * @Author: yili
     * @Date: 2021/4/8
     */
    @RequestMapping("/jin7")
    public String jin7(){

        return "WEB-INF/jsp/subject";
    }

    /**
     * @Description: 课程管理-根据课程名称分页查询
     * @Author: yili
     * @Date: 2021/4/8
     */
    @RequestMapping("/query")
    @ResponseBody
    public Map<String,Object> query(String subname, int pageSize, int pageNumber){
        Map<String,Object>  map1=new HashMap<String,Object>();
        map1.put("subname",subname);
        map1.put("qi",(pageNumber-1)*pageSize);
        map1.put("shi",pageSize);
        return subjectDaoImpl.query(map1);
    }

    /**
     * @Description: 课程管理-根据课程id删除课程
     * @Author: yili
     * @Date: 2021/4/8
     */
    @RequestMapping("/del")
    @ResponseBody
    public  Map<String,Object> del(long subId,String subname, int pageSize, int pageNumber){

        //先根据教练id在私教信息表里查询是否有其信息
        List<PrivateCoachInfo> privateCoachInfoList = privateCoachInfoDao.queryBySubjectIdNative(subId);
        if(privateCoachInfoList !=null && privateCoachInfoList.size() > 0){
            //如果有,先循环删除
            for(PrivateCoachInfo privateCoachInfo : privateCoachInfoList){
                if(subId == privateCoachInfo.getSubject().getSubId()){
                    privateCoachInfoDao.delete(privateCoachInfo);
                }
            }
        }
        subjectDao.deleteById(subId);
        Map<String,Object>  map1=new HashMap<String,Object>();
        map1.put("subname",subname);
        map1.put("qi",(pageNumber-1)*pageSize);
        map1.put("shi",pageSize);
        return subjectDaoImpl.query(map1);
    }

    /**
     * @Description: 课程管理-添加课程
     * @Author: yili
     * @Date: 2021/4/8
     */
    @RequestMapping("/add")
    @ResponseBody
    public  void save(Subject subject){
        subjectDao.save(subject);
    }

    /**
     * @Description: 课程管理-根据课程id查询课程信息
     * @Author: yili
     * @Date: 2021/4/8
     */
    @RequestMapping("/cha")
    @ResponseBody
    public Optional<Subject> one(long subId){
        return subjectDao.findById(subId);
    }

    /**
     * @Description: 课程管理-修改课程信息
     * @Author: yili
     * @Date: 2021/4/8
     */
    @RequestMapping("/upd")
    @ResponseBody
    public  void upd(Subject subject){
        subjectDao.save(subject);
    }

    /**
     * @Description: 课程管理-根据课程名称计算总课程数据
     * @Author: yili
     * @Date: 2021/4/8
     */
    @RequestMapping("/count")
    @ResponseBody
    public Long count (String subname){
        subjectDaoImpl.count(subname);
        return  subjectDaoImpl.count(subname);
    }

}
