import {thank, thanksList} from '/api/index.js'
import YShelf from '/components/shelf'
import product from '/components/product'
import mallGoods from '/components/mallGoods'
import 'gitment/style/default.css'
import Gitment from 'gitment'

export default {
  data: function () {
    return {
      thankPanel: [],
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      loading: true
    }
  },
  methods: {
    handleSizeChange: function (val) {
      this.pageSize = val
      this._thanksList()
      this.loading = true
    },
    handleCurrentChange: function (val) {
      this.currentPage = val
      this._thanksList()
      this.loading = true
    },
    _thanksList: function () {
      let params = {
        params: {
          size: this.pageSize,
          page: this.currentPage
        }
      }
      thanksList(params).then(res => {
        this.loading = false
        this.tableData = res.result.data
        this.total = res.result.recordsTotal
      })
    },
    initGitment: function () {
      const gitment = new Gitment({
        id: '1',
        owner: 'Exrick',
        repo: 'xmall-comments',
        oauth: {
          client_id: 'd52e48ce99ee4e8fb412',
          client_secret: 'f4154230d52f3a7d6b7695cb0ae89fe76b76121d'
        }
      })
      gitment.render('comment')
    }
  },
  mounted: function () {
    thank().then(res => {
      let data = res.result
      this.thankPanel = data[0]
    })
    this._thanksList()
    this.initGitment()
  },
  components: {
    YShelf,
    product,
    mallGoods
  }
}
