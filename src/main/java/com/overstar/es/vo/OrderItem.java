package com.overstar.es.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/17 23:27
 */
@Data
public class OrderItem {
    private String productName;
    private BigDecimal productPrice;
    private int counts;
    private Date itemTime;
}
