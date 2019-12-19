<template>
  <div>
    <y-shelf title="我的订单">
      <div slot="content">
        <div v-loading="loading" element-loading-text="加载中..." v-if="orderList.length" style="min-height: 10vw;">
          <div v-for="(item,i) in orderList" :key="i">
            <div class="gray-sub-title cart-title">
              <div class="first">
                <div>
                  <span class="date" v-text="item.createTime"></span>
                  <span class="order-id"> 订单号： <a @click="orderDetail(item.orderId)">{{item.orderId}}</a> </span>
                </div>
                <div class="f-bc">
                  <span class="price">单价</span>
                  <span class="num">数量</span>
                  <span class="operation">商品操作</span>
                </div>
              </div>
              <div class="last">
                <span class="sub-total">实付金额</span>
                <span class="order-detail"> <a @click="orderDetail(item.orderId)">查看详情 ><em class="icon-font"></em></a> </span>
              </div>
            </div>
            <div class="pr">
              <div class="cart" v-for="(good,j) in item.orderItemDto" :key="j">
                <div class="cart-l" :class="{bt:j>0}">
                  <div class="car-l-l">
                    <div class="img-box"><a @click="goodsDetails(good.itemId)"><img :src="good.picPath" alt=""></a>
                    </div>
                    <div class="ellipsis"><a style="color: #626262;"
                                             @click="goodsDetails(good.itemId)">{{good.title}}</a></div>
                  </div>
                  <div class="cart-l-r">
                    <div>¥ {{Number(good.price).toFixed(2)}}</div>
                    <div class="num">{{good.num}}</div>
                    <div class="type">
                      <el-button style="margin-left:20px" @click="_delOrder(item.orderId,i)" type="danger" size="small"
                                 v-if="j<1" class="del-order">删除此订单
                      </el-button>
                      <!-- <a @click="_delOrder(item.orderId,i)" href="javascript:;" v-if="j<1" class="del-order">删除此订单</a> -->
                    </div>
                  </div>
                </div>
                <div class="cart-r">
                  <span></span>
                  <span></span>
                </div>
              </div>
              <div class="prod-operation pa" style="right: 0;top: 0;">
                <div class="total">¥ {{item.payment}}</div>
                <div v-if="item.status ===0">
                  <el-button @click="orderPayment(item.orderId)" type="primary" size="small">现在付款</el-button>
                </div>
                <div class="status" v-if="item.status !== 0"> {{getOrderStatus(item.status)}}</div>
              </div>
            </div>
          </div>
        </div>
        <div v-loading="loading" element-loading-text="加载中..." class="no-info" v-else>
          <div style="padding: 100px 0;text-align: center">
            你还未创建过订单
          </div>
        </div>
      </div>
    </y-shelf>
    <div style="float:right">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>
<script src="../../../js/user/children/order.js"></script>
<style src="../../../css/user/children/order.scss" lang="scss" scoped></style>
