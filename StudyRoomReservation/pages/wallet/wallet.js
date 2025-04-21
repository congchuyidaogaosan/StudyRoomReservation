// pages/wallet/wallet.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    transactions: [],
    showRecharge: false,
    rechargeAmount: '',
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    const userInfo = wx.getStorageSync('userInfo')
    this.setData({ userInfo })
    this.getTransactions()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },

  // 获取交易记录
  getTransactions() {
    wx.request({
      url: 'http://localhost:8083/wallet/logs',
      method: 'GET',
      data: {
        userId: this.data.userInfo.id
      },
      success: (res) => {
        if (res.data.code === 200) {
          console.log(res.data);
          this.setData({
            transactions: res.data.data
          })
        }
      }
    })
  },

  // 显示充值弹窗
  showRechargeDialog() {
    this.setData({
      showRecharge: true,
      rechargeAmount: ''
    })
  },

  // 隐藏充值弹窗
  hideRechargeDialog() {
    this.setData({
      showRecharge: false
    })
  },

  // 处理金额输入
  handleAmountInput(e) {
    this.setData({
      rechargeAmount: e.detail.value
    })
  },

  // 选择快捷金额
  selectAmount(e) {
    const amount = e.currentTarget.dataset.amount
    this.setData({
      rechargeAmount: amount
    })
  },

  // 处理充值
  handleRecharge() {
    if (!this.data.rechargeAmount) {
      wx.showToast({
        title: '请输入充值金额',
        icon: 'none'
      })
      return
    }

    // 直接更新本地用户信息
    let userInfo = this.data.userInfo
    userInfo.balance = (parseFloat(userInfo.balance || 0) + parseFloat(this.data.rechargeAmount)).toFixed(2)
    wx.setStorageSync('userInfo', userInfo)
    
    this.setData({
      userInfo,
      showRecharge: false
    })

    wx.showToast({
      title: '充值成功',
      icon: 'success'
    })

    // 可以添加一条充值记录到交易记录中
    const newTransaction = {
      type: '充值',
      amount: this.data.rechargeAmount,
      createTime: new Date().toLocaleString()
    }

    this.setData({
      transactions: [newTransaction, ...this.data.transactions]
    })
  }
})