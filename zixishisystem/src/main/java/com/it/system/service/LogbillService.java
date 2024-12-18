package com.it.system.service;

import com.it.system.domain.Logbill;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface LogbillService extends IService<Logbill> {

    void newSave(Integer kehuId, Integer roomId, Integer timeId, Integer seatsId);
}
