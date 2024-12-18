Page({
  data: {
    records: [],
    tabs: ['全部', '未开始', '进行中', '已完成'],
    currentTab: 0,
    userInfo: {},
  },

  onLoad() {
    // 检查是否已登录
    const userInfo = wx.getStorageSync('userInfo')
    console.log(userInfo)
    if (!userInfo) {
      wx.redirectTo({
        url: '/pages/login/login'
      })
      return
    } else {
      this.setData({ userInfo: userInfo })
    }
    this.getRecords()
  },

  onShow() {
    // 每次显示页面时刷新记录
    this.getRecords()
  },

  // 切换标签
  changeTab(e) {
    const index = e.currentTarget.dataset.index
    this.setData({
      currentTab: index
    })
    // 根据标签筛选记录
    this.getRecords(index)
  },

  // 获取记录列表
  getRecords(tabIndex = 0) {
    let that = this;

    // 根据标签筛选



    if (tabIndex === 1) {
      this.data.status = '未开始'
    } else if (tabIndex === 2) {
      this.data.status = '进行中'
    } else if (tabIndex === 3) {
      this.data.status = '已完成'
    } else {
      this.data.status = ''
    }
    let data = { kehuid: this.data.userInfo.id, status: this.data.status };


    wx.request({
      url: 'http://localhost:8081/LogBill/list',
      data: data,
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        if (res.data.code === 200) {
          that.setData({
            records: res.data.data
          })
        }
      }
    })





  }
}) 