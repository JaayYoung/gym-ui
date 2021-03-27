package com.yili.gymxmjpa.dao;


import com.yili.gymxmjpa.entity.Loos;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: 丢失物品信息Dao层接口
 * @Author: yili
 * @Date: 2021/4/3
 */
public interface LoosDao extends JpaRepository<Loos,Long> {
}
