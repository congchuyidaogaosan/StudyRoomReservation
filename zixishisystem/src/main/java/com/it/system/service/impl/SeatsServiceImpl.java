package com.it.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.system.domain.Seats;
import com.it.system.service.SeatsService;
import com.it.system.mapper.SeatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SeatsServiceImpl extends ServiceImpl<SeatsMapper, Seats>
        implements SeatsService {

    @Autowired
    private SeatsMapper seatsMapper;

    @Override
    public boolean isInfo(Integer RootId) {

        List<Seats> room_id = seatsMapper.selectList(new QueryWrapper<Seats>().eq("room_id", RootId));

        if (room_id.size() > 0) {
            return true;
        }

        return false;
    }
}




