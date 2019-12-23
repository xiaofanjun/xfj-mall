package com.xfj.pay.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.BizException;
import com.xfj.pay.constants.PayChannelEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
public class PaymentNotifyVO extends AbstractRequest {
    /**
     * 支付渠道（alipay：支付宝  /  wechat_pay：微信）
     */
    private String payChannel;

    private Map<String,String[]> resultMap;//服务端返回的支付通知结果

    private String xml; //微信返回的结果

    @Override
    public void requestCheck() {
        if(payChannel.equals(PayChannelEnum.ALI_PAY)||payChannel.equals(PayChannelEnum.ALI_REFUND)){
            if(resultMap==null||resultMap.size() == 0){
                throw new BizException("0070001","异步通知返回的内容为空");
            }
        }
      if(payChannel.equals(PayChannelEnum.WECHAT_PAY)||payChannel.equals(PayChannelEnum.WECHAT_REFUND)){
          if(StringUtils.isBlank(xml)){
              throw new BizException("0070001","异步通知返回的内容为空");
          }
        }
      }
    }
