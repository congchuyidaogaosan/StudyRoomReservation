package com.it.system.service;

import com.it.system.domain.Seats;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface SeatsService extends IService<Seats> {

    boolean isInfo(Integer RootId);
}
