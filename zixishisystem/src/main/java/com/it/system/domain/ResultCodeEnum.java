package com.it.system.domain;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"操作成功"),
    FAIL(201, "操作失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),
    ARGUMENT_VALID_ERROR(210, "参数校验异常"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    ACCOUNT_ERROR(214, "账号不正确"),
    PASSWORD_ERROR(215, "密码不正确"),
    LOGIN_MOBLE_ERROR( 216, "账号不正确"),
    ACCOUNT_STOP( 217, "账号已停用"),
    NODE_ERROR( 218, "该节点下有子节点，不可以删除"),


    SELECT_NO(220,"查询数据失败"),
    ADD_NO(220,"添加失败"),
    UPDATE_NO(220,"修改失败"),
    DELECT_NO(220,"修改失败"),

    FILE_OPERATIONS(221,"文件操作异常"),
    FILE_TRANSFER(222,"文件传输异常"),

    SEATS_INIT(225,"座位列表初始化失败"),
    BILL_REALLY(225,"订单已存在");

    private final Integer code;

    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
