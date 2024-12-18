package com.it.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName studyrooms
 */
@TableName(value ="studyrooms")
@Data
public class Studyrooms implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String roomName;

    /**
     * 自习室所在位置，例如楼层、具体方位等信息
     */
    private String roomLocation;

    /**
     * 例如楼层
     */
    private String rootFloor;

    /**
     * 人数
     */
    private String totalSeats;

    /**
     * 开放时段段
     */
    private String rootTime;

    /**
     * 状态 0开放 1结束
     */
    private String states;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Studyrooms other = (Studyrooms) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoomName() == null ? other.getRoomName() == null : this.getRoomName().equals(other.getRoomName()))
            && (this.getRoomLocation() == null ? other.getRoomLocation() == null : this.getRoomLocation().equals(other.getRoomLocation()))
            && (this.getRootFloor() == null ? other.getRootFloor() == null : this.getRootFloor().equals(other.getRootFloor()))
            && (this.getTotalSeats() == null ? other.getTotalSeats() == null : this.getTotalSeats().equals(other.getTotalSeats()))
            && (this.getRootTime() == null ? other.getRootTime() == null : this.getRootTime().equals(other.getRootTime()))
            && (this.getStates() == null ? other.getStates() == null : this.getStates().equals(other.getStates()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoomName() == null) ? 0 : getRoomName().hashCode());
        result = prime * result + ((getRoomLocation() == null) ? 0 : getRoomLocation().hashCode());
        result = prime * result + ((getRootFloor() == null) ? 0 : getRootFloor().hashCode());
        result = prime * result + ((getTotalSeats() == null) ? 0 : getTotalSeats().hashCode());
        result = prime * result + ((getRootTime() == null) ? 0 : getRootTime().hashCode());
        result = prime * result + ((getStates() == null) ? 0 : getStates().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roomName=").append(roomName);
        sb.append(", roomLocation=").append(roomLocation);
        sb.append(", rootFloor=").append(rootFloor);
        sb.append(", totalSeats=").append(totalSeats);
        sb.append(", rootTime=").append(rootTime);
        sb.append(", states=").append(states);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}