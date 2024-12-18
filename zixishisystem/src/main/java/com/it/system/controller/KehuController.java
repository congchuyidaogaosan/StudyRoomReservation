package com.it.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Kehu;
import com.it.system.domain.Result;
import com.it.system.service.KehuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/Kehu")
public class KehuController {


    @Autowired
    private KehuService kehuService;


    @GetMapping("list")
    public Result list(Kehu kehu) {

        QueryWrapper<Kehu> queryWrapper = new QueryWrapper<>();

        if (kehu.getUsername()!=null && !kehu.getUsername().equals("")){
            queryWrapper.like("username",kehu.getUsername());
        }

        if (kehu.getMbti()!=null && !kehu.getMbti().equals("")){
            queryWrapper.eq("MBTI",kehu.getMbti());
        }

        List<Kehu> list = kehuService.list(queryWrapper);
        return Result.ok(list);
    }


    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpServletRequest request) {

        Kehu byId = kehuService.getById(id);
        return Result.ok(byId);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Kehu kehu) {

        boolean b = kehuService.updateById(kehu);
        return Result.ok();
    }

    @RequestMapping("/save")
    public Result save(@RequestBody Kehu kehu) {

        boolean save = kehuService.save(kehu);
        return Result.ok();
    }

    @RequestMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id) {

        boolean b = kehuService.removeById(id);
        return Result.ok();
    }

}
