package com.overstar.es.web;

import com.overstar.es.service.order.OrderFactory;
import com.overstar.es.vo.StarOrderCreateParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/17 23:06
 */
@RestController
public class OrderController {


    @Autowired
    private OrderFactory orderFactory;

    @RequestMapping("/create")
    public int orderCreat(StarOrderCreateParam orderCreateParam){
        orderFactory.create(orderCreateParam);
        return 10001;
    }
}
