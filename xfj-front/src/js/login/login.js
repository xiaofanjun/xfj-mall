import YFooter from '/common/footer'
import YButton from '/components/YButton'
import {userLogin, initKaptcha} from '/api/index.js'
import {addCart} from '/api/goods.js'
import {setStore, getStore, removeStore} from '/utils/storage.js'

require('../../../static/geetest/gt.js')
export default {
  data: function () {
    return {
      cart: [],
      loginPage: true,
      ruleForm: {
        userName: '',
        userPwd: '',
        captcha: '',
        errMsg: ''
      },
      registered: {
        userName: '',
        userPwd: '',
        userPwd2: '',
        errMsg: ''
      },
      autoLogin: false,
      logintxt: '登录',
      imageCode: ''
    }
  },
  computed: {
    count: function () {
      return this.$store.state.login
    }
  },
  methods: {
    open: function (t, m) {
      this.$notify.info({
        title: t,
        message: m
      })
    },
    messageSuccess: function () {
      this.$message({
        message: '恭喜您，注册成功！赶紧登录体验吧',
        type: 'success'
      })
    },
    message: function (m) {
      this.$message.error({
        message: m
      })
    },
    getRemembered: function () {
      var judge = getStore('remember')
      if (judge === 'true') {
        this.autoLogin = true
        this.ruleForm.userName = getStore('rusername')
        this.ruleForm.userPwd = getStore('rpassword')
      }
    },
    rememberPass: function () {
      if (this.autoLogin === true) {
        setStore('remember', 'true')
        setStore('rusername', this.ruleForm.userName)
        setStore('rpassword', this.ruleForm.userPwd)
      } else {
        setStore('remember', 'false')
        removeStore('rusername')
        removeStore('rpassword')
      }
    },
    toRegister: function () {
      this.$router.push({
        path: '/register'
      })
    },
    // 登录返回按钮
    login_back: function () {
      this.$router.go(-1)
    },
    // 登陆时将本地的添加到用户购物车
    login_addCart: function () {
      let cartArr = []
      let locaCart = JSON.parse(getStore('buyCart'))
      if (locaCart && locaCart.length) {
        locaCart.forEach(item => {
          cartArr.push({
            userId: getStore('userId'),
            productId: item.productId,
            productNum: item.productNum
          })
        })
      }
      this.cart = cartArr
    },
    login: function () {
      this.logintxt = '登录中...'
      this.rememberPass()
      if (!this.ruleForm.userName || !this.ruleForm.userPwd) {
        this.message('账号或者密码不能为空!')
        return false
      }
      if (!this.ruleForm.captcha) {
        this.message('请输入验证码!')
        return false
      }
      var params = {
        userName: this.ruleForm.userName,
        userPwd: this.ruleForm.userPwd,
        captcha: this.ruleForm.captcha
      }
      userLogin(params).then(res => {
        // eslint-disable-next-line eqeqeq
        if (res.success) {
          setStore('access_token', res.result.token)
          setStore('userId', res.result.id)
          // 登录后添加当前缓存中的购物车
          if (this.cart.length) {
            for (var i = 0; i < this.cart.length; i++) {
              addCart(this.cart[i]).then(res => {
                if (res.success === true) {
                }
              })
            }
            removeStore('buyCart')
            this.$router.push({
              path: '/'
            })
          } else {
            this.$router.push({
              path: '/'
            })
          }
        } else {
          this.logintxt = '登录'
          this.message(res.message)
          this.init_geetest()
          return false
        }
      })
    },
    init_geetest: function () {
      initKaptcha().then(res => {
        this.imageCode = 'data:image/gif;base64,' + res.result
      })
    }
  },
  mounted: function () {
    this.getRemembered()
    this.login_addCart()
    this.init_geetest()
  },
  components: {
    YFooter,
    YButton
  }
}
