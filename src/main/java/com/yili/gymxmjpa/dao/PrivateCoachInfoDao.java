package com.yili.gymxmjpa.dao;


import com.yili.gymxmjpa.entity.PrivateCoachInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description: 私教信息Dao层接口
 * @Author: yili
 * @Date: 2021/4/3
 */
public interface PrivateCoachInfoDao extends JpaRepository<PrivateCoachInfo,Long> {
/*
    @Query(value = "select p from PrivateCoachInfo p where p.memberid=:memberid")
    public PrivateCoachInfo queryById(@Param("memberid") long memberid) ;*/


    @Query(value = "select * FROM  privatecoachinfo where memberid =:memberid",nativeQuery = true)
    public List<PrivateCoachInfo> queryByIdNative(@Param("memberid") long memberid);

    @Query(value = "select * FROM  privatecoachinfo where coachid =:coachid",nativeQuery = true)
    public List<PrivateCoachInfo> queryByCoachIdNative(@Param("coachid") long coachid);

    @Query(value = "select * FROM  privatecoachinfo where subjectid =:subjectid",nativeQuery = true)
    public List<PrivateCoachInfo> queryBySubjectIdNative(@Param("subjectid") long subjectid);

}
