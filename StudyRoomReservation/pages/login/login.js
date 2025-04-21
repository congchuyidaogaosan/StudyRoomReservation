Page({
  data: {
    studentId: '',
    password: '',
    isSubmitting: false,
    captcha: '',
    captchaUrl: '',
    captchaKey: '',
    userInfo: null
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
      url: 'http://localhost:8083/Login/login',
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

   
  },

  handleWechatLogin(e) {
    if (e.detail.userInfo) {
      // 用户同意授权
      const userInfo = e.detail.userInfo
      
      wx.login({
        success: (res) => {
          if (res.code) {
            // 发送 code 到后台换取 token
            wx.request({
              url: 'YOUR_API_BASE_URL/api/login/wechat',
              method: 'POST',
              data: {
                code: res.code,
                userInfo: userInfo
              },
              success: (response) => {
                if (response.data.token) {
                  // 保存token
                  wx.setStorageSync('token', response.data.token)
                  // 保存用户信息
                  wx.setStorageSync('userInfo', userInfo)
                  
                  // 跳转到首页
                  wx.switchTab({
                    url: '/pages/index/index'
                  })
                } else {
                  wx.showToast({
                    title: '登录失败',
                    icon: 'none'
                  })
                }
              },
              fail: () => {
                wx.showToast({
                  title: '网络错误，请重试',
                  icon: 'none'
                })
              }
            })
          } else {
            wx.showToast({
              title: '登录失败',
              icon: 'none'
            })
          }
        }
      })
    } else {
      // 用户拒绝授权
      wx.showToast({
        title: '需要授权才能使用',
        icon: 'none'
      })
    }
  }
}) 