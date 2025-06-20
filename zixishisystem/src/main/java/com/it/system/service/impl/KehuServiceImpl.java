package com.it.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.system.domain.Kehu;
import com.it.system.service.KehuService;
import com.it.system.mapper.KehuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class KehuServiceImpl extends ServiceImpl<KehuMapper, Kehu>
    implements KehuService{

    @Autowired
    private KehuMapper kehuMapper;
    @Override
    public Integer listCount() {
        return kehuMapper.listcount();
    }
}




