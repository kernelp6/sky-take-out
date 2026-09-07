package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    void insertSetmealDish(List<SetmealDish> setmealDishes);

    void delectById(List<Long> ids);

    @Select("select * from sky_take_out.setmeal_dish where setmeal_dish.setmeal_id = #{id}")
    List<SetmealDish> seleteBySetmealId(Long id);
}
