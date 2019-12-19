<template>
  <div class="good-item">
    <div style="">
      <div class="good-img">
        <a @click="openProduct(msg.productId)">
          <img v-lazy="msg.picUrl" :alt="msg.productName" :key="msg.picUrl">
        </a>
      </div>
      <h6 class="good-title" v-html="msg.productName">{{msg.productName}}</h6>
      <h3 class="sub-title ellipsis">{{msg.subTitle}}</h3>
      <div class="good-price pr">
        <div class="ds pa">
          <a @click="openProduct(msg.productId)">
            <y-button text="查看详情" style="margin: 0 5px"></y-button>
          </a>
          <y-button text="加入购物车"
                    style="margin: 0 5px"
                    @btnClick="addCart(msg.productId,msg.salePrice,msg.productName,msg.picUrl)"
                    classStyle="main-btn"
          ></y-button>
        </div>
        <p><span style="font-size:14px">￥</span>{{Number(msg.salePrice).toFixed(2)}}</p>
      </div>
    </div>
  </div>
</template>
<script src="../js/components/mallgoods.js"></script>
<style src="../css/components/mallgoods.scss" lang="scss" rel="stylesheet/scss" scoped></style>
