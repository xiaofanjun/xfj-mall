import YFooter from '/common/footer'
import YHeader from '/common/header'
import {mapState} from 'vuex'

export default {
  data: function () {
    return {
      title: '我的订单',
      nav: [
        {name: '我的订单', path: 'orderList'},
        {name: '账户资料', path: 'information'},
        {name: '收货地址', path: 'addressList'},
        {name: '我的优惠', path: 'coupon'},
        {name: '售后服务', path: 'support'},
        {name: '以旧换新', path: 'aihuishou'}
      ],
      editAvatar: true
    }
  },
  computed: {
    ...mapState(['userInfo'])
  },
  methods: {
    tab: function (e) {
      this.$router.push({path: '/user/' + e.path})
    }
  },
  created: function () {
    let path = this.$route.path.split('/')[2]
    this.nav.forEach(item => {
      if (item.path === path) {
        this.title = item.name
      }
    })
  },
  components: {
    YFooter,
    YHeader
  },
  watch: {
    $route: function (to) {
      let path = to.path.split('/')[2]
      this.nav.forEach(item => {
        if (item.path === path) {
          this.title = item.name
        }
      })
    }
  }
}
