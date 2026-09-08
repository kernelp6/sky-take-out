package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealPageVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    public void insertSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.insert(setmeal);

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        setmealDishMapper.insertSetmealDish(setmealDishes);

    }

    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<Setmeal> setmeals = setmealMapper.page(setmealPageQueryDTO);
        Long total = setmeals.getTotal();
        List<Setmeal> list = setmeals.getResult();

        List<SetmealPageVO> list1 = list.stream().map(setmeal -> {
            SetmealPageVO setmealPageVO = new SetmealPageVO();
            BeanUtils.copyProperties(setmeal, setmealPageVO);
            Long categoryId = setmeal.getCategoryId();
            String name = categoryMapper.getCategoryNameById(categoryId);
            setmealPageVO.setCategoryName(name);
            return setmealPageVO;
        }).collect(Collectors.toList());


        PageResult pageResult = new PageResult();
        pageResult.setTotal(total);
        pageResult.setRecords(list1);
        return pageResult;


    }

    @Override
    public void updateStatus(Long status, Long id) {
        setmealMapper.update(status,id);
    }

    @Transactional
    @Override
    public void delect(List<Long> ids) {

        setmealDishMapper.delectById(ids);

        setmealMapper.delect(ids);
    }

    @Override
    @Transactional
    public SetmealVO selectById(Long id) {
        Setmeal setmeal = setmealMapper.selectById(id);
        String name = categoryMapper.getCategoryNameById(setmeal.getCategoryId());
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setCategoryName(name);
        List<SetmealDish> list = setmealDishMapper.seleteBySetmealId(setmeal.getId());
        setmealVO.setSetmealDishes(list);

        return setmealVO;
    }
    @Transactional
    @Override
    public void put(SetmealDTO setmealDTO) {
        List<Long> list = new ArrayList<>();
        Long id = setmealDTO.getId();
        list.add(id);
        delect(list);
        insertSetmeal(setmealDTO);
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }


    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

}
