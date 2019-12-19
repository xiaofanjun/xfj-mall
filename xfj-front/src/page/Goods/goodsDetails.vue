<!--商品详情-->
<template>
  <div class="w store-content">
    <div class="gray-box">
      <div class="gallery-wrapper">
        <div class="gallery">
          <div class="thumbnail">
            <ul>
              <li v-for="(item,i) in small" :key="i" :class="{on:big===item}" @click="big=item">
                <img v-lazy="item" :alt="product.productName">
              </li>
            </ul>
          </div>
          <div class="thumb">
            <div class="big">
              <img :src="big" :alt="product.productName">
            </div>
          </div>
        </div>
      </div>
      <!--右边-->
      <div class="banner">
        <div class="sku-custom-title">
          <h4>{{product.productName}}</h4>
          <h6>
            <span>{{product.subTitle}}</span>
            <span class="price">
              <em>¥</em><i>{{product.salePrice.toFixed(2)}}</i></span>
          </h6>
        </div>
        <div class="num">
          <span class="params-name">数量</span>
          <buy-num @edit-num="editNum" :limit="Number(product.limitNum)"></buy-num>
        </div>
        <div class="buy">
          <y-button text="加入购物车"
                    @btnClick="addCart(product.productId,product.salePrice,product.productName,product.productImageBig)"
                    classStyle="main-btn"
                    style="width: 145px;height: 50px;line-height: 48px"></y-button>
          <y-button text="现在购买"
                    @btnClick="checkout(product.productId)"
                    style="width: 145px;height: 50px;line-height: 48px;margin-left: 10px"></y-button>
        </div>
      </div>
    </div>
    <!--产品信息-->
    <el-tabs v-model="activeTab" @tab-click="_handleTabClick"
             style="background: #fff;border-radius: 5px;padding: 0 50px">
      <el-tab-pane label="产品信息" name="itemInfo">
        <div class="item-info" style="border-radius: 5px">
          <div>
            <div class="img-item" v-if="productMsg">
              <div v-html="productMsg">{{ productMsg }}</div>
            </div>
            <div class="no-info" v-else>
              <img src="/static/images/no-data.png">
              <br>
              该商品暂无内容数据
            </div>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="评论" name="comment">
        <span slot="label">评论（{{totalCommentCount}})</span>

        <div style="min-height: 300px">
          <div style="display: flex;justify-content: center;padding-top: 130px"
               v-if="!commentList || commentList.length === 0">
            <span style="font-size: 20px">当前商品无评价</span>
          </div>
          <div v-else>

          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script src="../../js/goods/goodsdetails.js"></script>
<style src="../../css/goods/goodsdetails.scss" lang="scss" scoped></style>
