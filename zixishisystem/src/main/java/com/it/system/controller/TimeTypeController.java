package com.it.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Result;
import com.it.system.domain.Studyrooms;
import com.it.system.domain.Timetype;
import com.it.system.service.StudyroomsService;
import com.it.system.service.TimetypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/TimeType")
public class TimeTypeController {


    @Autowired
    private TimetypeService timetypeService;


    @RequestMapping("list")
    public Result list(Timetype timetype) {
        QueryWrapper<Timetype> queryWrapper = new QueryWrapper<>();
//        if(studyrooms.getRootFloor()!=null && !studyrooms.getRootFloor().equals("")){
//            queryWrapper.eq("root_floor",studyrooms.getRootFloor());
//        }


        List<Timetype> list = timetypeService.list();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = formatter.format(calendar.getTime()).split(" ")[1].substring(0,2);


        for (Timetype t1:list){
            t1.setTime(t1.getStart()+"-"+t1.getEnd());
            String s1 = t1.getEnd().split(":")[0];
            if (Integer.parseInt(s1)>Integer.parseInt(s) ){
                t1.setDisabled(false);
            }else {
                t1.setDisabled(true);
            }

        }







        return Result.ok(list);
    }


    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpServletRequest request) {

        Timetype byId = timetypeService.getById(id);
        return Result.ok(byId);
    }

    @RequestMapping("/update")
    public Result update(Timetype timetype) {

        boolean b = timetypeService.updateById(timetype);
        return Result.ok();
    }

    @RequestMapping("/save")
    public Result save(Timetype timetype) {

        boolean save = timetypeService.save(timetype);
        return Result.ok();
    }

    @RequestMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id) {

        boolean b = timetypeService.removeById(id);
        return Result.ok();
    }

}
