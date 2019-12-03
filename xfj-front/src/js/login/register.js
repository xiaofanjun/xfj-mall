import YFooter from '/common/footer'
import YButton from '/components/YButton'
import {register, initKaptcha} from '/api/index.js'

require('../../../static/geetest/gt.js')
// var captcha
export default {
  data: function () {
    return {
      cart: [],
      loginPage: true,
      ruleForm: {
        userName: '',
        userPwd: '',
        errMsg: ''
      },
      registered: {
        userName: '',
        userPwd: '',
        userPwd2: '',
        errMsg: '',
        captcha: ''
      },
      imageCode: '',
      agreement: false,
      registxt: '注册',
      statusKey: ''
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
    toLogin: function () {
      this.$router.push({
        path: '/login'
      })
    },
    regist: function () {
      this.registxt = '注册中...'
      let userName = this.registered.userName
      let userPwd = this.registered.userPwd
      let userPwd2 = this.registered.userPwd2
      let captcha = this.registered.captcha
      if (!userName || !userPwd || !userPwd2) {
        this.message('账号密码不能为空!')
        this.registxt = '注册'
        return false
      }
      if (userPwd2 !== userPwd) {
        this.message('两次输入的密码不相同!')
        this.registxt = '注册'
        return false
      }
      if (!captcha) {
        this.message('请输入验证码')
        this.registxt = '注册'
        return false
      }
      if (!this.agreement) {
        this.message('您未勾选同意我们的相关注册协议!')
        this.registxt = '注册'
        return false
      }
      register({
        userName,
        userPwd,
        captcha
      }).then(res => {
        if (res.success === true) {
          this.messageSuccess()
          this.toLogin()
        } else {
          this.message(res.message)
          this.registxt = '注册'
          this.init_geetest()
          this.registered.captcha = ''
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
    this.init_geetest()
  },
  components: {
    YFooter,
    YButton
  }
}
