package com.overstar.es.impl;

import com.overstar.es.api.IOrderService;
import com.overstar.order.export.domain.OrderBase;
import com.overstar.order.export.domain.OrderStarDetail;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * https://blog.csdn.net/u011781521/article/details/77848489  各种聚合操作
 * https://blog.csdn.net/u014646662/article/details/100098851  各种查找嵌套文档操作
 * @Description
 * 订单和订单明细具有强耦合性
 * 考虑nested模型构建
 * 产品跟
 * 也可以采用平铺，但是数据比较大。选择nested
 * @Author stanley.yu
 * @Date 2019/9/19 22:25
 */
@Service
public class OrderIndexService implements IOrderService {


    @Autowired
    private RestHighLevelClient client;

    @Override
    public boolean indexOrderInfo(OrderBase orderBase, List<OrderStarDetail> details) {

        return false;
    }
}
