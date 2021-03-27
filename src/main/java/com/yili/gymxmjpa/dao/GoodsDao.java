package com.yili.gymxmjpa.dao;


import com.yili.gymxmjpa.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description: 商品信息Dao层接口
 * @Author: yili
 * @Date: 2021/4/3
 */
public interface GoodsDao extends JpaRepository<Goods,Long> {
}
