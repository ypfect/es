package com.overstar.es.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/18 10:15
 */
@AllArgsConstructor
@Getter
public enum EnumErrorCode {
    INDEX_ERROR("索引异常", 10001),
    ORDER_VALIDATE_ERROR("订单参数验证失败", 20001),
    ORDER_DATA_CALCULATE_ERROR("订单算价失败", 20002),
    ORDER_DATA_PACKAGE_PREPARE_ERROR("订单参数封装失败", 20003),
    ORDER_PERSIS_ERROR("订单持久化失败", 20004),
    ORDER_DELAY_QUEUE_ERROR("订单加入延时队列失败", 20005),
    ORDER_SERVICE_INIT_ERROR("订单处理handler初始化失败", 20006),
    ;
    private String desc;
    private Integer code;
}
