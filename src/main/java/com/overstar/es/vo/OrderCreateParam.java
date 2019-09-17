package com.overstar.es.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/17 23:21
 */
@Data
public class OrderCreateParam {
    private String username;
    private Date createTime;
    private double totalAmount;
    private long orderId;
    private List<OrderItem> itemList;
}
