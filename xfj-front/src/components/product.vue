<template>
  <div class="item" id="product.spu.id">
    <div class="img-box">
      <img :src="product.spu.sku_info[0].ali_image" alt="">
    </div>
    <div class="info">
      <h6 class="ellipsis">{{product.spu.sku_info[0].title}}</h6>
      <p>{{product.spu.sku_info[0].sub_title}}</p>
    </div>
    <p class="price">
      <i>¥</i>
      <span>{{product.spu.price}}</span>
    </p>
    <ul class="dot-list">
      <li></li>
    </ul>
  </div>
</template>
<script src="../js/components/product.js"></script>
<style src="../css/components/product.scss" scoped lang="scss"></style>
