package com.overstar.es.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class StarOrderDetail implements Serializable, Cloneable{
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;//订单详情id
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;//订单id
    private String orderCode;//订单编码
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long skuId;//skuid
    private Date createTime;//创建时间
    private String showName;//页面展示商品名
    private String pic2;//商品图
    private BigDecimal currentPrice;//价格
    private Integer skuNumber;//sku数量
    private Integer stockNumber;//库存数量
    private String spec;//规格
    private String parameter;//参数
    private String enough = "1";//库存是否够(0-库存不足无法备货  1-库存够)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long skuCode;//商品编码
    private String skuModel;//商品型号
    private String skuType;//商品类型
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String settleState;//结算状态
    private Long settleCode;//结算单号
    private BigDecimal purchasePrice;//采购价格
    private BigDecimal settleMoney;//结算单价
    private BigDecimal skuSettleMoney;//结算金额
    private List<GoodsAttributeAgg> attrs;
    @Override
    public Object clone() {
        StarOrderDetail detail = null;
        try{
            detail = (StarOrderDetail)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return detail;
    }



    
}
