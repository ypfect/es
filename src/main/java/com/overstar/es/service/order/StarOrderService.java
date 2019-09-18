package com.overstar.es.service.order;

import com.overstar.es.vo.OrderCreateParamBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/18 19:40
 */
@Slf4j
@Service
public class StarOrderService extends AbstractOrderCreate {
    @Override
    public boolean validateParam(OrderCreateParamBase orderCreateParamBase) {
        return false;
    }

    @Override
    public boolean amountCalculateHandler(OrderCreateParamBase orderCreateParamBase) {
        return false;
    }

    @Override
    public boolean orderDataPrepareHandler(OrderCreateParamBase orderCreateParamBase) {
        return false;
    }

    @Override
    public boolean oderCreateHandler(OrderCreateParamBase orderCreateParamBase) {
        return false;
    }

    @Override
    public boolean orderLog(OrderCreateParamBase orderCreateParamBase) {
        return false;
    }

    @Override
    public boolean orderMsgInQueueHandler(OrderCreateParamBase orderCreateParamBase) {
        return false;
    }
}
