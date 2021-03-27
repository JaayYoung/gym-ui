package com.yili.gymxmjpa.dao;


import com.yili.gymxmjpa.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: 设备信息Dao层接口
 * @Author: yili
 * @Date: 2021/4/3
 */
public interface EquipmentDao extends JpaRepository<Equipment,Long> {
}
