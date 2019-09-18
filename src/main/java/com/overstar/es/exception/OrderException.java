package com.overstar.es.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/18 19:30
 */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderException extends RuntimeException{
    private String msg;
    private int code;
}
