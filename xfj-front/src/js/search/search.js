import {getSearch} from '/api/goods.js'
import {recommend} from '/api/index.js'
import mallGoods from '/components/mallGoods'
import YButton from '/components/YButton'
import YShelf from '/components/shelf'
import YHeader from '/common/header'
import YFooter from '/common/footer'

export default {
  data: function () {
    return {
      goods: [],
      noResult: false,
      error: false,
      min: '',
      max: '',
      loading: true,
      searching: true,
      timer: null,
      sortType: 1,
      windowHeight: null,
      windowWidth: null,
      sort: '',
      recommendPanel: [],
      currentPage: 1,
      pageSize: 20,
      total: 0,
      key: ''
    }
  },
  methods: {
    handleSizeChange: function (val) {
      this.pageSize = val
      this._getSearch()
      this.loading = true
    },
    handleCurrentChange: function (val) {
      this.currentPage = val
      this._getSearch()
      this.loading = true
    },
    _getSearch: function () {
      let params = {
        key: this.key,
        size: this.pageSize,
        page: this.currentPage,
        sort: this.sort,
        priceGt: this.min,
        priceLte: this.max
      }
      getSearch(params).then(res => {
        if (res.success === true) {
          this.goods = res.result
          this.total = res.result.length
          this.noResult = false
          if (this.total === 0) {
            this.noResult = true
          }
          this.error = false
        } else {
          this.error = true
        }
        this.loading = false
        this.searching = false
      })
    },
    // 默认排序
    reset: function () {
      this.sortType = 1
      this.sort = ''
      this.currentPage = 1
      this.loading = true
      this._getSearch()
    },
    // 价格排序
    sortByPrice: function (v) {
      v === 1 ? this.sortType = 2 : this.sortType = 3
      this.sort = v
      this.currentPage = 1
      this.loading = true
      this._getSearch()
    }
  },
  created: function () {
  },
  mounted: function () {
    this.windowHeight = window.innerHeight
    this.windowWidth = window.innerWidth
    this.key = this.$route.query.key
    this._getSearch()
    recommend().then(res => {
      let data = res.result
      this.recommendPanel = data[0]
    })
  },
  components: {
    mallGoods,
    YButton,
    YShelf,
    YHeader,
    YFooter
  }
}
