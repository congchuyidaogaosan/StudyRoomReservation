package com.it.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.*;
import com.it.system.service.*;
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

    @Autowired
    private SeatsService seatsService;

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
        try {
            // 检查该用户在同一时间段是否已有预约
            QueryWrapper<Newbill> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("kehu_id", newbill.getKehuId())
                       .eq("time_id", newbill.getTimeId())
                       .eq("time", newbill.getTime());
            
            List<Newbill> existingBills = newbillService.list(queryWrapper);

            if (existingBills.size() > 0) {
                return Result.fail("您在该时间段已有预约，请选择其他时间段");
            }
            
            // 检查座位是否已被预约
            QueryWrapper<Newbill> seatWrapper = new QueryWrapper<>();
            seatWrapper.eq("seats_id", newbill.getSeatsId())
                      .eq("time_id", newbill.getTimeId())
                      .eq("time", newbill.getTime());
                      
            Newbill existingSeat = newbillService.getOne(seatWrapper);
            
            if (existingSeat != null) {
                return Result.fail("该座位已被预约，请选择其他座位");
            }

            if (reallyBill(newbill.getTimeId(), newbill.getRoomId(), newbill.getKehuId(), newbill.getSeatsId(),newbill.getTime())) {
                return Result.build(null, ResultCodeEnum.BILL_REALLY);
            }

            Timetype byId = timetypeService.getById(newbill.getTimeId());

            newbill.setStar(byId.getStart());
            newbill.setEnd(byId.getEnd());
            newbill.setStates("occupied");


            Seats seats = seatsService.getById(newbill.getSeatsId());


            boolean save = newbillService.save(newbill);
            logbillService.newSave(newbill.getKehuId(),newbill.getRoomId(),newbill.getTimeId(),newbill.getSeatsId(),seats.getPrice());
            if (save) {
                // 记录日志
                logbillService.newSave(newbill.getKehuId(), newbill.getRoomId(), 
                                     newbill.getTimeId(), newbill.getSeatsId(),0);
                return Result.ok();
            } else {
                return Result.fail("预约失败，请重试");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("系统错误，请稍后重试");
        }
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
