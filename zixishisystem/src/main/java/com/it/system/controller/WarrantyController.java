package com.it.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Result;
import com.it.system.domain.Timetype;
import com.it.system.domain.Warranty;
import com.it.system.service.WarrantyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/Warranty")
public class WarrantyController {


    @Autowired
    private WarrantyService warrantyService;


    @RequestMapping("addRecord")
    public Result addRecord(@RequestBody Warranty warranty) {

        warranty.setState("完成");

        warrantyService.updateById(warranty);
        return Result.ok();
    }


    @RequestMapping("list")
    public Result list(Warranty warranty) {
        QueryWrapper<Warranty> queryWrapper = new QueryWrapper<>();
        if (warranty.getState() != null && !warranty.getState().equals("")) {

            if (warranty.getState().equals("all")) {

            } else if (warranty.getState().equals("pending")) {
                queryWrapper.eq("state", "待处理");
            } else if (warranty.getState().equals("processing")) {
                queryWrapper.eq("state", "处理中");
            } else if (warranty.getState().equals("completed")) {
                queryWrapper.eq("state", "已完成");
            }

        }
        if (warranty.getRoomlocation() != null && !warranty.getRoomlocation().equals("")) {
            queryWrapper.eq("roomlocation", warranty.getRoomlocation());
        }


        List<Warranty> list = warrantyService.list(queryWrapper);
        return Result.ok(list);
    }


    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpServletRequest request) {

        Warranty byId = warrantyService.getById(id);
        return Result.ok(byId);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Warranty warranty) {

        boolean b = warrantyService.updateById(warranty);
        return Result.ok();
    }

    @RequestMapping("/save")
    public Result save(@RequestBody Warranty warranty) {

        boolean save = warrantyService.save(warranty);
        return Result.ok();
    }

    @RequestMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id) {

        boolean b = warrantyService.removeById(id);
        return Result.ok();
    }


}
