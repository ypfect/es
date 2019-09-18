package com.overstar.es.service.order;

import com.overstar.es.constants.EnumOrderType;
import com.overstar.es.vo.OrderCreateParamBase;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/18 15:53
 */
public interface IOrderService {

    long orderCreate(OrderCreateParamBase orderCreateParamBase);

    EnumOrderType orderCategory();
}
