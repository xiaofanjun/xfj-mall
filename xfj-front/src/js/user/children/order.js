import {orderList, delOrder} from '/api/goods'
import YShelf from '/components/shelf'
import {getStore} from '/utils/storage'

export default {
  data: function () {
    return {
      orderList: [0],
      userId: '',
      orderStatus: '',
      loading: true,
      currentPage: 1,
      pageSize: 5,
      total: 0
    }
  },
  methods: {
    message: function (m) {
      this.$message.error({
        message: m
      })
    },
    handleSizeChange: function (val) {
      this.pageSize = val
      this._orderList()
    },
    handleCurrentChange: function (val) {
      this.currentPage = val
      this._orderList()
    },
    orderPayment: function (orderId) {
      window.open(window.location.origin + '#/order/payment/' + orderId)
    },
    goodsDetails: function (id) {
      window.open(window.location.origin + '#/product/' + id)
    },
    orderDetail: function (orderId) {
      this.$router.push({
        path: 'orderDetail',
        query: {
          orderId: orderId
        }
      })
    },
    getOrderStatus: function (status) {
      if (status === '0') {
        return '支付审核中'
      } else if (status === '2') {
        return '待发货'
      } else if (status === '3') {
        return '待收货'
      } else if (status === '4') {
        return '交易成功'
      } else if (status === '5') {
        return '交易关闭'
      } else if (status === '6') {
        return '支付失败'
      }
    },
    _orderList: function () {
      let params = {
        params: {
          size: this.pageSize,
          page: this.currentPage
        }
      }
      orderList(params).then(res => {
        this.orderList = res.result.data
        this.total = res.result.total
        this.loading = false
      })
    },
    _delOrder: function (orderId, i) {
      let params = {
        params: {
          orderId: orderId
        }
      }
      delOrder(params).then(res => {
        if (res.success === true) {
          this.orderList.splice(i, 1)
        } else {
          this.message('删除失败')
        }
      })
    }
  },
  created: function () {
    this.userId = getStore('userId')
    this._orderList()
  },
  components: {
    YShelf
  }
}
