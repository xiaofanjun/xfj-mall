import {getAllGoods} from '/api/goods.js'
import {recommend} from '/api/index.js'
import mallGoods from '/components/mallGoods'
import YButton from '/components/YButton'
import YShelf from '/components/shelf'

import Utils from '/utils'

export default {
  data: function () {
    return {
      goods: [],
      noResult: false,
      error: false,
      min: '',
      max: '',
      loading: true,
      timer: null,
      sortType: 1,
      windowHeight: null,
      windowWidth: null,
      recommendPanel: [],
      sort: '',
      currentPage: 1,
      total: 0,
      pageSize: 20
    }
  },
  methods: {
    isEmpty: Utils.isEmpty,
    handleSizeChange: function (val) {
      this.pageSize = val
      this._getAllGoods()
      this.loading = true
    },
    handleCurrentChange: function (val) {
      this.currentPage = val
      this._getAllGoods()
      this.loading = true
    },
    _getAllGoods: function () {
      let cid

      if (this.$route.path.includes('goods/cate')) {
        cid = this.$route.params.cateId
      } else {
        cid = this.$route.query.cid
      }

      if (this.min !== '') {
        this.min = Math.floor(this.min)
      }
      if (this.max !== '') {
        this.max = Math.floor(this.max)
      }
      let params = {
        params: {
          page: this.currentPage,
          size: this.pageSize,
          sort: this.sort,
          priceGt: this.min,
          priceLte: this.max,
          cid: cid
        }
      }
      getAllGoods(params).then(res => {
        if (res.success) {
          this.total = res.result.total
          this.goods = res.result.data
          this.noResult = false
          if (this.total === 0) {
            this.noResult = true
          }
          this.error = false
        } else {
          this.error = true
        }
        this.loading = false
      })
    },
    // 默认排序
    reset: function () {
      this.sortType = 1
      this.sort = ''
      this.currentPage = 1
      this.loading = true
      this._getAllGoods()
    },
    // 价格排序
    sortByPrice: function (v) {
      v === 1 ? this.sortType = 2 : this.sortType = 3
      this.sort = v
      this.currentPage = 1
      this.loading = true
      this._getAllGoods()
    }
  },
  watch: {
    $route: function (to, from) {
      if (to.fullPath.indexOf('/goods?cid=') >= 0) {
        this.cId = to.query.cid
        this._getAllGoods()
      }
      if (to.fullPath.includes('/goods/cate')) {
        this._getAllGoods()
      }
    }
  },
  created: function () {
  },
  mounted: function () {
    this.windowHeight = window.innerHeight
    this.windowWidth = window.innerWidth
    this._getAllGoods()
    recommend().then(res => {
      let data = res.result
      this.recommendPanel = data[0]
    })
  },
  components: {
    mallGoods,
    YButton,
    YShelf
  }
}
