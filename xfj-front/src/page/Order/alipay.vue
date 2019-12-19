<template>
  <div class="w" style="padding-bottom: 100px;">
    <y-shelf title="支付结果通知">
      <div slot="content">
        <div class="box-inner order-info">
          <p class="payment-detail">扫一扫付款（元）</p>
          <p class="payment-money">{{orderTotal}}</p>
          <div class="img-box">
            <img id="qr" class="pic" v-bind:src="imgPath" alt="加载失败" width="168px" height="168px"/>
            <div class="explain">
              <img class="fn-left" src="https://t.alipayobjects.com/images/T1bdtfXfdiXXXXXXXX.png" alt="扫一扫标识">
              <div class="fn-right">打开手机支付宝<br>扫一扫继续付款</div>
              <div class="timeout" v-if="timeout">二维码已过期</div>
            </div>
          </div>
          <a class="download-alipay" href="https://mobile.alipay.com/index.htm" target="_blank">首次使用请下载手机支付宝</a>
          <div class="count">{{timecount}}</div>

          <div class="qrguide-area">
            <img src="https://t.alipayobjects.com/images/rmsweb/T13CpgXf8mXXXXXXXX.png"
                 :class="show?'show-img':'close-img'" @click="changePic()">
            <img src="https://t.alipayobjects.com/images/rmsweb/T1ASFgXdtnXXXXXXXX.png"
                 :class="show?'close-img':'show-img'" @click="changePic()">
          </div>

          <img src="static/images/red.png" width="50px" height="50px" class="red" title="支付领红包" @click="showRed()">
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
<script src="../../js/order/apipay.js"></script>
<style src="../../css/order/apipay.scss" lang="scss" scoped rel="stylesheet/scss"></style>
