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
  handleClick(e) {
    let that = this;
    const id = e.currentTarget.dataset.index;
    console.log(id)

    let date = {
      id: id,
      status: '进行中',
    }

    wx.request({
      url: 'http://localhost:8083/LogBill/update',
      data: date,
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        if (res.data.code === 200) {
          that.getRecords();
        }
      }
    })

  },
  handleNoClick(e) {
    let that = this;
    const index = e.currentTarget.dataset;
    console.log(index.index)
    let mydate = {
      id: index.index,
      status: '手动退订',
    }
    wx.request({
      url: 'http://localhost:8083/LogBill/update',
      data: mydate,
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success(res) {
        if (res.data.code === 200) {
          that.getRecords();
        }
      }
    })

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
      url: 'http://localhost:8083/LogBill/list',
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





  },

  handleRepair(e) {
    const item = e.currentTarget.dataset.item
    wx.showModal({
      title: '报修',
      content: '确认要报修该座位吗？',
      success: (res) => {
        if (res.confirm) {
          wx.navigateTo({
            url: `/pages/repair-form/repair-form?orderId=${item.id}&roomId=${item.roomId}&seatId=${item.seatsId}&roomName=${item.nick}&seatRow=${item.seatsrow}&seatColumn=${item.seatcolunm}`
          })
        }
      }
    })
  }
}) 