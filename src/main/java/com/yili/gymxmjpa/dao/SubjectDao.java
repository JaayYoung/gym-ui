package com.yili.gymxmjpa.dao;


import com.yili.gymxmjpa.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Description: 课程信息Dao层接口
 * @Author: yili
 * @Date: 2021/4/3
 */
public interface SubjectDao extends JpaRepository<Subject,Long> {
    @Query(value = "SELECT * FROM subject where subId =:subId",nativeQuery = true)
    Subject queryById(int subId);
}
