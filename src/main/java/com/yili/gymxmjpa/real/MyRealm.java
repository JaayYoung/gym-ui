package com.yili.gymxmjpa.real;

import com.yili.gymxmjpa.dao.AdminuserDao;
import com.yili.gymxmjpa.dao.MenberDao;
import com.yili.gymxmjpa.entity.Adminuser;
import com.yili.gymxmjpa.entity.Member;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private AdminuserDao adminUserMapper;
    @Autowired
    private MenberDao commonUserMapper;
//重写授权模块
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("已授权");
        Subject subject = SecurityUtils.getSubject();
        Adminuser user = (Adminuser) principalCollection.getPrimaryPrincipal() ;
        //
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo() ;
        return null;
    }
    //（2）重写认证方法doGetAuthenticationInfo
    //用户登录时会调用这个方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

//        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken ;
//        Adminuser adminuser = adminuserDao.findByAdminNameAndAdminPassword(token.getUsername(),String.copyValueOf(token.getPassword()));
//
//        if(null == adminuser){
//            return null ;
//        }
//        //SimpleAuthenticationInfo ：代表该用户的认证信息。参数为“用户名+密码+盐”
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(adminuser.getAdminName(),adminuser.getAdminPassword(),"") ;
//        System.out.println("完成认证！");
//        return info;
        if(authenticationToken instanceof CommonUserToken) //普通用户的认证逻辑
        {
            UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken ;
        Member member = commonUserMapper.findByMemberAccountAndMemberPassword(token.getUsername(),String.copyValueOf(token.getPassword()));

        if(null == member){
            return null ;
        }
        //SimpleAuthenticationInfo ：代表该用户的认证信息。参数为“用户名+密码+盐”
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(member,member.getMemberPassword(),"") ;
        System.out.println("完成认证！");
        return info;
        }
        else if(authenticationToken instanceof AdminUserToken) //管理员的谁逻辑
        {
            UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken ;
        Adminuser adminuser = adminUserMapper.findByAdminNameAndAdminPassword(token.getUsername(),String.copyValueOf(token.getPassword()));

        if(null == adminuser){
            return null ;
        }
        //SimpleAuthenticationInfo ：代表该用户的认证信息。参数为“用户名+密码+盐”
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(adminuser.getAdminName(),adminuser.getAdminPassword(),"") ;
        System.out.println("完成认证！");
        return info;
        }
        else
        {
            return null;
        }
    }
}
