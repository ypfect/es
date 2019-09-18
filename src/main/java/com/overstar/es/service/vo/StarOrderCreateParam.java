package com.overstar.es.service.vo;

import lombok.Data;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/18 15:58
 */
@Data
public class StarOrderCreateParam extends OrderCreateParamBase<StarOrderDetail> {
    private OrderBase order;
}
