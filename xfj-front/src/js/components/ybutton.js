export default {
  props: {
    text: {
      type: [String, Number],
      default: '一颗小按钮'
    },
    inputType: {
      type: [String],
      default: 'button'
    },
    classStyle: {
      type: String,
      default: 'default-btn'
    }
  },
  methods: {
    btnClick: function (event) {
      this.$emit('btnClick', event)
    }
  }
}
