package com.overstar.es.api;

import com.overstar.order.export.domain.OrderBase;
import com.overstar.order.export.domain.OrderStarDetail;

import java.util.List;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/9 15:17
 */
public interface IOrderService {
    boolean indexOrderInfo(OrderBase orderBase, List<OrderStarDetail> details);
}
