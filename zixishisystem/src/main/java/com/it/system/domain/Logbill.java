package com.it.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @TableName logbill
 */
@TableName(value = "logbill")
@Data
public class Logbill implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String  time;
    /**
     *
     */
    private Integer kehuid;

    private String kehuname;
    /**
     *
     */
    private Integer roomid;

    /**
     *
     */
    private String nick;

    /**
     *
     */
    private String roomfloor;

    /**
     *
     */
    private String status;

    /**
     *
     */
    private Date infotime;

    private String seatsrow;
    private String seatcolunm;

    private String startTime;
    private String endTime;

    private Integer amount;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}