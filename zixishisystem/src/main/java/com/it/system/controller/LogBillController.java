package com.it.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Logbill;
import com.it.system.domain.Loglogin;
import com.it.system.domain.Result;
import com.it.system.service.LogbillService;
import com.it.system.service.LogloginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/LogBill")
public class LogBillController {


    @Autowired
    private LogbillService logbillService;


    @RequestMapping("list")
    public Result list(@RequestBody Logbill loglogin) {
        QueryWrapper<Logbill> logbillQueryWrapper = new QueryWrapper<>();
        if (loglogin.getKehuid()!=null && loglogin.getKehuid()!=0){
            logbillQueryWrapper.eq("kehuid",loglogin.getKehuid());
        }
        if (loglogin.getStatus()!=null && !loglogin.getStatus().equals("")){
            logbillQueryWrapper.eq("status",loglogin.getStatus());
        }

        logbillQueryWrapper.orderByDesc("infotime");

        List<Logbill> list2 = logbillService.list(logbillQueryWrapper);
        return Result.ok(list2);
    }

    @RequestMapping("RootList")
    public Result RootList(Logbill loglogin) {
        QueryWrapper<Logbill> logbillQueryWrapper = new QueryWrapper<>();
        if (loglogin.getKehuid()!=null && loglogin.getKehuid()!=0){
            logbillQueryWrapper.eq("kehuid",loglogin.getKehuid());
        }
        if (loglogin.getStatus()!=null && !loglogin.getStatus().equals("")){
            logbillQueryWrapper.eq("status",loglogin.getStatus());
        }

        logbillQueryWrapper.orderByDesc("infotime");

        List<Logbill> list2 = logbillService.list(logbillQueryWrapper);
        return Result.ok(list2);
    }


    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpServletRequest request) {

        Logbill byId = logbillService.getById(id);
        return Result.ok(byId);
    }

    @RequestMapping("/update")
    public Result update(Logbill logbill) {

        boolean update = logbillService.updateById(logbill);
        return Result.ok();
    }

    @RequestMapping("/save")
    public Result save(Logbill logbill) {

        boolean save = logbillService.save(logbill);
        return Result.ok();
    }

    @RequestMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id) {

        boolean b = logbillService.removeById(id);
        return Result.ok();
    }
}
