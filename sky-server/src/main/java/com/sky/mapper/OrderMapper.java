package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.xmlbeans.impl.soap.Detail;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    void insert(Orders orders);
    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);


    @Select("select * from  orders where user_id = #{id}")
    Page<OrderVO> selectByUserId(Long id);


    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> selectByOrderId(Long id);

    @Select("select * from orders where id = #{id}")
    OrderVO selectById(Long id);

    @Delete("delete from order_detail where order_id = #{id}")
    void deleteById(Long id);

    @Delete("delete from orders where id = #{id}")
    void deleteByUserId(Long id);

    void insertList(List<OrderDetail> list);


    @Select("select count(0) from orders where status = #{deliveryInProgress}")
    Integer countStatistics(Integer deliveryInProgress);


    void updateConfirm(Orders orders);

    @Select("select * from orders where status = #{pendingPayment} and order_time < #{time} ")
    List<Orders> getByStatusAndOrderTimeLT(Integer pendingPayment, LocalDateTime time);
}
