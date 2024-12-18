Page({
  data: {
    studentId: '',
    password: '',
    isSubmitting: false,
    captcha: '',
    captchaUrl: '',
    captchaKey: ''
  },

  onLoad() {
    this.refreshCaptcha()
  },

  handleStudentIdInput(e) {
    this.setData({
      studentId: e.detail.value
    })
  },

  handlePasswordInput(e) {
    this.setData({
      password: e.detail.value
    })
  },

  handleCaptchaInput(e) {
    this.setData({
      captcha: e.detail.value
    })
  },

  refreshCaptcha() {
    this.setData({ captchaUrl: '' })

    setTimeout(() => {
      this.setData({
        captchaUrl: 'https://example.com/captcha.jpg',
        captchaKey: 'random_key_' + Date.now()
      })
    }, 500)
  },

  handleLogin() {

    let that = this;

    // 表单验证
    if (!this.data.studentId.trim()) {
      wx.showToast({
        title: '请输入学号',
        icon: 'none'
      })
      return
    }

    if (!this.data.password.trim()) {
      wx.showToast({
        title: '请输入密码',
        icon: 'none'
      })
      return
    }

    if (!this.data.captcha.trim()) {
      wx.showToast({
        title: '请输入验证码',
        icon: 'none'
      })
      return
    }

    // 防止重复提交
    if (this.data.isSubmitting) return

    this.setData({ isSubmitting: true })

    // 这里添加登录逻辑
    wx.showLoading({
      title: '登录中...'
    })

    if (this.data.captcha !== '1234') {
      wx.showToast({
        title: '验证码错误',
        icon: 'none'
      })
      this.refreshCaptcha()
      return
    }



    let data = { password: this.data.password, username: this.data.studentId };
    console.log(data)
    wx.request({
      url: 'http://localhost:8081/Login/login',
      data: data,
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success(res) {
      
        wx.hideLoading()
        if (res.data.code === 200) {
          that.setData({ isSubmitting: false })
          wx.setStorageSync('isLoggedIn', true)
          wx.setStorageSync('userInfo', res.data.data)
          wx.switchTab({
            url: '/pages/index/index'
          })
        } else {
          wx.showToast({
            title: res.data.message,
            icon: 'none'
          })
          return;
        }


      }
    })

   
  }
}) 