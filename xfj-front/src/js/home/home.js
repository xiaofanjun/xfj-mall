import {productHome} from '/api/index.js'
import YShelf from '/components/shelf'
import product from '/components/product'
import mallGoods from '/components/mallGoods'
import {setStore, getStore} from '/utils/storage.js'

export default {
  data: function () {
    return {
      error: false,
      banner: [],
      mark: 0,
      bgOpt: {
        px: 0,
        py: 0,
        w: 0,
        h: 0
      },
      home: [],
      loading: true,
      notify: '1',
      dialogVisible: false,
      timer: ''
    }
  },
  methods: {
    autoPlay: function () {
      this.mark++
      if (this.mark > this.banner.length - 1) {
        // 当遍历到最后一张图片置零
        this.mark = 0
      }
    },
    play: function () {
      // 每2.5s自动切换
      this.timer = setInterval(this.autoPlay, 2500)
    },
    change: function (i) {
      this.mark = i
    },
    startTimer: function () {
      this.timer = setInterval(this.autoPlay, 2500)
    },
    stopTimer: function () {
      clearInterval(this.timer)
    },
    linkTo: function (item) {
      if (item.type === 0 || item.type === 2) {
        // 关联商品
        this.$router.push({
          path: '/product/' + item.productId
        })
      } else {
        // 完整链接
        window.location.href = item.fullUrl
      }
    },
    bgOver: function (e) {
      this.bgOpt.px = e.offsetLeft
      this.bgOpt.py = e.offsetTop
      this.bgOpt.w = e.offsetWidth
      this.bgOpt.h = e.offsetHeight
    },
    bgMove: function (dom, eve) {
      let bgOpt = this.bgOpt
      let X, Y
      let mouseX = eve.pageX - bgOpt.px
      let mouseY = eve.pageY - bgOpt.py
      if (mouseX > bgOpt.w / 2) {
        X = mouseX - (bgOpt.w / 2)
      } else {
        X = mouseX - (bgOpt.w / 2)
      }
      if (mouseY > bgOpt.h / 2) {
        Y = bgOpt.h / 2 - mouseY
      } else {
        Y = bgOpt.h / 2 - mouseY
      }
      dom.style['transform'] = `rotateY(${X / 50}deg) rotateX(${Y / 50}deg)`
      dom.style.transform = `rotateY(${X / 50}deg) rotateX(${Y / 50}deg)`
    },
    bgOut: function (dom) {
      dom.style['transform'] = 'rotateY(0deg) rotateX(0deg)'
      dom.style.transform = 'rotateY(0deg) rotateX(0deg)'
    },
    showNotify: function () {
      var value = getStore('notify')
      if (this.notify !== value) {
        this.dialogVisible = true
        setStore('notify', this.notify)
      }
    }
  },
  mounted: function () {
    productHome().then(res => {
      if (res.success === false) {
        this.error = true
        return
      }
      let data = res.result
      this.home = data
      this.loading = false
      for (let i = 0; i < data.length; i++) {
        if (data[i].type === 0) {
          this.banner = data[i].panelContentItems
        }
      }
    })
    this.showNotify()
  },
  created: function () {
    this.play()
  },
  components: {
    YShelf,
    product,
    mallGoods
  }
}
