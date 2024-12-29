Page({
  data: {
    roomId: '',
    seatId: '',
    roomName: '',
    seatRow: '',
    seatColumn: '',
    description: ''
  },

  onLoad(options) {
    this.setData({
      roomId: options.roomId,
      seatId: options.seatId,
      roomName: options.roomName,
      seatRow: options.seatRow,
      seatColumn: options.seatColumn
    })
  },

  handleInput(e) {
    this.setData({
      description: e.detail.value
    })
  },

  submitRepair() {
    if (!this.data.description.trim()) {
      wx.showToast({
        title: '请描述问题',
        icon: 'none'
      })
      return
    }

    wx.request({
      url: 'http://localhost:8081/Warranty/save',
      method: 'POST',
      data: {
        roomId: this.data.roomId,
        seatId: this.data.seatId,
        description: this.data.description,
        status: '待处理'
      },
      success: (res) => {
        if (res.data.code === 200) {
          wx.showToast({
            title: '报修成功',
            icon: 'success',
            duration: 2000,
            success: () => {
              setTimeout(() => {
                wx.navigateBack()
              }, 2000)
            }
          })
        } else {
          wx.showToast({
            title: res.data.message,
            icon: 'none'
          })
        }
      }
    })
  }
}) 