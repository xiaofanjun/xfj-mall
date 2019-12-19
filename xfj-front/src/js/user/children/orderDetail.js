import {getOrderDet, cancelOrder} from '/api/goods'
import YShelf from '/components/shelf'
import {getStore} from '/utils/storage'
import countDown from '/components/countDown'

export default {
  data: function () {
    return {
      orderList: [0],
      userId: '',
      orderStatus: 0,
      orderId: '',
      userName: '',
      tel: '',
      streetName: '',
      orderTitle: '',
      createTime: '',
      payTime: '',
      closeTime: '',
      finishTime: '',
      orderTotal: '',
      loading: true,
      countTime: 0
    }
  },
  methods: {
    message: function (m) {
      this.$message.error({
        message: m
      })
    },
    orderPayment: function (orderId) {
      window.open(window.location.origin + '#/order/payment?orderId=' + orderId)
    },
    goodsDetails: function (id) {
      window.open(window.location.origin + '#/product/' + id)
    },
    _getOrderDet: function () {
      let params = {
        params: {
          orderId: this.orderId
        }
      }
      getOrderDet(params).then(res => {
        if (res.result.orderStatus === '0') {
          this.orderStatus = 1
        } else if (res.result.orderStatus === '1') {
          this.orderStatus = 2
        } else if (res.result.orderStatus === '4') {
          this.orderStatus = 5
        } else if (res.result.orderStatus === '5') {
          this.orderStatus = -1
        } else if (res.result.orderStatus === '6') {
          this.orderStatus = 6
        }
        this.orderList = res.result.goodsList
        this.orderTotal = res.result.orderTotal
        this.userName = res.result.addressInfo.userName
        this.tel = res.result.addressInfo.tel
        this.streetName = res.result.addressInfo.streetName
        this.createTime = res.result.createDate
        this.closeTime = res.result.closeDate
        this.payTime = res.result.payDate
        if (this.orderStatus === 5) {
          this.finishTime = res.result.finishDate
        } else {
          this.countTime = res.result.finishDate
        }
        this.loading = false
      })
    },
    _cancelOrder: function () {
      cancelOrder({orderId: this.orderId}).then(res => {
        if (res.success === true) {
          this._getOrderDet()
        } else {
          this.message('取消失败')
        }
      })
    }
  },
  created: function () {
    this.userId = getStore('userId')
    this.orderId = this.$route.query.orderId
    this.orderTitle = '订单号：' + this.orderId
    this._getOrderDet()
  },
  components: {
    YShelf,
    countDown
  }
}
