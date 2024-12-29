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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @RequestMapping("updateSeatPrice")
    public Result updatePrice(@RequestBody Seats seats){

        boolean update = seatsService.updateById(seats);
        return Result.ok();
    }

    @RequestMapping("RellayList")
    public Result RellayList(@RequestParam Integer roomId, 
                            @RequestParam(required = false) Integer timeId,
                            @RequestParam(required = false) String date) {
        try {
            if (date == null || date.isEmpty()) {
                LocalDate today = LocalDate.now();
                date = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            
            List<Newbill> newBillList = newbillService.list(new QueryWrapper<Newbill>()
                    .eq("time_id", timeId)
                    .eq("room_id", roomId)
                    .eq("time", date));

            List<Seats> seatsList = seatsService.list(new QueryWrapper<Seats>()
                    .eq("room_id", roomId));

            HashMap<Integer, Seats> seatsMap = new HashMap<>();
            for (Seats seat : seatsList) {
                seatsMap.put(seat.getSeatId(), seat);
            }

            for (Newbill bill : newBillList) {
                if (seatsMap.containsKey(bill.getSeatsId())) {
                    Seats seat = seatsMap.get(bill.getSeatsId());
                    seat.setIsAvailable("occupied");
                }
            }

            List<Seats> resultList = new ArrayList<>(seatsMap.values());
            
            return Result.ok(resultList);
            
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取座位列表失败");
        }
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
