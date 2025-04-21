package com.it.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Kehu;
import com.it.system.domain.Result;
import com.it.system.domain.Wallet;
import com.it.system.service.KehuService;
import com.it.system.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("wallet")
@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private KehuService kehuService;

    @RequestMapping("Addition")
    public Result Addition(@RequestBody Wallet wallet) {

        if (wallet.getPrice().compareTo(BigDecimal.ZERO) > 0) {

            Kehu byId = kehuService.getById(wallet.getUid());
            BigDecimal price = new BigDecimal(byId.getPrice());
            BigDecimal a = price.add(wallet.getPrice());
            byId.setPrice(a.toString());
            kehuService.updateById(byId);

            walletService.save(wallet);
            return Result.ok();
        }

        return Result.fail("充值失败");
    }


    @RequestMapping("Subtraction")
    public Result Subtraction(@RequestBody Wallet wallet) {
        if (wallet.getPrice().compareTo(BigDecimal.ZERO) >= 0) {

            Kehu byId = kehuService.getById(wallet.getUid());
            BigDecimal price = new BigDecimal(byId.getPrice());
            if (price.compareTo(wallet.getPrice()) >= 0) {
                BigDecimal a = price.subtract(wallet.getPrice());
                byId.setPrice(a.toString());
                kehuService.updateById(byId);

                walletService.save(wallet);
                return Result.ok();
            }

            return Result.fail("余额不足");

        }

        return Result.fail("消费失败");
    }

    @RequestMapping("list/{uid}")
    public Result list(@PathVariable("uid") Integer integer) {

        List<Wallet> list = walletService.list(new QueryWrapper<Wallet>().eq("uid", integer).orderByDesc("thistime"));

        return Result.ok(list);

    }
}
