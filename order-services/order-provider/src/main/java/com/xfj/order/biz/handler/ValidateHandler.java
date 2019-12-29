package com.xfj.order.biz.handler;

import com.xfj.commons.tool.exception.BizException;
import com.xfj.order.biz.context.CreateOrderContext;
import com.xfj.order.biz.context.TransHandlerContext;
import com.xfj.order.constant.OrderRetCode;
import com.xfj.order.mapper.OrderMapper;
import com.xfj.user.IMemberService;
import com.xfj.user.rs.QueryMemberRS;
import com.xfj.user.vo.QueryMemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidateHandler extends AbstractTransHandler {

    @Autowired
    OrderMapper orderMapper;

    @Reference(mock = "com.xfj.order.biz.mock.MockMemberService", check = false, group = "${dubbo-group.name}")
    IMemberService memberService;

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean handle(TransHandlerContext context) {
        log.info("begin ValidateHandler :context:" + context);
        CreateOrderContext createOrderContext = (CreateOrderContext) context;
        QueryMemberVO queryMemberRequest = new QueryMemberVO();
        queryMemberRequest.setUserId(createOrderContext.getUserId());
        QueryMemberRS response = memberService.queryMemberById(queryMemberRequest);
        if (OrderRetCode.SUCCESS.getCode().equals(response.getCode())) {
            createOrderContext.setBuyerNickName(response.getUsername());
        } else {
            throw new BizException(response.getCode(), response.getMsg());
        }
        return true;
    }
}
