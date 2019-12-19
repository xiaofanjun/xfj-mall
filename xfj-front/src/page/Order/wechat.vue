<template>
  <div class="w" style="padding-bottom: 100px;">
    <y-shelf title="支付结果通知">
      <div slot="content">
        <div class="box-inner order-info">
          <img class="wechat" src="static/images/weixinpay@2x.png" alt="扫一扫标识">
          <p class="payment-detail">扫一扫付款（元）</p>
          <p class="payment-money">{{orderTotal}}</p>
          <div class="img-box">
            <img class="pic" v-bind:src="imgPath" alt="加载失败" width="168px" height="168px"/>
            <div class="timeout" v-if="timeout">二维码已过期</div>
          </div>
        </div>
        <img class="explain" src="static/images/wechat-explain.png" alt="扫一扫标识">
        <div class="count">{{timecount}}</div>

        <div>
          <div class="box-btn">
            <div>
              <span>

              </span>
              <em><span>¥</span>{{orderTotal}}</em>
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
<script src="../../js/order/wechat.js"></script>
<style src="../../css/order/wechat.scss" lang="scss" scoped rel="stylesheet/scss"></style>
