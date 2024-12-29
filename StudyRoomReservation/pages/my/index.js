Page({
  data: {
    userInfo: {
      avatarUrl: '',
      nickName: ''
    },
    hasUserInfo: false
  },

  onLoad() {
    // 检查是否已有用户信息
    const userInfo = wx.getStorageSync('userInfo')
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
    
    this.setData({
      hasUserInfo: true,
      userInfo: userInfo
    });
    wx.setStorageSync('userInfo', userInfo);
  },

  // 处理昵称输入
  onInputNickname(e) {
    const nickName = e.detail.value;
    const userInfo = this.data.userInfo;
    userInfo.nickName = nickName;
    
    this.setData({
      userInfo: userInfo,
      hasUserInfo: true
    });
    
    wx.setStorageSync('userInfo', userInfo);
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