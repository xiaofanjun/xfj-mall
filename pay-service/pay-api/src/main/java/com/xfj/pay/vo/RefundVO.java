package com.xfj.pay.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.pay.validatorextend.PayChannel;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class RefundVO extends AbstractRequest {
	/**
	 * 用户id
	 */
	@NotNull(message = "userId不可为空")
	private String userId;
	/**
	 * 支付渠道（alipay：支付宝  /  wechat_pay：微信）
	 */
	@PayChannel
	private String payChannel;
	/**
	 * 支付订单号
	 */
	@NotNull(message = "原交易订单号不能为空")
	private String orderId;

	/**
	 * 申请退款金额
	 */
	@NotNull(message = "退款金额不能为空")
	@DecimalMin(value = "0.01",message = "退款最小金额不能小于0.01元")
	private BigDecimal refundAmount;

	@Override
	public void requestCheck() {

	}
}