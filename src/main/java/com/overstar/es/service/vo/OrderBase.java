package com.overstar.es.service.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OrderBase implements Serializable, Cloneable{

    private List<StarOrderDetail> starOrderDetails;//订单详情集合
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private Date finishTime;//订单完成时间
    private String orderCode;//订单编码
    private String orderSrc;//订单来源(0导购端，1用户端)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long supplierId;//供应商id
    private Integer storeAreaId;//仓库id
    private String storeName;//仓库名称
    private String storePosition; //仓库位置(仓库所在区域)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;//用户id
    private Integer addressId;//地址id
    private String consigneeName;//收货人姓名
    private String consigneePhone;//收货人电话
    private String area;//收货区域
    private String address;//收货详细地址
    private BigDecimal transportMoney;//运费
    private BigDecimal goodsValue;//商品总价
    private Integer couponId;//优惠券id
    private BigDecimal totalCoupon ;//优惠券金额
    private BigDecimal totalPayment;//优惠前总金额
    private BigDecimal actualPayment;//实际支付金额
    private String getType;//取货方式
    private String getTime;//取货时间(自提、配送时间)
    private Date createTime;//创建时间
    private String payType;//支付类型(0-支付宝  1-微信)
    private String payState;//支付状态(1-未完成  2-已完成)
    private String payMode;//支付方式(1-在线支付, 2-线下支付, 7-扫码支付)
    private Date payTime;//支付时间
    private String transactionId;//支付流水号
    private String refundState;//退款状态（1-申请退款；2-同意退款3-已退款；4-驳回；5-撤销）
    private String tradeState;//订单交易状态（-3-订单删除  -2-订单取消 , -1-订单关闭，1-未付款 2-待备货(待发货) 3-待取货(待发货)  4-已取货(待收货) 5-已完成  97-退款中 98-交易关闭）
    private String cancelCause;//取消原因
    private Date cancelTime;//取消状态
    private Date refundApplyTime;//退款申请时间
    private String returnState;//退货状态（1：等待卖家确认; 2:卖家不同意退货；3：卖家同意退货；4：买家发货；5-卖家确认收货；6-强制取消;7-撤销申请）
    private Long storeMark;//依据此long类型数字查询仓库信息
    private String settleFlag;//结算标志(0-未结算，1-用户不需要发票结算，2-用户需要发票结算)
    private Long settleCode;//结算编号
    private String userDawInvoiceFlag;//是否开了发票(0-没开， 1-开了)
    private Long userInvoiceId;//用户发票id
    private Long settleId;//结算单号id
    private String supplierName;//供应商名称
    private BigDecimal goodsActuallyValue;//商品实付金额
    private String userDrawInvoiceFlag;//是否给用户开发票
    private Date claimTime;//发货时间

    @Override
    public OrderBase clone()  {
        OrderBase orderBase = null;
        try {
            orderBase = (OrderBase) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return orderBase;
    }
}
