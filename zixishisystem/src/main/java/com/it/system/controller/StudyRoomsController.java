package com.it.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Kehu;
import com.it.system.domain.Result;
import com.it.system.domain.Studyrooms;
import com.it.system.service.KehuService;
import com.it.system.service.StudyroomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/StudyRooms")
public class StudyRoomsController {


    @Autowired
    private StudyroomsService studyroomsService;

    @GetMapping("floorList")
    public Result floorList() {


        List<String> list = studyroomsService.floorList();

        return Result.ok(list);

    }

    @RequestMapping("list")
    public Result list(Studyrooms studyrooms) {
        QueryWrapper<Studyrooms> queryWrapper = new QueryWrapper<>();
        if (studyrooms.getRootFloor() != null && !studyrooms.getRootFloor().equals("")) {
            queryWrapper.eq("root_floor", studyrooms.getRootFloor());
        }
        queryWrapper.eq("states",0);


        List<Studyrooms> list = studyroomsService.list(queryWrapper);
        return Result.ok(list);
    }

    @RequestMapping("RootList")
    public Result RootList(Studyrooms studyrooms) {
        QueryWrapper<Studyrooms> queryWrapper = new QueryWrapper<>();
        if (studyrooms.getRootFloor() != null && !studyrooms.getRootFloor().equals("")) {
            queryWrapper.eq("root_floor", studyrooms.getRootFloor());
        }



        List<Studyrooms> list = studyroomsService.list(queryWrapper);
        return Result.ok(list);
    }


    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpServletRequest request) {

        Studyrooms byId = studyroomsService.getById(id);
        return Result.ok(byId);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Studyrooms studyrooms) {

        boolean b = studyroomsService.updateById(studyrooms);
        return Result.ok();
    }

    @RequestMapping("/save")
    public Result save(@RequestBody Studyrooms studyrooms) {

        boolean save = studyroomsService.save(studyrooms);
        return Result.ok();
    }

    @RequestMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id) {

        boolean b = studyroomsService.removeById(id);
        return Result.ok();
    }

}
