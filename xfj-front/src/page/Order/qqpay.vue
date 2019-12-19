<template>
  <div class="w" style="padding-bottom: 100px;">
    <y-shelf title="XPay收银台 收款方: Exrick">
      <div slot="content">
        <div class="box-inner order-info">
          <img class="wechat" src="static/images/qqpay.png" alt="扫一扫标识">
          <p class="payment-detail">扫一扫付款（元）</p>
          <p class="payment-money">{{orderTotal}}</p>
          <div class="img-box">
            <img class="pic" v-bind:src="imgPath" alt="加载失败" width="168px" height="168px"/>
            <div class="explain">
              <img class="fn-left" src="static/images/qr.png" width="38px" height="38px" alt="扫一扫标识">
              <div class="fn-right">打开手机QQ<br>扫一扫继续付款</div>
              <div class="timeout" v-if="timeout">二维码已过期</div>
            </div>
          </div>
          <div class="count">{{timecount}}</div>
        </div>

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
<script src="../../js/order/qqpay.js"></script>
<style src="../../css/order/qqpay.scss" lang="scss" scoped rel="stylesheet/scss"></style>
