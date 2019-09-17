package com.overstar.es.template.order;

import com.overstar.es.vo.OrderCreateParam;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/17 23:30
 */
public  abstract class AbstractOrderCreateService implements OrderCreateService{


    @Override
    public int createOrder(OrderCreateParam param) {
        return 0;
    }
}
