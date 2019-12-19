export default {
  props: {
    num: {
      type: [Number],
      default: 1
    },
    id: {
      type: [Number, String]
    },
    checked: {
      type: [String, Boolean]
    },
    limit: {
      type: Number,
      default: 10
    }
  },
  computed: {},
  data: function () {
    return {
      show: true,
      flag: true,
      Num: this.num,
      numList: []
    }
  },
  methods: {
    up: function () {
      if (this.flag && this.Num < this.limit) {
        this.ani('up')
      }
      return false
    },
    down: function () {
      if (this.flag && this.Num > 1) {
        this.ani('down')
      }
      return false
    },
    blur: function () {
      this.Num = this.Num > this.limit ? Number(this.limit) : Number(this.Num)
      this.$emit('edit-num', this.Num, this.id, this.checked)
    },
    ani: function (opera) {
      this.flag = false
      let n = this.Num
      this.numList = [n - 1, n, n + 1]
      let ul = this.$refs.ul
      let ulStyle = ul.style
      this.show = false
      ulStyle.zIndex = '99'
      ulStyle.transition = 'all .2s ease-out'
      if (opera === 'up') {
        this.Num++
        ulStyle.transform = 'translateY(-54px)'
      } else {
        this.Num--
        ulStyle.transform = `translateY(-18px)`
      }
      ul.addEventListener('transitionend', () => {
        this.show = true
        this.domInt(ulStyle)
        this.flag = true
      })
      ul.addEventListener('webkitAnimationEnd', () => {
        this.show = true
        this.domInt(ulStyle)
        this.flag = true
      })
      this.$emit('edit-num', this.Num, this.id, this.checked)
    },
    domInt: function (domStyle) {
      domStyle.zIndex = '1'
      domStyle.transition = 'all 0s'
      domStyle.transform = 'translateY(-36px)' // 回到原位
    }
  }
}
