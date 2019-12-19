import YShelf from '/components/shelf'
import YButton from '/components/YButton'

export default {
  data: function () {
    return {
      price: 10
    }
  },
  components: {
    YShelf,
    YButton
  },
  created: function () {
    this.price = this.$route.query.price
  }
}
