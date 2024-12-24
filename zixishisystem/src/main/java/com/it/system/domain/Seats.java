package com.it.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName seats
 */
@TableName(value ="seats")
@Data
public class Seats implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer seatId;

    /**
     * 
     */
    private Integer roomId;

    /**
     * 行
     */
    private String Rrow;

    private String number;

    /**
     * 列
     */
    private String Ccolumn;

    private Integer price;
    /**
     * 表示座位是否可用，1 表示可用，0 表示已被预约
     */
    private String  isAvailable;

    @TableField(exist = false)
    private boolean selected=false;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;




}