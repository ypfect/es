package com.overstar.es.template.order;

import com.overstar.es.vo.OrderCreateParam;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/17 23:29
 */
public interface OrderCreateService {

    int createOrder(OrderCreateParam param);
}
