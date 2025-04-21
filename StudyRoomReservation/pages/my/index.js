Page({
  data: {
    userInfo: {
      avatarUrl: '',
      nickName: ''
    },
    hasUserInfo: false
  },
  
  onShow() {
    // 获取本地存储的用户ID
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo && userInfo.id) {
      // 从服务器获取最新的用户信息
      wx.request({
        url: `http://localhost:8083/Kehu/info/${userInfo.id}`,
        method: 'GET',
        success: (res) => {
          if (res.data.code === 200 && res.data.data) {
            // 更新本地存储和页面数据
            wx.setStorageSync('userInfo', res.data.data)
            this.setData({
              userInfo: res.data.data,
              hasUserInfo: true
            })
          }
        },
        fail: () => {
          // 如果请求失败，使用本地缓存的数据
          this.setData({
            userInfo: userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  onLoad() {
    // 检查是否已有用户信息
    const userInfo = wx.getStorageSync('userInfo')
    console.log(userInfo);
    if (userInfo) {
      this.setData({
        userInfo: userInfo,
        hasUserInfo: true
      })
    }
  },

  // 获取用户头像
  onChooseAvatar(e) {
    const { avatarUrl } = e.detail;
    const userInfo = this.data.userInfo;
    userInfo.avatarUrl = avatarUrl;
    userInfo.photo = avatarUrl;
    this.setData({
      hasUserInfo: true,
      userInfo: userInfo
    });
    wx.request({
      url: 'http://localhost:8083/Kehu/update',
      method: 'POST',
      data: userInfo,
      success: (res) => {
        wx.setStorageSync('userInfo', res.data.data)
      }
    })
  },

  // 处理昵称输入
  onInputNickname(e) {
    const nickName = e.detail.value;
    const userInfo = this.data.userInfo;
    userInfo.nickName = nickName;
    userInfo.username = nickName;
    
    this.setData({
      userInfo: userInfo,
      hasUserInfo: true
    });
    wx.request({
      url: 'http://localhost:8083/Kehu/update',
      method: 'POST',
      data: userInfo,
      success: (res) => {
        wx.setStorageSync('userInfo', res.data.data)
      }
    })
  },

  // 跳转到记录页面
  goToRecords() {
    wx.navigateTo({
      url: '/pages/records/records'
    })
  },
  gooutput() {
 
    wx.setStorageSync('isLoggedIn', false)
    wx.redirectTo({
      url: '/pages/login/login'
    })
  },

  // 跳转到钱包页面
  goToWallet() {
    wx.navigateTo({
      url: '/pages/wallet/wallet'
    })
  }
}) 