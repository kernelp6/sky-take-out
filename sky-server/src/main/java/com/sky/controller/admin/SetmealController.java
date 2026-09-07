package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "套餐相关接口")
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    @ApiOperation("新增套餐")
    @PostMapping
    public Result insertSetmeal(@RequestBody SetmealDTO setmealDTO){

        setmealService.insertSetmeal(setmealDTO);

        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("分页展示")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult page =setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(page);
    }

    @PostMapping("/status/{status}")
    @ApiOperation("套餐状态")
    public Result update(@PathVariable Long status,Long id ){
        setmealService.updateStatus(status,id);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("删除套餐")
    public Result delect(@RequestParam List<Long> ids){
        setmealService.delect(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查套餐")
    public Result<SetmealVO> selectById(@PathVariable Long id){
        SetmealVO setmealVO = setmealService.selectById(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    public Result put(@RequestBody SetmealDTO setmealDTO){

        setmealService.put(setmealDTO);
        return Result.success();
    }


}
