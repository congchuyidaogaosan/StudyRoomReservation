package com.it.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.*;
import com.it.system.service.NewbillService;
import com.it.system.service.SeatsService;
import com.it.system.service.StudyroomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/Seats")
public class SeatsController {


    @Autowired
    private SeatsService seatsService;


    @Autowired
    private StudyroomsService studyroomsService;

    @Autowired
    private NewbillService newbillService;

    @RequestMapping("RellayList")
    public Result RellayList(@RequestParam("timeId") Integer timeId, @RequestParam("roomId") Integer roomId) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = formatter.format(calendar.getTime()).split(" ")[0];

        List<Newbill> newBillList = newbillService.list(new QueryWrapper<Newbill>()
                .eq("time_id", timeId).eq("room_id", roomId).eq("time", s));

        List<Seats> seatsList = seatsService.list(new QueryWrapper<Seats>().eq("room_id", roomId));

        HashMap<Integer, Seats> stringSeatsHashMap = new HashMap<>();

        for (Seats seats : seatsList) {
            stringSeatsHashMap.put(seats.getSeatId(), seats);
        }

        for (Newbill newbill : newBillList) {
            if (stringSeatsHashMap.containsKey(newbill.getRoomId())) {
                Seats seats = stringSeatsHashMap.get(newbill.getSeatsId());
                seats.setIsAvailable("occupied");
            }
        }

        List<Seats> list = new ArrayList<>();

        Set<Integer> integers = stringSeatsHashMap.keySet();
        for (Integer integer : integers) {
            Seats seats = stringSeatsHashMap.get(integer);
            list.add(seats);
        }

        return Result.ok(list);
    }


    @RequestMapping("initSeats")
    public Result initSeats(@RequestParam("RootId") Integer RootId) {

        if (seatsService.isInfo(RootId)) {
            return Result.build(null, ResultCodeEnum.SEATS_INIT);
        }

        Studyrooms byId = studyroomsService.getById(RootId);

        LinkedList<Seats> linkedList = new LinkedList<>();


        for (int a = 1, b = 1, c = 1; a <= Integer.parseInt(byId.getTotalSeats()); a++, c++) {
            if (a % 9 == 0) {
                b++;
                c = 1;
            }
            Seats seats = new Seats();
            seats.setRoomId(RootId);
            seats.setIsAvailable("available");

            seats.setCcolumn(c + "");

            seats.setRrow(b + "");

            seats.setNumber(a + "");
            linkedList.add(seats);
        }


        seatsService.saveBatch(linkedList);

        return Result.ok(linkedList);

    }

    @RequestMapping("list")
    public Result list(Seats seats) {
        QueryWrapper<Seats> queryWrapper = new QueryWrapper<>();

        if (seats.getRoomId() != null && seats.getRoomId() != 0) {
            queryWrapper.eq("room_id", seats.getRoomId());
        }


        List<Seats> list = seatsService.list(queryWrapper);
        return Result.ok(list);
    }


    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpServletRequest request) {

        Seats byId = seatsService.getById(id);
        return Result.ok(byId);
    }

    @RequestMapping("/update")
    public Result update(Seats seats) {

        boolean update = seatsService.updateById(seats);
        return Result.ok();
    }

    @RequestMapping("/save")
    public Result save(Seats seats) {

        boolean save = seatsService.save(seats);
        return Result.ok();
    }

    @RequestMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id) {

        boolean b = seatsService.removeById(id);
        return Result.ok();
    }


}
