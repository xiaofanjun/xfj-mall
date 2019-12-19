import YButton from '/components/YButton'
import {mapMutations, mapState} from 'vuex'
import {getQuickSearch, getCartList, cartDel, getAllGoodsCategories} from '/api/goods'
import {loginOut, navList, recommend} from '/api/index'
import {setStore, getStore, removeStore} from '/utils/storage'

// import store from '../store/'
import 'element-ui/lib/theme-default/index.css'

export default {
  data: function () {
    return {
      // visible
      showCateDiv: false,
      showRecommend: false,  // 产品分类是否显示 推荐商品 （手机板块显示推荐商品）

      // data
      curCateList: [],
      curItem: null,
      recommendPanel: null,

      goodsCateList: [], // 产品分类列表
      goodsCateTree: {}, // 树级机构的产品分类列表

      user: {},
      // 查询数据库的商品
      st: false,
      // 头部购物车显示
      cartShow: false,
      positionL: 0,
      positionT: 0,
      timerCartShow: null, // 定时隐藏购物车
      input: '',
      choosePage: -1,
      searchResults: [],
      timeout: null,
      token: '',
      navList: []
    }
  },
  computed: {
    ...mapState([
      'cartList', 'login', 'receiveInCart', 'showCart', 'userInfo'
    ]),
    // 计算价格
    totalPrice: function () {
      var totalPrice = 0
      this.cartList && this.cartList.forEach(item => {
        totalPrice += (item.productNum * item.salePrice)
      })
      return totalPrice
    },
    // 计算数量
    totalNum: function () {
      var totalNum = 0
      this.cartList && this.cartList.forEach(item => {
        totalNum += (item.productNum)
      })
      return totalNum
    }
  },
  methods: {
    ...mapMutations(['ADD_CART', 'INIT_BUYCART', 'ADD_ANIMATION', 'SHOW_CART', 'REDUCE_CART', 'RECORD_USERINFO', 'EDIT_CART']),
    handleNavItemMouseEnter: function (item, index) {
      let cateName = item.picUrl
      this.showRecommend = false
      if (cateName === '手机') {
        this.showCateDiv = false
        this.showRecommend = true
        return
      }
      let cate = this.goodsCateTree[cateName]
      if (cate) {
        this.curCateList = cate.children
        this.showCateDiv = true
      } else {
        this.showCateDiv = false
      }
    },
    handleNavSubMouseLeave: function () {
      this.showCateDiv = false
      this.showRecommend = false
    },
    handleIconClick: function (ev) {
      if (this.$route.path === '/search') {
        this.$router.push({
          path: '/refreshsearch',
          query: {
            key: this.input
          }
        })
      } else {
        this.$router.push({
          path: '/search',
          query: {
            key: this.input
          }
        })
      }
    },
    goGoodsCatePage: function (childCateItem) {
      let {id} = childCateItem
      this.$router.push('/goods/cate/' + id)
      this.showCateDiv = false
    },
    goRecommendGoodsDetail: function (item) {
      let {productId} = item
      this.$router.push('/product/' + productId)
      this.showRecommend = false
    },
    showError: function (m) {
      this.$message.error({
        message: m
      })
    },
    // 导航栏文字样式改变
    changePage: function (v) {
      this.choosePage = v
    },
    changGoods: function (v, item) {
      this.changePage(v)
      if (v === -1) {
        this.$router.push({
          path: '/'
        })
      } else if (v === -2) {
        this.$router.push({
          path: '/refreshgoods'
        })
      }
    },
    // 搜索框提示
    loadAll: function () {
      let params = {
        params: {
          key: this.input
        }
      }
      getQuickSearch(params).then(response => {
        if (response === null || response === '') {
          return
        }
        if (response.error) {
          this.showError(response.error.reason)
          return
        }
        var array = []
        var maxSize = 5
        if (response.result.length <= 5) {
          maxSize = response.result.length
        }
        for (var i = 0; i < maxSize; i++) {
          var obj = {}
          obj.value = response.result[i].productName
          array.push(obj)
        }
        if (array.length !== 0) {
          this.searchResults = array
        } else {
          this.searchResults = []
        }
      })
    },
    querySearchAsync: function (queryString, cb) {
      if (this.input === undefined) {
        cb([])
        return
      }
      this.input = this.input.trim()
      if (this.input === '') {
        cb([])
        return
      } else {
        this.loadAll()
        setTimeout(() => {
          cb(this.searchResults)
        }, 300)
      }
    },
    handleSelect: function (item) {
      this.input = item.value
    },
    // 购物车显示
    cartShowState: function (state) {
      this.SHOW_CART({showCart: state})
    },
    // 登陆时获取一次购物车商品
    _getCartList: function () {
      getCartList({userId: getStore('userId')}).then(res => {
        if (res.success === true) {
          setStore('buyCart', res.result)
        }
        // 重新初始化一次本地数据
      }).then(this.INIT_BUYCART)
    },
    // 删除商品
    delGoods: function (productId) {
      if (this.login) { // 登陆了
        cartDel({userId: getStore('userId'), productId}).then(res => {
          this.EDIT_CART({productId})
        })
      } else {
        this.EDIT_CART({productId})
      }
    },
    toCart: function () {
      this.$router.push({path: '/cart'})
    },
    // 控制顶部
    navFixed: function () {
      const fixedPages = ['goods/*', '/home', 'product/*']
      let path = this.$route.path
      let rs = false
      fixedPages.forEach(function (val, index, fixedPages) {
        let exp = new RegExp(val)
        if (path.match(exp)) {
          rs = true
          return
        } else {
          return
        }
      })
      if (rs) {
        var st = document.documentElement.scrollTop || document.body.scrollTop
        st >= 100 ? this.st = true : this.st = false
        // 计算小圆当前位置
        let num = document.querySelector('.num')
        this.positionL = num.getBoundingClientRect().left
        this.positionT = num.getBoundingClientRect().top
        this.ADD_ANIMATION({cartPositionL: this.positionL, cartPositionT: this.positionT})
      }
    },
    // 退出登陆
    _loginOut: function () {
      let params = {
        params: {
          token: this.token
        }
      }
      loginOut(params).then(res => {
        removeStore('buyCart')
        window.location.href = '/'
      })
    },
    // 通过路由改变导航文字样式
    getPage: function () {
      let path = this.$route.path
      // let fullPath = this.$route.fullPath
      if (path === '/' || path === '/home') {
        this.changePage(-1)
      } else if (path === '/goods') {
        this.changePage(-2)
      } else {
        this.changePage(0)
      }
    },
    openProduct: function (productId) {
      window.open('//' + window.location.host + '/#/product/' + productId)
    },
    _getNavList: function () {
      navList().then(res => {
        this.navList = res.result
      })
    },
    _getGoodsCategoryList: function () {
      getAllGoodsCategories().then(res => {
        this.goodsCateList = res.result
        this.goodsCateTree = this._buildCateTree(this.goodsCateList)
      })
    },
    _buildCateTree: function (goodsCateList) {
      let parentCateList = goodsCateList.filter(cate => cate.isParent) || []
      let tree = {}
      if (parentCateList) {
        // 遍历父级产品分类
        for (let parentCate of parentCateList) {
          let parentCateId = parentCate.id // 父级分类id
          let parentCateName = parentCate.name // 分类名称

          let childCateList = goodsCateList
            .filter(cate => cate.parentId === parentCateId && !cate.isParent)  // 获取当前父级父类对应的二级子分类
            .map(cate => {
              let childCateId = cate.id
              // 查询三级分类
              let children = goodsCateList.filter(cate => cate.parentId === childCateId && !cate.isParent)
              // 重新构造子分类对象
              return {
                ...cate,
                children: children
              }
            })
          tree[parentCateName] = {
            ...parentCate,
            children: childCateList
          }
        }
      }
      return tree
    },
    _getRecommendGoodsAsPhone: function () {
      recommend().then(res => {
        let data = res.result
        this.recommendPanel = data[0]
      })
    }
  },
  mounted: function () {
    this._getNavList()
    this._getGoodsCategoryList()
    this._getRecommendGoodsAsPhone()
    this.token = getStore('token')
    if (this.login) {
      this._getCartList()
    } else {
      this.INIT_BUYCART()
    }
    this.navFixed()
    this.getPage()
    window.addEventListener('scroll', this.navFixed)
    window.addEventListener('resize', this.navFixed)
    if (typeof (this.$route.query.key) !== undefined) {
      this.input = this.$route.query.key
    }
  },
  components: {
    YButton
  }
}
