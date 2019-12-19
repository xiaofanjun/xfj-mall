import {getCartList, cartEdit, editCheckAll, cartDel, delCartChecked} from '/api/goods'
import {mapMutations, mapState} from 'vuex'
import YButton from '/components/YButton'
import YHeader from '/common/header'
import YFooter from '/common/footer'
import BuyNum from '/components/buynum'
import {getStore} from '/utils/storage'

export default {
  data: function () {
    return {
      userId: 0,
      checkoutNow: '现在结算',
      submit: true
    }
  },
  computed: {
    ...mapState(
      ['cartList']
    ),
    // 是否全选
    checkAllFlag: function () {
      return this.checkedCount === this.cartList.length
    },
    // 勾选的数量
    checkedCount: function () {
      var i = 0
      this.cartList && this.cartList.forEach((item) => {
        if (item.checked === 'true') i++
      })
      return Number(i)
    },
    // 计算总数量
    totalNum: function () {
      var totalNum = 0
      this.cartList && this.cartList.forEach(item => {
        totalNum += (item.productNum)
      })
      return Number(totalNum)
    },
    // 选中的总价格
    checkPrice: function () {
      var totalPrice = 0
      this.cartList && this.cartList.forEach(item => {
        if (item.checked === 'true') {
          totalPrice += (item.productNum * item.salePrice)
        }
      })
      return totalPrice
    },
    // 选中的商品数量
    checkNum: function () {
      var checkNum = 0
      this.cartList && this.cartList.forEach(item => {
        if (item.checked === 'true') {
          checkNum += (item.productNum)
        }
      })
      return checkNum
    }
  },
  methods: {
    ...mapMutations([
      'INIT_BUYCART', 'EDIT_CART'
    ]),
    message: function (m) {
      this.$message.error({
        message: m
      })
    },
    goodsDetails: function (id) {
      window.open(window.location.origin + '#/product/' + id)
    },
    // 全选
    editCheckAll: function () {
      let checkAll = !this.checkAllFlag
      editCheckAll({userId: this.userId, checked: checkAll}).then(res => {
        this.EDIT_CART({checked: checkAll})
      })
    },
    // 修改购物车
    _cartEdit: function (userId, productId, productNum, checked) {
      cartEdit(
        {
          userId,
          productId,
          productNum,
          checked
        }
      ).then(res => {
        if (res.success === true) {
          this.EDIT_CART(
            {
              productId,
              checked,
              productNum
            }
          )
        }
      })
    },
    // 修改购物车
    editCart: function (type, item) {
      if (type && item) {
        let checked = item.checked
        let productId = item.productId
        let productNum = item.productNum
        // 勾选
        if (type === 'check') {
          let newChecked = checked === 'true' ? 'false' : 'true'
          this._cartEdit(this.userId, productId, productNum, newChecked)
        }
      } else {
        console.log('缺少所需参数')
      }
    },
    EditNum: function (productNum, productId, checked) { // 数量
      this._cartEdit(this.userId, productId, productNum, checked)
    },
    // 删除整条购物车
    cartdel: function (productId) {
      cartDel({userId: this.userId, productId: productId}).then(res => {
        this.EDIT_CART({productId})
      })
    },
    checkout: function () {
      this.checkoutNow = '结算中...'
      this.submit = false
      this.$router.push({path: 'checkout'})
    },
    delChecked: function () {
      getCartList().then(res => {
        if (res.success === true) {
          res.result.forEach(item => {
            if (item.checked === 'true') {
              let productId = item.productId
              this.EDIT_CART({productId})
            }
          })
        }
      })
      delCartChecked({userId: this.userId}).then(res => {
        if (res.success !== true) {
          this.message('删除失败')
        }
      })
    }
  },
  mounted: function () {
    this.userId = getStore('userId')
    this.INIT_BUYCART()
  },
  components: {
    YButton,
    YHeader,
    YFooter,
    BuyNum
  }
}
