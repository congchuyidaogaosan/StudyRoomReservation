package com.it.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Kehu;
import com.it.system.domain.Loglogin;
import com.it.system.domain.Result;
import com.it.system.domain.ResultCodeEnum;
import com.it.system.service.KehuService;
import com.it.system.service.LogloginService;
import com.it.system.utill.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/Login")
public class LoginController {

    @Autowired
    private KehuService kehuService;

    @Autowired
    private LogloginService logloginService;

    /**
     * 登入
     * @param kehu
     * @param httpRequest
     * @return
     */
    @RequestMapping("login")
    public Result login(@RequestBody Kehu kehu, HttpServletRequest httpRequest){
        QueryWrapper<Kehu> queryWrapper = new QueryWrapper<>();

        if (kehu.getUsername()!=null && !kehu.getUsername().equals("")){
            queryWrapper.eq("username",kehu.getUsername());
        }else {
            Loglogin loglogin = new Loglogin();
            loglogin.setUsername(kehu.getUsername());
            loglogin.setNode(ResultCodeEnum.ACCOUNT_ERROR+"");
            loglogin.setIp( IPUtil.getClientIP(httpRequest));

            logloginService.save(loglogin);

            return Result.build(null,ResultCodeEnum.ACCOUNT_ERROR);
        }

        if (kehu.getPassword()!=null && !kehu.getPassword().equals("")){
            queryWrapper.eq("password",kehu.getPassword());
        }else {

            Loglogin loglogin = new Loglogin();
            loglogin.setUsername(kehu.getUsername());
            loglogin.setNode(ResultCodeEnum.PASSWORD_ERROR+"");
            loglogin.setIp( IPUtil.getClientIP(httpRequest));

            logloginService.save(loglogin);

            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }

        List<Kehu> list = kehuService.list(queryWrapper);

        if (list.size()==0){
            Loglogin loglogin = new Loglogin();
            loglogin.setUsername(kehu.getUsername());
            loglogin.setNode(ResultCodeEnum.PASSWORD_ERROR+"");
            loglogin.setIp( IPUtil.getClientIP(httpRequest));

            logloginService.save(loglogin);
            return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
        }


        Kehu thisKehu = list.get(0);

        Loglogin loglogin = new Loglogin();
        loglogin.setUsername(kehu.getUsername());
        loglogin.setNode(ResultCodeEnum.SUCCESS+"");
        loglogin.setIp( IPUtil.getClientIP(httpRequest));

        logloginService.save(loglogin);

        return Result.ok(thisKehu);
    }
}
