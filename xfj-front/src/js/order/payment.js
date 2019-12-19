import YShelf from '/components/shelf'
import YButton from '/components/YButton'
import {getOrderDet, payMent} from '/api/goods'

export default {
  data: function () {
    return {
      payType: 1,
      addList: {},
      cartList: [],
      addressId: 0,
      productId: '',
      num: '',
      orderTotal: 0,
      userName: '',
      tel: '',
      streetName: '',
      payNow: '立刻支付',
      submit: true,
      nickName: '',
      money: '1.00',
      info: '',
      email: '',
      orderId: '',
      type: '',
      moneySelect: '1.00',
      isCustom: false,
      maxLength: 30
    }
  },
  computed: {
    // 选中的总价格
    checkPrice: function () {
      let totalPrice = 0
      this.cartList && this.cartList.forEach(item => {
        if (item.checked) {
          totalPrice += (item.productNum * item.salePrice)
        }
      })
      return totalPrice
    }
  },
  methods: {
    open: function (t, m) {
      this.$notify.info({
        title: t,
        message: m
      })
    },
    checkValid: function () {
      if (this.nickName !== '' && this.money !== '' && this.isMoney(this.money) && this.email !== '' && this.isEmail(this.email)) {
        this.submit = true
      } else {
        this.submit = false
      }
    },
    messageFail: function (m) {
      this.$message.error({
        message: m
      })
    },
    changeSelect: function (v) {
      if (v !== 'custom') {
        this.money = v
      } else {
        this.isCustom = true
        this.money = ''
      }
      this.checkValid()
    },
    goodsDetails: function (id) {
      window.open(window.location.origin + '#/product/' + id)
    },
    _getOrderDet: function (orderId) {
      let params = {
        params: {
          orderId: this.orderId
        }
      }
      getOrderDet(params).then(res => {
        this.cartList = res.result.goodsList
        this.userName = res.result.userName
        this.orderTotal = res.result.orderTotal
      })
    },
    paySuc: function () {
      this.payNow = '支付中...'
      this.submit = false
      if (this.payType === 1) {
        this.type = 'alipay'
      } else if (this.payType === 2) {
        this.type = 'wechat_pay'
      } else if (this.payType === 3) {
        this.type = 'QQ'
        this.open('提示', '暂不支持qq钱包支付')
        return
      } else {
        this.type = '其它'
      }
      const info = this.cartList[0].title
      payMent({
        nickName: this.userName,
        money: this.money,
        info: info,
        orderId: this.orderId,
        payType: this.type
      }).then(res => {
        if (res.success === true) {
          if (this.payType === 1) {
            const div = document.createElement('div')
            div.innerHTML = res.result
            document.body.appendChild(div)
            document.forms.alipaysubmit.submit()
          } else if (this.payType === 2) {
            this.$router.push({path: '/order/wechat'})
          } else if (this.payType === 3) {
            this.$router.push({path: '/order/qqpay'})
          } else {
            this.$router.push({path: '/order/alipay'})
          }
        } else {
          this.payNow = '立刻支付'
          this.submit = true
          this.messageFail(res.message)
        }
      })
    },
    isMoney: function (v) {
      if (v < 0.1) {
        return false
      }
      var regu = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/
      var re = new RegExp(regu)
      if (re.test(v)) {
        return true
      } else {
        return false
      }
    },
    isEmail: function (v) {
      var regu = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/
      var re = new RegExp(regu)
      if (re.test(v)) {
        return true
      } else {
        return false
      }
    }
  },
  created: function () {
    this.orderId = this.$route.params.orderId
    if (this.orderId) {
      this._getOrderDet(this.orderId)
    } else {
      this.$router.push({path: '/'})
    }
  },
  components: {
    YShelf,
    YButton
  }
}
