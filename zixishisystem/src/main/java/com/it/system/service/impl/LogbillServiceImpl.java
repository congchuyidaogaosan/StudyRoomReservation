package com.it.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.system.controller.StudyRoomsController;
import com.it.system.domain.*;
import com.it.system.service.*;
import com.it.system.mapper.LogbillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class LogbillServiceImpl extends ServiceImpl<LogbillMapper, Logbill>
    implements LogbillService{

    @Autowired
    private LogbillMapper logbillMapper;

    @Autowired
    private StudyroomsService studyroomsService;

    @Autowired
    private TimetypeService timetypeService;

    @Autowired
    private SeatsService seatsService;

    @Autowired
    private KehuService kehuService;

    @Override
    public void newSave(Integer kehuId, Integer roomId, Integer timeId, Integer seatsId,Integer price,String time) {
        Logbill logbill = new Logbill();

        Studyrooms studyrooms = studyroomsService.getById(roomId);
        Timetype timetype = timetypeService.getById(timeId);
        Seats seats = seatsService.getById(seatsId);
        Kehu kehu = kehuService.getById(kehuId);

        logbill.setKehuid(kehuId);
        logbill.setKehuname(kehu.getNickname());

        logbill.setRoomid(roomId);
        logbill.setStatus("未开始");

        logbill.setRoomfloor(studyrooms.getRootFloor());
        logbill.setNick(studyrooms.getRoomLocation());

        logbill.setSeatcolunm(seats.getCcolumn());
        logbill.setSeatsrow(seats.getRrow());

        logbill.setStartTime(timetype.getStart());
        logbill.setEndTime(timetype.getEnd());

        logbill.setAmount(price);
        logbill.setTime(time);
        logbillMapper.insert(logbill);
    }
}




