package com.overstar.es.web;

import com.overstar.es.vo.OrderCreateParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/17 23:06
 */
@RestController
public class OrderController {


    @RequestMapping("/create")
    public int orderCreat(OrderCreateParam orderCreateParam){

        return 10001;
    }
}
