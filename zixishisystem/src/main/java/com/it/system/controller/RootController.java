package com.it.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Result;
import com.it.system.domain.ResultCodeEnum;
import com.it.system.domain.Root;
import com.it.system.service.RootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admin")
@RestController
public class RootController {

    @Autowired
    private RootService rootService;

    @PostMapping("/login")
    public Result login(@RequestBody Root root){

        QueryWrapper<Root> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("username",root.getUsername()).eq("password",root.getPassword());

        List<Root> list = rootService.list(queryWrapper);
        if (list.size()==1){
            return Result.ok(list.get(0));
        }

        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }
}
