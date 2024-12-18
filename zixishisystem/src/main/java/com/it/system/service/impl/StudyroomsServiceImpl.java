package com.it.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.system.domain.Studyrooms;
import com.it.system.service.StudyroomsService;
import com.it.system.mapper.StudyroomsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class StudyroomsServiceImpl extends ServiceImpl<StudyroomsMapper, Studyrooms>
    implements StudyroomsService{

    @Autowired
    private StudyroomsMapper studyroomsMapper;

    @Override
    public List<String> floorList() {
        return studyroomsMapper.floorList();
    }
}




