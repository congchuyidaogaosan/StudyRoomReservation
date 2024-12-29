package com.it.system.mapper;

import com.it.system.domain.Studyrooms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.it.system.domain.Studyrooms
 */
public interface StudyroomsMapper extends BaseMapper<Studyrooms> {

    @Select("SELECT DISTINCT  root_floor FROM studyrooms")
    List<String> floorList();

    @Select("SELECT COUNT(*) FROM `studyrooms`")
    Integer listCount();

    @Select("SELECT COUNT(*) FROM `studyrooms` WHERE states =0")
    Integer listStateCount();

}




