import YShelf from '/components/shelf'
import YButton from '/components/YButton'
import {getStore, setStore} from '/utils/storage'

export default {
  data: function () {
    return {
      show: true,
      count: 25,
      userId: '',
      orderTotal: '',
      userName: '',
      tel: '',
      streetName: '',
      checkPrice: '',
      payNow: '等待支付...',
      submit: false,
      nickName: '',
      money: '',
      info: '',
      email: '',
      dialogVisible: true,
      isCustom: 0,
      imgPath: 'static/qr/alipay/custom.png',
      picName: '',
      timeout: false,
      timecount: ''
    }
  },
  computed: {},
  methods: {
    changePic: function () {
      this.show = !this.show
    },
    toMoney: function (num) {
      num = parseFloat(num)
      num = num.toFixed(2)
      num = num.toLocaleString()
      return num
    },
    handleClose: function () {
      this.countDown()
      this.countTime()
    },
    showRed: function () {
      this.dialogVisible = true
    },
    countDown: function () {
      let me = this
      if (this.count === 0) {
        this.payNow = '确认已支付'
        this.submit = true
        return
      } else {
        this.count--
      }
      setTimeout(function () {
        me.countDown()
      }, 1000)
    },
    countTime: function () {
      let me = this
      let time = getStore('setTime')
      if (time <= 0) {
        this.timeout = true
        this.timecount = ''
        this.count = 10000
        return
      } else {
        time--
        this.showTime(time)
        setStore('setTime', time)
      }
      setTimeout(function () {
        me.countTime()
      }, 1000)
    },
    showTime: function (v) {
      let m = 0
      let s = 0
      if (v === null || v === '') {
        return ''
      }
      if (v >= 60) {
        m = Math.floor(v / 60)
        s = v % 60
      } else {
        s = v
      }
      if (m >= 0 && m <= 9) {
        m = '0' + m
      }
      if (s >= 0 && s <= 9) {
        s = '0' + s
      }
      this.timecount = '请于 ' + m + ' 分 ' + s + ' 秒 内支付'
    },
    paySuc: function () {
      this.$router.push({path: '/order/paysuccess', query: {price: this.orderTotal}})
    }
  },
  mounted: function () {
    let price = getStore('price')
    let isCustom = getStore('isCustom')
    this.orderTotal = this.toMoney(price)
    if (this.orderTotal === 'NaN') {
      this.$router.push({path: '/'})
    }
    if (isCustom !== 'true') {
      this.picName = this.orderTotal
      this.imgPath = 'static/qr/alipay/' + this.picName + '.png'
    }
  },
  components: {
    YShelf,
    YButton
  }
}
