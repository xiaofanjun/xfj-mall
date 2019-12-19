<template>
  <div class="header-box">
    <div>
      <header class="w">
        <div class="w-box">
          <div class="nav-logo">
            <h1 @click="changePage(-1)">
              <router-link to="/" title=""></router-link>
            </h1>
          </div>
          <!--<div _ngcontent-c1="" class="container">
            <ul _ngcontent-c1="" class="nav-list"> &lt;!&ndash;&ndash;&gt;
              <li _ngcontent-c1=""><a _ngcontent-c1="" class="active" title="在线商城" href="/" target="">在线商城 </a></li>
            </ul>
          </div>-->
          <div class="right-box">
            <div class="nav-aside" ref="aside" :class="{fixed:st}">
              <div class="user pr">
                <router-link to="/user">个人中心</router-link>
                <!--用户信息显示-->
                <div class="nav-user-wrapper pa" v-if="login">
                  <div class="nav-user-list">
                    <ul>
                      <!--头像-->
                      <li class="nav-user-avatar">
                        <div>
                          <span class="avatar" :style="{backgroundImage:'url('+userInfo.info.file+')'}">
                          </span>
                        </div>
                        <p class="name">{{userInfo.info.username}}</p>
                      </li>
                      <li>
                        <router-link to="/user/orderList">我的订单</router-link>
                      </li>
                      <li>
                        <router-link to="/user/information">账号资料</router-link>
                      </li>
                      <li>
                        <router-link to="/user/addressList">收货地址</router-link>
                      </li>
                      <li>
                        <router-link to="/user/support">售后服务</router-link>
                      </li>
                      <li>
                        <router-link to="/user/coupon">我的优惠</router-link>
                      </li>
                      <li>
                        <a href="javascript:;" @click="_loginOut">退出</a>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              <div class="shop pr" @mouseover="cartShowState(true)" @mouseout="cartShowState(false)"
                   ref="positionMsg">
                <router-link to="/cart"></router-link>
                <span class="cart-num">
                  <i class="num" :class="{no:totalNum <= 0,move_in_cart:receiveInCart}">{{totalNum}}</i></span>
                <!--购物车显示块-->
                <div class="nav-user-wrapper pa active" v-show="showCart">
                  <div class="nav-user-list">
                    <div class="full" v-show="totalNum">
                      <!--购物列表-->
                      <div class="nav-cart-items">
                        <ul>
                          <li class="clearfix" v-for="(item,i) in cartList" :key="i">
                            <div class="cart-item">
                              <div class="cart-item-inner">
                                <a @click="openProduct(item.productId)">
                                  <div class="item-thumb">
                                    <img :src="item.productImg">
                                  </div>
                                  <div class="item-desc">
                                    <div class="cart-cell"><h4>
                                      <a href="" v-text="item.productName"></a>
                                    </h4>
                                      <!-- <p class="attrs"><span>白色</span></p> -->
                                      <h6><span class="price-icon">¥</span><span
                                        class="price-num">{{item.salePrice}}</span><span
                                        class="item-num">x {{item.productNum}}</span>
                                      </h6></div>
                                  </div>
                                </a>
                                <div class="del-btn del" @click="delGoods(item.productId)">删除</div>
                              </div>
                            </div>
                          </li>
                        </ul>
                      </div>
                      <!--总件数-->
                      <div class="nav-cart-total"><p>共 <strong>{{totalNum}}</strong> 件商品</p> <h5>合计：<span
                        class="price-icon">¥</span><span
                        class="price-num">{{totalPrice}}</span></h5>
                        <h6>
                          <y-button classStyle="main-btn"
                                    style="height: 40px;width: 100%;margin: 0;color: #fff;font-size: 14px;line-height: 38px"
                                    text="去购物车" @btnClick="toCart"></y-button>
                        </h6>
                      </div>
                    </div>
                    <div v-show="!totalNum" style="height: 313px;text-align: center" class="cart-con">
                      <p>您的购物车竟然是空的!</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </header>
      <slot name="nav">
        <div class="nav-sub"
             @mouseleave="handleNavSubMouseLeave"
             :class="{fixed:st}">
          <div class="nav-sub-bg"></div>
          <div class="nav-sub-wrapper" :class="{fixed:st}">
            <div class="w">
              <ul class="nav-list2">
                <li>
                  <router-link to="/"><a @click="changGoods(-1)" :class="{active:choosePage===-1}">首页</a></router-link>
                </li>
                <li>
                  <a @click="changGoods(-2)" :class="{active:choosePage===-2}">全部商品</a>
                </li>
                <li
                  @mouseenter="handleNavItemMouseEnter(item, i)"
                  v-for="(item,i) in navList"
                  :key="i">
                  <a @click="changGoods(i, item)" :class="{active:i===choosePage}">{{item.picUrl}}</a>
                </li>
              </ul>
              <div class="nav-search">
                <el-autocomplete
                  placeholder="请输入商品信息"
                  icon="search"
                  v-model="input"
                  minlength=1
                  maxlength=100
                  :fetch-suggestions="querySearchAsync"
                  @select="handleSelect"
                  :on-icon-click="handleIconClick"
                  @keydown.enter.native="handleIconClick">
                </el-autocomplete>
                <!--<router-link to="/goods"><a @click="changePage(2)">全部商品</a></router-link>-->
                <!--<router-link to="/thanks"><a @click="changePage(4)">捐赠</a></router-link>-->
                <!-- <router-link to="/">Smartisan M1 / M1L</router-link>
                <router-link to="/">Smartisan OS</router-link>
                <router-link to="/">欢喜云</router-link>
                <router-link to="/">应用下载</router-link>
                <router-link to="/">官方论坛</router-link> -->
              </div>
            </div>
          </div>
          <div v-if="showCateDiv" class="dropdown-div">
            <div class="cate-con">
              <div
                class="cate"
                v-for="(item, index) in curCateList"
                :key="index">
                <div class="cate-name-label">{{item.name}}</div>
                <div
                  class="cate-item"
                  v-for="(childItem, idx) in item.children"
                  @click="goGoodsCatePage(childItem)"
                  :key="idx">
                  <img :src="childItem.iconUrl" class="item-icon">
                  <div>{{childItem.name}}</div>
                </div>
              </div>
            </div>
          </div>

          <div class="dropdown-div" v-if="showRecommend">
            <div class="recommend-con">
              <div v-for="(item, index) in recommendPanel.panelContentItems"
                   class="recommend-item"
                   @click="goRecommendGoodsDetail(item)"
                   :key="index">
                <div class="item">
                  <img :src="item.picUrl" alt="手机图片">
                  <div class="product-name">{{item.productName}}</div>
                  <div><span class="product-price">&yen;{{item.salePrice}}</span>&nbsp;起</div>
                </div>
              </div>
            </div>
          </div>

        </div>
      </slot>
    </div>
  </div>
</template>
<script src="../js/common/header.js"></script>
<style src="../css/common/header.scss" lang="scss" rel="stylesheet/scss" scoped></style>

