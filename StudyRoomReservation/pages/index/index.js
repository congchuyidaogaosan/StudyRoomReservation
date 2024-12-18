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
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
  },

  handleDateChange(e) {
    this.setData({
      currentDate: e.detail.value
    });
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
      url: 'http://localhost:8081/Seats/RellayList?roomId=' + room.id + '&timeId=' + slots.id,
      method: 'POST',
      success(res) {
        console.log(res.data.data)
        that.setData({
          seats: res.data.data
        })
      }
    })


    // this.setData({ seats });
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
      success(res) {
        console.log(res.data)
        if (res.data.code === 200) {

          wx.showToast({
            title: '预约成功',
            icon: 'success'
          })
          wx.switchTab({
            url: '/pages/records/records'
          })
        } else {
          console.error('预约失败：')
          wx.showToast({
            title: res.data.message,
            icon: 'none'
          })
        }
      }
    })
  },

  handleTimeSelect(e) {
    const index = e.detail.value;
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