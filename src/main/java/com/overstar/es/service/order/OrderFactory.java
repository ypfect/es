package com.overstar.es.service.order;

import com.overstar.es.constants.EnumOrderType;
import com.overstar.es.exception.OrderException;
import com.overstar.es.vo.OrderCreateParamBase;
import com.overstar.es.utils.ApplicationContextUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.overstar.es.constants.EnumErrorCode.ORDER_SERVICE_INIT_ERROR;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/18 20:04
 */
@Service
public class OrderFactory {


    public static HashMap<EnumOrderType,IOrderService> orderServiceMap = new HashMap<>();

    /**
     * 初始化服务
     */
    @PostConstruct
    private void initOrderServer(){
        //这里直接直接autowired
        Map<String, IOrderService> beans = ApplicationContextUtil.getBeans(IOrderService.class);
        if (CollectionUtils.isEmpty(beans)){
            throw new OrderException(ORDER_SERVICE_INIT_ERROR.getDesc(),ORDER_SERVICE_INIT_ERROR.getCode());
        }
        orderServiceMap.clear();
        beans.forEach((s, iOrderService) -> {
            if (iOrderService.orderCategory() == null) return;
            if (orderServiceMap.containsKey(iOrderService.orderCategory())) {
                throw new RuntimeException(iOrderService.getClass().getName() + ", handlerType return value repeat with " + orderServiceMap.get(iOrderService.orderCategory()).getClass().getName());
            }
            orderServiceMap.put(iOrderService.orderCategory(), iOrderService);
        });

    }


    public long create(OrderCreateParamBase createParamBase){
        EnumOrderType orderCategory = createParamBase.getOrderCategory();
        IOrderService iOrderService = orderServiceMap.get(orderCategory);
        return iOrderService.orderCreate(createParamBase);
    }
}
