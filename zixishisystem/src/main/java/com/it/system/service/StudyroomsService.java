package com.it.system.service;

import com.it.system.domain.Studyrooms;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface StudyroomsService extends IService<Studyrooms> {

    List<String> floorList();

    Integer listCount();

    Integer listStateCount();

}
