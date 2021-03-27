package com.yili.gymxmjpa.dao;


import com.yili.gymxmjpa.entity.Membertype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Description: 会员卡类型信息Dao层接口
 * @Author: yili
 * @Date: 2021/4/3
 */
public interface MemberttypeDao extends JpaRepository<Membertype,Long> {
    @Query(value = "SELECT * FROM membertype ORDER BY Typemoney limit 4 ",nativeQuery = true)
    List<Membertype> querybynative();
    @Query(value = "SELECT * FROM membertype Where typeId =:typeId ",nativeQuery = true)
    Membertype queryById(int typeId);
}
