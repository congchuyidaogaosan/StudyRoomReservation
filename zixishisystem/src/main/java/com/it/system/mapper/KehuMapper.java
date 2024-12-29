package com.it.system.mapper;

import com.it.system.domain.Kehu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Entity com.it.system.domain.Kehu
 */
public interface KehuMapper extends BaseMapper<Kehu> {

    @Select("SELECT COUNT(*) FROM `kehu`")
    Integer listcount();
}




