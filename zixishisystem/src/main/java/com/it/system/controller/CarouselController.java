package com.it.system.controller;


import com.it.system.domain.Carousel;
import com.it.system.domain.Kehu;
import com.it.system.domain.Result;
import com.it.system.service.CarouselService;
import com.it.system.service.KehuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/Carouse")
public class CarouselController {


    @Autowired
    private CarouselService carouselService;


    @PostMapping("list")
    public Result list(@RequestBody Carousel kehu) {

        List<Carousel> list = carouselService.list();
        return Result.ok(list);
    }


    @RequestMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id, HttpServletRequest request) {

        Carousel byId = carouselService.getById(id);
        return Result.ok(byId);
    }

    @RequestMapping("/update")
    public Result update(Carousel carousel) {

        boolean b = carouselService.updateById(carousel);
        return Result.ok();
    }

    @RequestMapping("/save")
    public Result save(Carousel carousel) {

        boolean save = carouselService.save(carousel);
        return Result.ok();
    }

    @RequestMapping("/del/{id}")
    public Result del(@PathVariable("id") Integer id) {

        boolean b = carouselService.removeById(id);
        return Result.ok();
    }

}
