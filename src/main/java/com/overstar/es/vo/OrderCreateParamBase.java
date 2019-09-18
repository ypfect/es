package com.overstar.es.vo;

import com.overstar.es.constants.EnumOrderType;
import lombok.Data;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/18 15:58
 */
@Data
public class OrderCreateParamBase <T>{
    private EnumOrderType orderCategory;
    private T param;
    private long orderId;
}
