import YButton from '/components/YButton'
import {upload} from '/api/index'
import YShelf from '/components/shelf'
import vueCropper from 'vue-cropper'
import {mapState, mapMutations} from 'vuex'
import {getStore} from '/utils/storage'

export default {
  data: function () {
    return {
      imgSrc: '',
      editAvatarShow: false,
      cropContext: '',
      cropperImg: '',
      previews: {},
      option: {
        img: '',
        zoom: 0
      },
      fixedNumber: [1, 1],
      example2: {
        info: true,
        size: 1,
        canScale: false,
        autoCrop: true,
        // 只有自动截图开启 宽度高度才生效
        autoCropWidth: 300,
        autoCropHeight: 250,
        // 开启宽度和高度比例
        fixed: true
      },
      userId: '',
      token: ''
    }
  },
  computed: {// 有个监控的作用,这整个作用就是映射userinfo对象的各个属性，并监控着
    ...mapState(['userInfo'])
  },
  methods: {
    ...mapMutations([
      'RECORD_USERINFO'
    ]),
    message: function (m) {
      this.$message(m)
    },
    messageSuccess: function (m) {
      this.$message({
        message: m,
        type: 'success'
      })
    },
    messageFail: function (m) {
      this.$message.error({
        message: m
      })
    },
    upimg: function (e) {
      var file = e.target.files[0]
      if (file.size > 1048576) {
        this.messageFail('图片大小不得超过1Mb')
        return false
      }
      if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
        this.messageFail('图片类型仅支持.gif,jpeg,jpg,png,bmp')
        return false
      }
      var reader = new FileReader()
      reader.readAsDataURL(file)
      reader.onload = (e) => {
        this.option.img = e.target.result
      }
    },
    cropper: function () {
      this.message('上传中...')
      if (this.option.img) {
        this.$refs.cropper.getCropData((data) => {
          this.imgSrc = data
          upload({userId: this.userId, token: this.token, imageData: data}).then(res => {
            if (res.success === true) {
              let path = res.result
              let info = this.userInfo
              info.file = path + '?' + new Date().getTime()
              this.RECORD_USERINFO({info: info})
              this.editAvatarShow = false
              this.messageSuccess('上传成功')
            } else {
              this.messageFail(res.message)
            }
          })
        })
      } else {
        this.messageFail('别玩我啊 先选照骗')
      }
    },
    editAvatar: function () {
      this.editAvatarShow = true
    },
    realTime: function (data) {
      this.previews = data
      let w = 100 / data.w
      this.option.zoom = w
    }
  },
  created: function () {
    this.userId = getStore('userId')
    this.token = getStore('token')
  },
  components: {
    YButton,
    YShelf,
    vueCropper
  }
}
