package com.yili.gymxmjpa.dao;


import com.yili.gymxmjpa.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description: 教练信息Dao层接口
 * @Author: yili
 * @Date: 2021/4/3
 */
public interface CoachDao extends JpaRepository<Coach,Long> {

    @Query(value = "select * FROM  coach where CoachStatic =:CoachStatic limit 4",nativeQuery = true)
    List<Coach> querybyCoachStatic(@Param("CoachStatic") int CoachStatic);
    @Query(value = "select * FROM  coach where coachId =:coachId",nativeQuery = true)
    Coach querybyid(int coachId);
}
