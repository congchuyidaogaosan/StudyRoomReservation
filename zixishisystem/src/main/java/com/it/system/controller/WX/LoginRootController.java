package com.it.system.controller.WX;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Result;
import com.it.system.domain.Root;
import com.it.system.service.RootService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("auto")
@RestController
public class LoginRootController {


    @Autowired
    private RootService rootService;


    @GetMapping("login")
    private Result login(@RequestBody Root root) {


        QueryWrapper<Root> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", root.getUsername()).eq("password", root.getPassword());

        List<Root> list = rootService.list(queryWrapper);
        if (list != null && list.size() >= 1) {
            Root root1 = list.get(0);
            return Result.ok(root1);
        }
        return Result.fail();
    }


}
