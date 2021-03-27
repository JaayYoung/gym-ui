package com.yili.gymxmjpa.dao;


import com.yili.gymxmjpa.entity.Adminuser;
import com.yili.gymxmjpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: 会员信息Dao层接口
 * @Author: yili
 * @Date: 2021/4/3
 */
public interface MenberDao extends JpaRepository<Member,Long> {
    Member findByMemberAccountAndMemberPassword(String username, String copyValueOf);
}
