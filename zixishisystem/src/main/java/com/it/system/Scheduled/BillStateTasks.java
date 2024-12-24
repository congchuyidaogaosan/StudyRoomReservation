package com.it.system.Scheduled;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Logbill;
import com.it.system.service.LogbillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class BillStateTasks {

    @Autowired
    private LogbillService logbillService;

    // 每天凌晨 2 点执行
    @Scheduled(cron = "0 0 * * * *")
    public void cronTask() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDateTime = currentDateTime.format(formatter);
        String[] s = formattedDateTime.split(" ");
        String hh = s[1].split(":")[0];

        System.out.println("Cron Task is running at " + System.currentTimeMillis());
        Logbill logbill = new Logbill();
        logbill.setStatus("自动退订");
        QueryWrapper<Logbill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status","进行中").like("start_time", hh).like("infotime", s[0]);

        logbillService.update(logbill, queryWrapper);

    }


}
