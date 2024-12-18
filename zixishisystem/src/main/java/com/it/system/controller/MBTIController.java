package com.it.system.controller;

import com.it.system.domain.Personality;
import com.it.system.domain.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RequestMapping("MBTI")
@RestController
public class MBTIController {

    private ArrayList arrays = new ArrayList<>();


    @RequestMapping("list")
    public Result list() {
        arrays = new ArrayList<>();
        info();

        return Result.ok(arrays);
    }

    private void info() {

        arrays.add(new Personality(1, "ENFJ", "主人公", "http://localhost:8081/upload/enfj.jpg"));
        arrays.add(new Personality(2, "ENFP", "竞选者", "http://localhost:8081/upload/enfp.jpg"));
        arrays.add(new Personality(3, "INFJ", "提倡者", "http://localhost:8081/upload/infj.jpg"));
        arrays.add(new Personality(4, "INFP", "调停者", "http://localhost:8081/upload/infp.jpg"));
        arrays.add(new Personality(5, "ISFP", "冒险家", "http://localhost:8081/upload/isfp.jpg"));
        arrays.add(new Personality(6, "ISTP", "鉴赏家", "http://localhost:8081/upload/istp.jpg"));
        arrays.add(new Personality(7, "ESTP", "企业家", "http://localhost:8081/upload/estp.jpg"));
        arrays.add(new Personality(8, "ESFP", "表演者", "http://localhost:8081/upload/esfp.jpg"));
        arrays.add(new Personality(9, "ENTJ", "指挥官", "http://localhost:8081/upload/entj.jpg"));
        arrays.add(new Personality(10, "ENTP", "辩论家", "http://localhost:8081/upload/entp.jpg"));
        arrays.add(new Personality(11, "INTJ", "建筑师", "http://localhost:8081/upload/intj.jpg"));
        arrays.add(new Personality(12, "INTP", "逻辑学家", "http://localhost:8081/upload/intp.jpg"));
        arrays.add(new Personality(13, "ESFJ", "守护者", "http://localhost:8081/upload/esfj.jpg"));
        arrays.add(new Personality(14, "ESTJ", "总经理", "http://localhost:8081/upload/estj.jpg"));
        arrays.add(new Personality(15, "ISFJ", "执政官", "http://localhost:8081/upload/isfj.jpg"));
        arrays.add(new Personality(16, "ISTJ", "物流师", "http://localhost:8081/upload/istj.jpg"));

    }


}
