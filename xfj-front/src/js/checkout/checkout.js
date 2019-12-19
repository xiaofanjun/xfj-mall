import {getCartList, addressList, addressUpdate, addressAdd, addressDel, productDet, submitOrder} from '/api/goods'
import YShelf from '/components/shelf'
import YButton from '/components/YButton'
import YPopup from '/components/popup'
import YHeader from '/common/header'
import YFooter from '/common/footer'
import cityMap from '/utils/area/city'
import provinceList from '/utils/area/province'
import districtMap from '/utils/area/country'
import Util from '/utils'

export default {
  data: function () {
    return {
      cartList: [],
      addList: [],
      addressId: '0',
      popupOpen: false,
      popupTitle: '管理收货地址',
      num: '', // 立刻购买
      productId: '',
      msg: {
        addressId: '',
        userName: '',
        tel: '',
        streetName: '',
        isDefault: false
      },
      userName: '',
      tel: '',
      streetName: '',
      orderTotal: 0,
      submit: false,
      submitOrder: '提交订单',
      // data
      provinceList: [],
      cityList: [],
      districtList: [],

      city: '', // 市
      province: '', // 省
      district: '', // 区
      cityId: null,
      provinceId: null,
      districtId: null,
      address: ''  // 街道
    }
  },
  computed: {
    btnHighlight: function () {
      let msg = this.msg
      return msg.userName && msg.tel && msg.streetName
    },
    // 选中的总价格
    checkPrice: function () {
      let totalPrice = 0
      this.cartList && this.cartList.forEach(item => {
        if (item.checked) {
          totalPrice += (item.productNum * item.salePrice)
        }
      })
      this.orderTotal = totalPrice
      return totalPrice
    }
  },
  methods: {
    message: function (m) {
      this.$message.error({
        message: m
      })
    },
    _handleProvinceChange: function (provinceId) {
      if (!provinceId) {
        return
      }
      this.province = provinceList.find(p => p.id === provinceId).name
      this.cityId = null
      this.districtId = null
      this.districtList = []

      let cityList = cityMap[provinceId]
      this.cityList = cityList || []
      this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
    },
    _handleCityChange: function (cityId) {
      if (!cityId) {
        return
      }
      this.city = this.cityList.find(c => c.id === cityId).name
      this.districtId = null

      let districtList = districtMap[cityId]
      this.districtList = districtList || []
      this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
    },
    _handleDistrictChange: function (districtId) {
      if (!districtId) {
        return
      }
      this.district = this.districtList.find(d => d.id === districtId).name
      this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
    },
    _handleAddressChange: function () {
      this.msg.streetName = this.province + '-' + this.city + '-' + this.district + '-' + this.address
      // console.log('%c[addressList-_handleAddressChange]', 'color: #63ADD1', this.msg.streetName)
    },
    goodsDetails: function (id) {
      window.open(window.location.origin + '#/product/' + id)
    },
    _getCartList: function () {
      getCartList().then(res => {
        this.cartList = res.result
      })
    },
    _addressList: function () {
      addressList().then(res => {
        let data = res.result
        if (data.length) {
          this.addList = data
          this.addressId = data[0].addressId || '1'
          this.userName = data[0].userName
          this.tel = data[0].tel
          this.streetName = data[0].streetName
        } else {
          this.addList = []
        }
      })
    },
    _addressUpdate: function (params) {
      addressUpdate(params).then(res => {
        this._addressList()
      })
    },
    _addressAdd: function (params) {
      addressAdd(params).then(res => {
        if (res.success === true) {
          this._addressList()
        } else {
          this.message(res.message)
        }
      })
    },
    _addressDel: function (params) {
      addressDel(params).then(res => {
        this._addressList()
      })
    },
    // 提交订单后跳转付款页面
    _submitOrder: function () {
      this.submitOrder = '提交订单中...'
      this.submit = true
      var array = []
      if (this.addressId === '0') {
        this.message('请选择收货地址')
        this.submitOrder = '提交订单'
        this.submit = false
        return
      }
      if (this.cartList.length === 0) {
        this.message('非法操作')
        this.submitOrder = '提交订单'
        this.submit = false
        return
      }
      for (var i = 0; i < this.cartList.length; i++) {
        if (this.cartList[i].checked) {
          array.push(this.cartList[i])
        }
      }
      let params = {
        tel: this.tel,
        userName: this.userName,
        streetName: this.streetName,
        cartProductDtoList: array,
        addressId: this.addressId,
        orderTotal: this.orderTotal
      }
      submitOrder(params).then(res => {
        if (res.success === true) {
          this.payment(res.result)
        } else {
          this.message(res.message)
          this.submitOrder = '提交订单'
          this.submit = false
        }
      })
    },
    // 付款
    payment: function (orderId) {
      // 需要拿到地址id
      this.$router.push({
        path: '/order/payment/' + orderId
      })
    },
    // 选择地址
    chooseAddress: function (addressId, userName, tel, streetName) {
      this.addressId = addressId
      this.userName = userName
      this.tel = tel
      this.streetName = streetName
    },
    // 修改
    update: function (item) {
      this.popupOpen = true
      if (item) {
        this.popupTitle = '管理收货地址'
        this.msg.userName = item.userName
        this.msg.tel = item.tel
        this.msg.streetName = item.streetName
        this.msg.isDefault = item.isDefault
        this.msg.addressId = item.addressId
        // init 地址选择框
        this._initAddressSelect(item.streetName)
      } else {
        this.popupTitle = '新增收货地址'
        this.msg.userName = ''
        this.msg.tel = ''
        this.msg.streetName = ''
        this.msg.isDefault = false
        this.msg.addressId = ''
      }
    },
    // 保存
    save: function (p) {
      this.popupOpen = false
      if (p.addressId) {
        this._addressUpdate(p)
      } else {
        delete p.addressId
        this._addressAdd(p)
      }
    },
    // 删除
    del: function (addressId) {
      this._addressDel({addressId})
    },
    _productDet: function (productId) {
      productDet({params: {productId}}).then(res => {
        let item = res.result
        item.checked = 'true'
        item.productImg = item.productImageBig
        item.productNum = this.num
        item.productPrice = item.salePrice
        this.cartList.push(item)
      })
    },
    _clearAddressSelect: function () {
      this.provinceId = null
      this.cityId = null
      this.districtId = null
    },
    _initAddressSelect: function (streetName) {
      let addressList = !Util.isEmpty(streetName) ? streetName.split('-') : []
      if (addressList.length >= 3) {
        this.province = addressList[0]
        this.city = addressList[1]
        this.district = addressList[2]
        this.address = addressList.length === 4 ? addressList[3] : ''

        // 查找对应省份
        let provinceObj = provinceList.find(p => p.name === this.province)
        if (!Util.isEmpty(provinceObj)) {
          this.provinceId = provinceObj.id
          this.cityList = cityMap[this.provinceId]
          // 查找对应城市
          let cityObj = cityMap[this.provinceId].find(c => c.name === this.city)
          if (!Util.isEmpty(cityObj)) {
            this.cityId = cityObj.id
            this.districtList = districtMap[this.cityId]
            // 查找对应地区
            let districtObj = districtMap[this.cityId].find(d => d.name === this.district)
            if (!Util.isEmpty(districtObj)) {
              this.districtId = districtObj.id
            }
          }
        }
      } else {
        // 老数据直接重置
        this.provinceId = null
        this.cityId = null
        this.districtId = null
      }
    }
  },
  filters: {
    addressFilter: function (streetName) {
      return streetName ? streetName.replace(new RegExp('-', 'g'), '') : ''
    }
  },
  created: function () {
    let query = this.$route.params
    if (query.productId && query.num) {
      this.productId = query.productId
      this.num = query.num
      this._productDet(this.productId)
    } else {
      this._getCartList()
    }
    this.provinceList = provinceList
    this.cityList = []
    this.district = []
    this._addressList()
  },
  components: {
    YShelf,
    YButton,
    YPopup,
    YHeader,
    YFooter
  }
}
