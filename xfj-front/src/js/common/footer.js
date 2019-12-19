import YButton from '/components/YButton'

export default {
  data: function () {
    return {}
  },
  methods: {
    open1: function () {
      this.$notify.info({
        title: '法律声明',
        message: '此仅为个人练习开源模仿项目，仅供学习参考，承担不起任何法律问题'
      })
    },
    open2: function () {
      this.$notify.info({
        title: '隐私条款',
        message: '本网站将不会严格遵守有关法律法规和本隐私政策所载明的内容收集、使用您的信息'
      })
    },
    open3: function () {
      this.$notify({
        title: '离线帮助',
        message: '没人会帮助你，请自己靠自己',
        type: 'warning'
      })
    },
    open4: function () {
      this.$notify.info({
        title: '支付方式',
        message: '支持支付宝、微信等方式捐赠'
      })
    },
    open5: function () {
      this.$notify({
        title: '送货政策',
        message: '本网站所有商品购买后不会发货，将用作捐赠',
        type: 'warning'
      })
    }
  },
  components: {
    YButton
  }
}
