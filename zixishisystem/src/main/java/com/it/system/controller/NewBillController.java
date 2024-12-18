package com.it.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.*;
import com.it.system.service.KehuService;
import com.it.system.service.LogbillService;
import com.it.system.service.NewbillService;
import com.it.system.service.TimetypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/NewBill")
public class NewBillController {

    @Autowired
    private NewbillService newbillService;

    @Autowired
    private TimetypeService timetypeService;

    @Autowired
    private LogbillService logbillService;

    @RequestMapping("list")
    public Result list(Newbill newbill) {

        List<Newbill> list = newbillService.list();
        return Result.ok(list);
    }



    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpServletRequest request) {

        Newbill byId = newbillService.getById(id);
        return Result.ok(byId);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Newbill newbill) {

        boolean b = newbillService.updateById(newbill);
        return Result.ok();
    }

    @RequestMapping("/save")
    public Result save(@RequestBody Newbill newbill) {

        if (reallyBill(newbill.getTimeId(), newbill.getRoomId(), newbill.getKehuId(), newbill.getSeatsId(),newbill.getTime())) {
            return Result.build(null, ResultCodeEnum.BILL_REALLY);
        }

        Timetype byId = timetypeService.getById(newbill.getTimeId());

        newbill.setStar(byId.getStart());
        newbill.setEnd(byId.getEnd());
        newbill.setStates("occupied");

        boolean save = newbillService.save(newbill);
        logbillService.newSave(newbill.getKehuId(),newbill.getRoomId(),newbill.getTimeId(),newbill.getSeatsId());

        return Result.ok();
    }

    @RequestMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id) {

        boolean b = newbillService.removeById(id);
        return Result.ok();
    }


    private boolean reallyBill(Integer timeId, Integer roomId, Integer kehuId, Integer seatsId,String time) {
        QueryWrapper<Newbill> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .eq("time_id", timeId)
                .eq("room_id", roomId)
                .eq("kehu_id", kehuId)
                .eq("seats_id", seatsId)
                .eq("init_time",time);

        Newbill one = newbillService.getOne(queryWrapper);
        if (one == null) {
            return false;
        }
        return true;
    }

}
