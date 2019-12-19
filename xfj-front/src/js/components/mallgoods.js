import YButton from '/components/YButton'
import {addCart} from '/api/goods.js'
import {mapMutations, mapState} from 'vuex'
import {getStore} from '/utils/storage'

export default {
  props: {
    msg: {
      salePrice: 0
    }
  },
  data: function () {
    return {}
  },
  methods: {
    ...mapMutations(['ADD_CART', 'ADD_ANIMATION', 'SHOW_CART']),
    goodsDetails: function (id) {
      this.$router.push({path: 'product/' + id})
    },
    openProduct: function (id) {
      window.open('//' + window.location.host + '/#/product/' + id)
    },
    addCart: function (id, price, name, img) {
      if (!this.showMoveImg) {     // 动画是否在运动
        if (this.login) { // 登录了 直接存在用户名下
          addCart({userId: getStore('userId'), productId: id, productNum: 1}).then(res => {
            // 并不重新请求数据
            this.ADD_CART({productId: id, salePrice: price, productName: name, productImg: img})
          })
        } else { // 未登录 vuex
          this.ADD_CART({productId: id, salePrice: price, productName: name, productImg: img})
        }
        // 加入购物车动画
        var dom = event.target
        // 获取点击的坐标
        let elLeft = dom.getBoundingClientRect().left + (dom.offsetWidth / 2)
        let elTop = dom.getBoundingClientRect().top + (dom.offsetHeight / 2)
        // 需要触发
        this.ADD_ANIMATION({moveShow: true, elLeft: elLeft, elTop: elTop, img: img})
        if (!this.showCart) {
          this.SHOW_CART({showCart: true})
        }
      }
    }
  },
  computed: {
    ...mapState(['login', 'showMoveImg', 'showCart'])
  },
  mounted: function () {
  },
  components: {
    YButton
  }
}
