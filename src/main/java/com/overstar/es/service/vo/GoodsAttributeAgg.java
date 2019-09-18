package com.overstar.es.service.vo;

import lombok.Data;

@Data
public class GoodsAttributeAgg {

    private Long goodsSkuId;
    private Integer attrId;
    private String name;
    private String value;
    private Integer type;
    private String unit;


    public GoodsAttributeAgg() {
    }

    public GoodsAttributeAgg(String name, String value, Integer type, String unit) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.unit = unit;
    }

}