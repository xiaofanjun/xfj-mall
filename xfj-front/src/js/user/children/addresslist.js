import {addressList, addressUpdate, addressAdd, addressDel} from '/api/goods'
import YButton from '/components/YButton'
import YPopup from '/components/popup'
import YShelf from '/components/shelf'

import cityMap from '/utils/area/city'
import provinceList from '/utils/area/province'
import districtMap from '/utils/area/country'
import Util from '/utils'

export default {
  data: function () {
    return {
      addList: [],
      popupOpen: false,
      popupTitle: '管理收货地址',
      msg: {
        addressId: '',
        userName: '',
        tel: '',
        streetName: '',
        _Default: false
      },
      userId: '',

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
    _addressList: function () {
      addressList().then(res => {
        let data = res.result
        if (data.length) {
          this.addList = res.result
          this.addressId = res.result[0].addressId || '1'
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
    changeDef: function (item) {
      if (!item._Default) {
        item._Default = true
        this._addressUpdate(item)
      }
      return false
    },
    // 保存
    save: function (p) {
//        alert(p._Default)
      this.popupOpen = false
      if (p.addressId) {
        this._addressUpdate(p)
      } else {
        delete p.addressId
        this._addressAdd(p)
      }
    },
    // 删除
    del: function (addressId, i) {
      addressDel({addressId: addressId}).then(res => {
        if (res.success === true) {
          this.addList.splice(i, 1)
        } else {
          this.message('删除失败')
        }
      })
    },
    // 修改
    update: function (item) {
      if (item) {
        this.popupTitle = '管理收货地址'
        this.msg.userName = item.userName
        this.msg.tel = item.tel
        this.msg.streetName = item.streetName
        this.msg._Default = item._Default
        this.msg.addressId = item.addressId
        // init 地址选择框
        this._initAddressSelect(item.streetName)
        this.popupOpen = true
      }
    },
    addNewAddress: function () {
      this.popupTitle = '新增收货地址'
      this.msg.userName = ''
      this.msg.tel = ''
      this.msg.streetName = ''
      this.msg._Default = false
      this.msg.addressId = ''

      this._clearAddressSelect()
      this.popupOpen = true
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
    this.provinceList = provinceList
    this.cityList = []
    this.district = []
    this._addressList()
  },
  components: {
    YButton,
    YPopup,
    YShelf
  }
}
