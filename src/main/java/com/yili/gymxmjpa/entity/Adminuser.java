package com.yili.gymxmjpa.entity;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

/**
 * @Description: 管理员信息实体类
 * @Author: yili
 * @Date: 2021/4/3
 */
@Entity
@Table(name = "adminuser")
@Getter
@Setter
public class Adminuser {
  @Id
  @GeneratedValue(strategy =  GenerationType.AUTO)
  private long adminId;
//  管理员账号
  private String adminName;
//  管理员密码
  private String adminPassword;
}
