export default {
  props: {
    open: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: '管理收货地址'
    }
  },
  methods: {
    close: function () {
      this.$emit('close')
    }
  }
}
