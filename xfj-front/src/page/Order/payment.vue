<template>
  <div class="w" style="padding-bottom: 100px;">
    <y-shelf title="支付订单">
      <div slot="content">
        <div class="box-inner order-info">
          <h3>提交订单成功，请选择支付方式</h3>
          <p class="payment-detail">请在 <span>2 小时内</span>完成支付，超时订单将自动取消。</p>
          <p class="payment-detail">我们不会在您完成支付后的 72 小时内发货，您的支付将用作捐赠</p>
        </div>
        <!--支付方式-->
        <div class="pay-type">
          <div class="p-title">支付方式</div>
          <div class="pay-item">
            <div :class="{active:payType==1}" @click="payType=1"><img src="/static/images/alipay@2x.png" alt=""></div>
            <div :class="{active:payType==2}" @click="payType=2"><img src="/static/images/weixinpay@2x.png" alt="">
            </div>
            <div :class="{active:payType==3}" @click="payType=3"><img src="/static/images/qqpay.png" alt=""></div>
          </div>
        </div>
        <div>
          <div class="box-inner">
            <div>
              <span>
                订单金额：
              </span>
              <em><span>¥</span>{{orderTotal.toFixed(2)}}</em>
              <span>
                实际应付金额：
              </span>
              <em><span>¥</span>{{orderTotal.toFixed(2)}}</em>
              <y-button :text="payNow"
                        :classStyle="submit?'main-btn':'disabled-btn'"
                        style="width: 120px;height: 40px;font-size: 16px;line-height: 38px"
                        @btnClick="paySuc()"
              ></y-button>
            </div>
          </div>
        </div>

      </div>
    </y-shelf>
  </div>
</template>
<script src="../../js/order/payment.js"></script>
<style src="../../css/order/payment.scss" lang="scss" scoped rel="stylesheet/scss"></style>
