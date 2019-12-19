import {productDet, addCart, getAllComments, getAllCommentsCount} from '/api/goods'
import {mapMutations, mapState} from 'vuex'
import YShelf from '/components/shelf'
import BuyNum from '/components/buynum'
import YButton from '/components/YButton'
import {getStore} from '/utils/storage'

export default {
  data: function () {
    return {
      productMsg: {},
      small: [],
      big: '',
      product: {
        salePrice: 0
      },
      productNum: 1,
      userId: '',
      // form
      pageForm: {
        page: 0,
        size: 10
      },

      // data
      activeTab: 'itemInfo',
      totalCommentCount: 0,
      commentList: [],
      commentType: null
    }
  },
  computed: {
    ...mapState(['login', 'showMoveImg', 'showCart'])
  },
  methods: {
    ...mapMutations(['ADD_CART', 'ADD_ANIMATION', 'SHOW_CART']),
    _productDet: function (productId) {
      productDet({params: {productId}}).then(res => {
        let result = res.result
        this.product = result
        this.productMsg = result.detail || ''
        this.small = result.productImageSmall
        this.big = this.small[0]
      })
    },
    _productCommentCount: function (productId) {
      let params = {
        productId,
        type: this.commentType
      }
      getAllCommentsCount({params: params}).then(res => {
        let result = res.result
        console.log('%c[goodsDetails-res]', 'color: #63ADD1', result)
      })
    },
    _productCommentList: function (productId) {
      let params = {
        productId,
        type: this.commentType,
        page: this.pageForm.page,
        size: this.pageForm.size
      }
      getAllComments({params: params}).then(res => {
        let result = res.result
        console.log('%c[goodsDetails-res]', 'color: #63ADD1', result)
      })
    },
    _handleTabClick: function (tabComponent) {
      let {name} = tabComponent
      if (name === 'comment') {
        if (this.totalCommentCount !== 0) {
          this.pageForm.page = 0
          this._productCommentList(this.$route.params.productId)
        }
      }
    },

    addCart: function (id, price, name, img) {
      if (!this.showMoveImg) {     // 动画是否在运动
        if (this.login) { // 登录了 直接存在用户名下
          addCart({userId: this.userId, productId: id, productNum: this.productNum}).then(res => {
            // 并不重新请求数据
            this.ADD_CART({
              productId: id,
              salePrice: price,
              productName: name,
              productImg: img,
              productNum: this.productNum
            })
          })
        } else { // 未登录 vuex
          this.ADD_CART({
            productId: id,
            salePrice: price,
            productName: name,
            productImg: img,
            productNum: this.productNum
          })
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
    },
    checkout: function (productId) {
      this.$router.push({path: '/checkout/' + productId + '/' + this.productNum})
    },
    editNum: function (num) {
      this.productNum = num
    }
  },
  components: {
    YShelf, BuyNum, YButton
  },
  watch: {
    $route: function (to, from) {
      if (to.fullPath.includes('/product/')) {
        let id = this.$route.params.productId
        this._productDet(id)
        this.userId = getStore('userId')
      }
    }
  },
  created: function () {
    let id = this.$route.params.productId
    this._productDet(id)
    this._productCommentCount(id)
    this.userId = getStore('userId')
  }
}
