package com.it.system.controller;


import com.it.system.domain.Loglogin;
import com.it.system.domain.Result;
import com.it.system.domain.Seats;
import com.it.system.service.LogloginService;
import com.it.system.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/LogLogin")
public class LogLoginController {


    @Autowired
    private LogloginService logloginService;


    @PostMapping("list")
    public Result list(@RequestBody Loglogin loglogin) {

        List<Loglogin> list = logloginService.list();
        return Result.ok(list);
    }


    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpServletRequest request) {

        Loglogin byId = logloginService.getById(id);
        return Result.ok(byId);
    }

    @RequestMapping("/update")
    public Result update(Loglogin loglogin) {

        boolean update = logloginService.updateById(loglogin);
        return Result.ok();
    }

    @RequestMapping("/save")
    public Result save(Loglogin loglogin) {

        boolean save = logloginService.save(loglogin);
        return Result.ok();
    }

    @RequestMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id) {

        boolean b = logloginService.removeById(id);
        return Result.ok();
    }
}
