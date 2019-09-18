package com.overstar.es.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/18 19:44
 */
@Getter
@AllArgsConstructor
public enum EnumOrderType {
    STAR_ORDER("star订单", 11);
    private String desc;
    private Integer code;
}
