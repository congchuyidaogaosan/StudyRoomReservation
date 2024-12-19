Page({
  data: {
    currentDate: '',
    minDate: '',
    maxDate: '',
    seats: [],
    selectedSeats: [],
    selectedTimeIndex: -1,
    timeSlots: [
      { id: 1, time: '08:00-10:00', selected: false, disabled: false },
      { id: 2, time: '10:00-12:00', selected: false, disabled: false },
      { id: 3, time: '14:00-16:00', selected: false, disabled: false },
      { id: 4, time: '16:00-18:00', selected: false, disabled: false },
      { id: 5, time: '19:00-21:00', selected: false, disabled: false }
    ],
    floors: [],
    floorIndex: -1,
    flagseat: -1,
    flagSeatId: -1,
    noseats: [],
  },

  onLoad() {
    // 设置日期范围
    const today = new Date();
    const maxDate = new Date();
    maxDate.setDate(today.getDate() + 7);

    this.setData({
      minDate: this.formatDate(today),
      maxDate: this.formatDate(maxDate),
      currentDate: this.formatDate(today)
    });

    // 如果是当天，禁用已过去的时间段
    if (this.data.currentDate === this.formatDate(new Date())) {
      const now = new Date();
      const currentHour = now.getHours();
      
      const timeSlots = this.data.timeSlots.map(slot => {
        const timeStart = parseInt(slot.time.split(':')[0]);
        return {
          ...slot,
          disabled: timeStart <= currentHour
        };
      });
      
      this.setData({ timeSlots });
    }

    this.initRoom();
    this.initTime();
    this.initSeats();
  },
  initRoom() {
    let that = this;
    wx.request({
      url: 'http://localhost:8081/StudyRooms/list',
      method: 'POST',
      success(res) {
        console.log(res.data.data)

        that.setData({
          floors: res.data.data
        })
      }
    })

  },
  initTime() {
    let that = this;
    wx.request({
      url: 'http://localhost:8081/TimeType/list',
      method: 'POST',
      success(res) {
        console.log(res.data.data)

        that.setData({
          timeSlots: res.data.data
        })
      }
    })
  },
  formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  },

  handleDateChange(e) {
    const selectedDate = e.detail.value;
    const today = this.formatDate(new Date());
    
    // 检查是否选择了过去的日期
    if (selectedDate < today) {
      wx.showToast({
        title: '不能选择过去的日期',
        icon: 'none'
      });
      return;
    }
    
    this.setData({
      currentDate: selectedDate
    });
    
    // 如果选择的是当天，禁用已过去的时间段
    if (selectedDate === today) {
      const now = new Date();
      const currentHour = now.getHours();
      
      const timeSlots = this.data.timeSlots.map(slot => {
        const timeStart = parseInt(slot.time.split(':')[0]);
        return {
          ...slot,
          disabled: timeStart <= currentHour
        };
      });
      
      this.setData({ timeSlots });
    } else {
      // 如果不是当天，启用所有时间段
      const timeSlots = this.data.timeSlots.map(slot => ({
        ...slot,
        disabled: false
      }));
      
      this.setData({ timeSlots });
    }
    
    console.log(this.data.currentDate);
    this.initSeats();
  },



  initSeats() {
    let that = this;
    console.log(this.data.floors)
    console.log(this.data.floorIndex)
    let slots = this.data.timeSlots[this.data.selectedTimeIndex];
    let room = this.data.floors[this.data.floorIndex];
    console.log(room)
    console.log(slots)
    const seatCount = room.totalSeats;
    const seats = [];

    // for (let i = 1; i <= seatCount; i++) {
    //   seats.push({
    //     id: i,
    //     seatNumber: i,
    //     status: Math.random() > 0.7 ? 'occupied' : 'available', // 随机生成一些已占座位
    //     selected: false
    //   });
    // }

    wx.request({
      url: 'http://localhost:8081/Seats/RellayList?roomId=' + room.id + '&timeId=' + slots.id+'&date='+this.data.currentDate,
      method: 'POST',
      success(res) {
        console.log(res.data.data)
        that.setData({
          seats: res.data.data
        })
      }
    })

  },

  handleSeatSelect(e) {
    const seatId = e.currentTarget.dataset.id;
    console.log(seatId)

    let num = 0;
    for (let a = 0; a < this.data.seats.length; a++) {
      if (this.data.seats[a].selected) {
        num++;
      }
    }



    const seats = this.data.seats.map(seat => {
      if (seat.seatId === seatId) {
        console.log(seat)
        if (num > 0 && (!seat.selected)) {
          wx.showToast({
            title: '只能预约一个座位',
            icon: 'none'
          })
          return seat;
        }
        this.data.flagSeatId = seatId;
        return { ...seat, selected: !seat.selected };
      }
      return seat;
    });

    this.setData({ seats });
  },

  handleSubmit() {
    const selectedSeats = this.data.seats.filter(seat => seat.selected);
    const selectedTime = this.data.timeSlots.find(slot => slot.selected);

    if (selectedSeats.length === 0 || !selectedTime) {
      wx.showToast({
        title: '请选择座位和时间段',
        icon: 'none'
      });
      return;
    }

    wx.showModal({
      title: '确认预约',
      content: `您选择了${selectedSeats.length}个座位，时间为${this.data.currentDate} ${selectedTime.time}，是否确认预约？`,
      success: (res) => {
        if (res.confirm) {
          // 这里添加预约逻辑
          this.handleReservation();
        }
      }
    });
  },

  handleReservation() {
    let that = this;
    const userInfo = wx.getStorageSync('userInfo')

    if (!userInfo) {
      wx.showToast({
        title: '请登入',
        icon: 'none'
      })
      return;
    }

    console.log(userInfo.id)
    console.log(this.data.currentDate)
    let slots = this.data.timeSlots[this.data.selectedTimeIndex];
    let floors = this.data.floors[this.data.floorIndex];
    console.log(floors)
    console.log(slots)

    let data = {
      timeId: slots.id,
      roomId: floors.id,
      kehuId: userInfo.id,
      time: this.data.currentDate,
      seatsId: this.data.flagSeatId
    };

    wx.request({
      url: 'http://localhost:8081/NewBill/save',
      method: 'POST',
      data: data,
      header: {
        'content-type': 'application/json'
      },
      success: (res) => {
        console.log(res.data)
        if (res.data.code === 200) {
          // 先显示Toast
          wx.showToast({
            title: '预约成功',
            icon: 'success',
            duration: 1500
          });
          
          // 清空数据
          this.setData({
            selectedSeats: [],
            selectedTimeIndex: -1,
            floorIndex: -1,
            flagseat: -1,
            flagSeatId: -1,
            seats: []
          });
          
          // 进行跳转
          wx.navigateTo({
            url: '/pages/records/records'
          });
          console.log('跳转成功');
          
        } else {
          console.error('预约失败：')
          wx.showToast({
            title: res.data.data,
            icon: 'none'
          })
        }
      },
      fail(error) {
        console.error('请求失败：', error)
        wx.showToast({
          title: '网络错误，请重试',
          icon: 'none'
        })
      }
    })
  },

  handleTimeSelect(e) {
    const index = e.detail.value;
    const selectedTime = this.data.timeSlots[index];
    
    // 如果是当天，检查时间段
    if (this.data.currentDate === this.formatDate(new Date())) {
      const now = new Date();
      const currentHour = now.getHours();
      
      // 从时间段字符串中提取小时
      const timeStart = parseInt(selectedTime.time.split(':')[0]);
      
      if (timeStart <= currentHour) {
        wx.showToast({
          title: '不能选择已过去的时间段',
          icon: 'none'
        });
        return;
      }
    }
    
    // 更新选中的时间段
    const timeSlots = this.data.timeSlots.map((slot, idx) => ({
      ...slot,
      selected: idx === parseInt(index)
    }));
    
    this.setData({
      timeSlots,
      selectedTimeIndex: index
    });
    
    this.initSeats();
  },

  bindFloorChange: function (e) {
    console.log(e.detail.value)
    // floors
    this.setData({
      floorIndex: e.detail.value
    }, () => {
      // 楼层改变后重新初始化座位
      this.initSeats();
    })
  }
}); 