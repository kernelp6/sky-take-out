package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    void insertSetmeal(SetmealDTO setmealDTO);

    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);


    void updateStatus(Long status, Long id);

    void delect(List<Long> ids);

    SetmealVO selectById(Long id);

    void put(SetmealDTO setmealDTO);
}
